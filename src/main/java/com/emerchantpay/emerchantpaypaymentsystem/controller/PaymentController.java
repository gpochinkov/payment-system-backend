package com.emerchantpay.emerchantpaypaymentsystem.controller;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emerchantpay.emerchantpaypaymentsystem.model.AuthorizeTransactionPostModel;
import com.emerchantpay.emerchantpaypaymentsystem.model.ChargeTransactionPostModel;
import com.emerchantpay.emerchantpaypaymentsystem.model.RefundTransactionPostModel;
import com.emerchantpay.emerchantpaypaymentsystem.model.ReversalTransactionPostModel;
import com.emerchantpay.emerchantpaypaymentsystem.service.TransactionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {
  private final TransactionService transactionService;

  @PostMapping("/authorizeTransaction")
  public ResponseEntity<String> authorizeTransaction(
      @RequestBody @NotNull AuthorizeTransactionPostModel data) {
    transactionService.authorizeTransaction(data.getAmount(),
                                            data.getCustomerId(),
                                            data.getMerchantId());
    return ResponseEntity.ok("Data processed successfully");
  }

  @PostMapping("/chargeTransaction")
  public ResponseEntity<String> chargeTransaction(
      @RequestBody @NotNull ChargeTransactionPostModel data) {
    transactionService.chargeTransaction(data.getAmount(), data.getTransactionId());
    return ResponseEntity.ok("Data processed successfully");
  }

  @PostMapping("/refundTransaction")
  public ResponseEntity<String> refundTransaction(
      @RequestBody @NotNull RefundTransactionPostModel data) {
    transactionService.refundTransaction(data.getAmount(), data.getTransactionId());
    return ResponseEntity.ok("Data processed successfully");
  }

  @PostMapping("/reversalTransaction")
  public ResponseEntity<String> reversalTransaction(
      @RequestBody @NotNull ReversalTransactionPostModel data) {
    transactionService.reversalTransaction(data.getTransactionId());
    return ResponseEntity.ok("Data processed successfully");
  }

}
