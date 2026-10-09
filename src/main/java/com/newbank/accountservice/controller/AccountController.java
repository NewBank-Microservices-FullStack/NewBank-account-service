package com.newbank.accountservice.controller;

import com.newbank.accountservice.dto.AccountResponse;
import com.newbank.accountservice.entity.Account;
import com.newbank.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody Account account) {
        log.info("POST /api/accounts");
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        log.info("GET /api/accounts/{}", id);
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(@PathVariable Long userId) {
        log.info("GET /api/accounts/user/{}", userId);
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AccountResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        log.info("PUT /api/accounts/{}/status?status={}", id, status);
        return ResponseEntity.ok(accountService.updateStatus(id, status));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Account Service is running");
    }
}
