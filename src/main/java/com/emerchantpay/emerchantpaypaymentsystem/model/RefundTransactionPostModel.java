package com.emerchantpay.emerchantpaypaymentsystem.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundTransactionPostModel {
  private BigDecimal amount;
  private Long transactionId;
}
