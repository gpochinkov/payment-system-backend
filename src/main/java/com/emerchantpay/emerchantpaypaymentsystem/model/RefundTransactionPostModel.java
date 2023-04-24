package com.emerchantpay.emerchantpaypaymentsystem.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RefundTransactionPostModel {
  private final BigDecimal amount;
  private final Long transactionId;
}
