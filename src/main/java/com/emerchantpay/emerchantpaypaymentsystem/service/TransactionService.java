package com.emerchantpay.emerchantpaypaymentsystem.service;

import java.math.BigDecimal;
import java.util.List;

import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransaction;

public interface TransactionService {
  void authorizeTransaction(BigDecimal lockAmount, Long customerId, Long merchantId);

  void chargeTransaction(BigDecimal chargeAmount, Long authorisedTransactionId);

  void refundTransaction(BigDecimal refundAmount, Long chargeTransactionId);

  void reversalTransaction(Long authorisedTransactionId);

  List<PaymentTransaction> getTransactions();
}
