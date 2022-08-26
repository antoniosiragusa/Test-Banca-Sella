package com.app.testbancasella.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.app.testbancasella.dto.MovimentiDto;
import com.app.testbancasella.model.Movimenti;
import com.app.testbancasella.repository.MovimentiRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import aj.org.objectweb.asm.TypeReference;

@Service
public class MovimentiService {

	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	MovimentiRepository movimentiRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;

	public HttpEntity<Object> getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Auth-Schema", "S2S");
		headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		return requestEntity;
	}

	public List<Movimenti> saveDB(List<Movimenti> movimentilist) {
		return movimentiRepository.saveAll(movimentilist);
	}

	public List<Movimenti> saveDB(String accountId, String fromAccountingDate, String toAccountingDate)
			throws JsonProcessingException {
		log.info("Numero Account : " + accountId);
		Map<String, String> params = new HashMap<String, String>();

		params.put("fromAccountingDate", fromAccountingDate);
		params.put("toAccountingDate", toAccountingDate);

		String url = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/" + accountId + "/transactions";
		String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("fromAccountingDate", "{fromAccountingDate}")
				.queryParam("toAccountingDate", "{toAccountingDate}").encode().toUriString();

		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(urlTemplate, HttpMethod.GET, getHeaders(), String.class, params);
		} catch (HttpClientErrorException e) {
			log.error("Errore nel recupero delle transazioni dell account :" + accountId);
		}
		String responseBody = responseEntity.getBody();
		JsonNode jsonNode = objectMapper.readTree(responseBody);

		String payload = jsonNode.get("payload").get("list").toString();

		ObjectReader reader = null;
		List<MovimentiDto> movimentilist = new ArrayList<>();
		try {
			reader = objectMapper.readerFor(new com.fasterxml.jackson.core.type.TypeReference<List<MovimentiDto>>() {
			});
			movimentilist = reader.readValue(payload);

		} catch (HttpClientErrorException e) {
			log.error("Errore nella mappatura dei dati: " + e.getMessage());
		}
		
		List<Movimenti> movimenti = new ArrayList<>();
		for (MovimentiDto dto : movimentilist) {
			Movimenti movimento = new Movimenti();
			movimento.setTransactionId(dto.getTransactionId());
			movimento.setAccountingDate(dto.getAccountingDate());
			movimento.setAmount(dto.getAmount());
			movimento.setCurrency(dto.getCurrency());
			movimento.setDescription(dto.getDescription());
			movimento.setValueDate(dto.getValueDate());
			movimento.setEnumeration(dto.getType().getEnumeration());
			movimento.setValue(dto.getType().getValue());
			movimento.setOperationId(dto.getOperationId());
			movimenti.add(movimento);
		}
		try {
			movimentiRepository.saveAll(movimenti);
		} catch (HttpClientErrorException e) {
			log.error("Errore nel salvataggio sul db :" + e.getMessage());

		}
		return movimenti;
	}

}
