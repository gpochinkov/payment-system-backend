package com.emerchantpay.emerchantpaypaymentsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransactionsResponse;
import com.emerchantpay.emerchantpaypaymentsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ui")
public class TransactionsUIController {

  private final TransactionService transactionService;

  @GetMapping("/transactions")
  public PaymentTransactionsResponse getTransactions() {
    return new PaymentTransactionsResponse(transactionService.getTransactions());
  }
}
