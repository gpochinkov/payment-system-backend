package com.emerchantpay.emerchantpaypaymentsystem.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AuthorizeTransactionPostModel {
  private final BigDecimal amount;
  private final Long customerId;
  private final Long merchantId;
}
