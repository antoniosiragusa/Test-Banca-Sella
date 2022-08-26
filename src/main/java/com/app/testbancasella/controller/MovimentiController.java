package com.app.testbancasella.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.testbancasella.model.Movimenti;
import com.app.testbancasella.service.MovimentiService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/fabrick")
public class MovimentiController {
	
    @Autowired
    MovimentiService movimentiService;

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<Movimenti>>getAccount(@PathVariable(name="accountId")String accountId,
        @RequestParam("fromAccountingDate")String fromAccountingDate,
        @RequestParam("toAccountingDate") String toAccountingDate) throws JsonProcessingException {

        List<Movimenti>movimenti=movimentiService.saveDB(accountId,fromAccountingDate,toAccountingDate);
        return new ResponseEntity<>(movimenti,HttpStatus.OK);
    }

}
