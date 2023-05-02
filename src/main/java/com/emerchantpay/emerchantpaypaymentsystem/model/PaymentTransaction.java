package com.emerchantpay.emerchantpaypaymentsystem.model;

import java.math.BigDecimal;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
public class PaymentTransaction {
  @EqualsAndHashCode.Exclude
  private Long id;

  private String uuid;
  private PaymentTransactionType type;
  private BigDecimal amount;
  private PaymentTransactionStatus status;

  @NotBlank(message = "userEmail must not be blank and must be valid email")
  @Email(message = "userEmail must not be blank and must be valid email")
  private String customerEmail;

  @NotBlank(message = "customerPhone must not be blank and must be valid phone")
  private String customerPhone;

  private Long reference;
  private Long merchantId;
  private String merchantUserName;
}
