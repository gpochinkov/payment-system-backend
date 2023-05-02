package com.emerchantpay.emerchantpaypaymentsystem.entiry;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransactionStatus;
import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransactionType;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_transaction")
public class PaymentTransactionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private PaymentTransactionType type;

  private String uuid;

  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private PaymentTransactionStatus status;

  private String customerEmail;

  private String customerPhone;

  @ManyToOne()
  @JoinColumn(name = "reference_id", referencedColumnName = "id")
  private PaymentTransactionEntity reference;

  @ManyToOne()
  @JoinColumn(name = "merchant_id", referencedColumnName = "id")
  private MerchantEntity merchant;

  @CreationTimestamp
  private Instant creationTime;

  @UpdateTimestamp
  private Instant lastUpdateTime;

  @SuppressWarnings("unused")
  public PaymentTransactionEntity() {
  }

  public PaymentTransactionEntity(
      PaymentTransactionType type,
      BigDecimal amount,
      PaymentTransactionStatus status,
      String customerEmail,
      String customerPhone,
      MerchantEntity merchant,
      PaymentTransactionEntity reference
  ) {
    this.type = type;
    this.uuid = UUID.randomUUID().toString();
    this.status = status;
    this.amount = amount;
    this.customerEmail = customerEmail;
    this.customerPhone = customerPhone;
    this.merchant = merchant;
    this.reference = reference;
  }
}
