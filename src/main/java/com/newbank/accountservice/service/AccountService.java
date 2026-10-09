package com.newbank.accountservice.service;

import com.newbank.accountservice.entity.Account;
import com.newbank.accountservice.dto.AccountResponse;
import com.newbank.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountResponse createAccount(Account account) {
        log.info("Creating account for userId: {}", account.getUserId());
        Account saved = accountRepository.save(account);
        return convertToResponse(saved);
    }

    public AccountResponse getAccount(Long id) {
        log.info("Fetching account with id: {}", id);
        return accountRepository.findById(id)
                .map(this::convertToResponse)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public List<AccountResponse> getAccountsByUserId(Long userId) {
        log.info("Fetching accounts for userId: {}", userId);
        return accountRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public AccountResponse updateStatus(Long id, String status) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setStatus(status);
        Account updated = accountRepository.save(account);
        return convertToResponse(updated);
    }

    private AccountResponse convertToResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .userId(account.getUserId())
                .accountType(account.getAccountType())
                .currency(account.getCurrency())
                .balance(account.getBalance())
                .status(account.getStatus())
                .build();
    }
}
