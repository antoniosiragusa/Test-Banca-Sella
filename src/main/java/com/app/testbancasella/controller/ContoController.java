package com.app.testbancasella.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.testbancasella.dto.MoneyTransferDto;
import com.app.testbancasella.dto.SaldoDto;
import com.app.testbancasella.service.ContoService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/fabrick/accounts")
public class ContoController {

	@Autowired
	ContoService contoService;

	@GetMapping("/{accountId}")
	public ResponseEntity<Object> getAccount(@PathVariable(name = "accountId") String accountId) {
		return contoService.getAccount(accountId);
	}

	@GetMapping("/{accountId}/balance")
	public ResponseEntity<SaldoDto> getBalance(@PathVariable(name = "accountId") String accountId)
			throws JsonProcessingException {
		SaldoDto balance = contoService.getBalance(accountId);
		return new ResponseEntity<>(balance, HttpStatus.OK);

	}

	@PostMapping("{accountId}/payments/money-transfers")
	public ResponseEntity<Object> createMoneyTransfer(@PathVariable(name = "accountId") String accountId,
			@RequestBody MoneyTransferDto moneyTransferDto) {
		return contoService.moneyTransfer(accountId, moneyTransferDto);
	}

}
