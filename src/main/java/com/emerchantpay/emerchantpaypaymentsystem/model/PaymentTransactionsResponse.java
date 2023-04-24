package com.emerchantpay.emerchantpaypaymentsystem.model;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class PaymentTransactionsResponse {
  private final List<PaymentTransaction> paymentTransactions;
}
