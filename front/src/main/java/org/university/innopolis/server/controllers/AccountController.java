package org.university.innopolis.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.university.innopolis.server.services.AccountService;
import org.university.innopolis.server.services.AuthenticationService;
import org.university.innopolis.server.views.AccountView;
import org.university.innopolis.server.wrappers.BalanceWrapper;

import javax.validation.Valid;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class AccountController {
    private AccountService accountService;
    private AuthenticationService authService;

    @Autowired
    public AccountController(AccountService accountService, AuthenticationService authService) {
        this.accountService = accountService;
        this.authService = authService;
    }

    @PostMapping(path = "/balance")
    ResponseEntity adjustBalance(@Valid @RequestBody BalanceWrapper wrapper,
                                 @RequestAttribute int accountId) {
        return ResponseEntity.ok(accountService.adjustBalance(wrapper.getBalance(), accountId));
    }

    @GetMapping(path = "/user")
    ResponseEntity getAccount(@RequestParam String login,
                              @RequestAttribute int accountId) {
        if (!authService.isAuthorized(accountId, login))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        AccountView res = accountService.findAccount(login);
        if (res == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this login is not found");
        return ResponseEntity.ok(res);
    }
}
