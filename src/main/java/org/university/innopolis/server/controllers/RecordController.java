package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.university.innopolis.server.common.Currency;
import org.university.innopolis.server.common.Type;
import org.university.innopolis.server.services.RecordService;

import java.util.Date;

@Controller
@RequestMapping(path="api/")
public class RecordController {

    private RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping(path="/add/expense")
    ResponseEntity addExpense(@RequestParam String description,
                              @RequestParam int amount,
                              @RequestParam Currency currency,
                              @RequestParam Date date) {
        return ResponseEntity.ok(recordService.addRecord(
                description,
                amount,
                currency,
                date,
                Type.EXPENSE));
    }

    @PostMapping(path="add/income")
    ResponseEntity addIncome(@RequestParam String description,
                             @RequestParam int amount,
                             @RequestParam Currency currency,
                             @RequestParam Date date) {
        return ResponseEntity.ok(recordService.addRecord(
                description,
                amount,
                currency,
                date,
                Type.INCOME));
    }
}
