package com.app.testbancasella.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.app.testbancasella.dto.MoneyTransferDto;
import com.app.testbancasella.dto.SaldoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import aj.org.objectweb.asm.TypeReference;

@Service
public class ContoService {

	protected final Log log = LogFactory.getLog(getClass());

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

	public ResponseEntity<Object> moneyTransfer(String accountId, MoneyTransferDto dto) {
		String url = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/" + accountId
				+ "/payments/money-transfers";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Auth-Schema", "S2S");
		headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");

		HttpEntity<MoneyTransferDto> requestEntity = new HttpEntity<>(dto, headers);

		ResponseEntity<Object> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
		} catch (HttpClientErrorException e) {
			log.error("Errore nell'effettuare il bonifico" + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((e.getResponseBodyAsString()));
		}
		return responseEntity;
	}

	public ResponseEntity<Object> getAccount(String accountId) {
		log.info("Numbero conto da recuperare:" + accountId);
		String url = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/" + accountId;
		ResponseEntity<Object> responseEntity = null;
		try {
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, getHeaders(), Object.class);
		} catch (HttpClientErrorException e) {
			log.error("Errore nel recupero dei dati dell'account :" + accountId);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getResponseBodyAsString());
		}
		return responseEntity;
	}

	public SaldoDto getBalance(String accountId) throws JsonProcessingException {
		String url = "https://sandbox.platfr.io//api/gbs/banking/v4.0/accounts/" + accountId + "/balance";
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, getHeaders(), String.class);
		JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
		String payload = jsonNode.get("payload").toString();
		String balance = jsonNode.get("payload").get("balance").toString();
		ObjectReader reader = null;
		SaldoDto saldoDto = null;
		try {
			reader = objectMapper.readerFor(new com.fasterxml.jackson.core.type.TypeReference<SaldoDto>() {
			});
			saldoDto = reader.readValue(payload);

		} catch (HttpClientErrorException e) {
			log.error("Errore nella mappatura dei dati: " + e.getMessage());
		}
		log.info("Il bilancio del conto " + accountId + " è di :" + balance);
		return saldoDto;
	}

}
