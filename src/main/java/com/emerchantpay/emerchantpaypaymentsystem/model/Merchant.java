package com.emerchantpay.emerchantpaypaymentsystem.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class Merchant extends EmerchantpayUser {

  public Merchant() {
  }

  private String description;
  private MerchantStatus status;
  private BigDecimal totalTransactionSum;
}
