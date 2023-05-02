package com.emerchantpay.emerchantpaypaymentsystem.util;

import java.util.ArrayList;
import java.util.List;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.PaymentTransactionEntity;
import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransaction;

public class TransactionMapper {

  public static PaymentTransaction paymentTransactionEntityToDto(
      PaymentTransactionEntity paymentTransactionEntity) {
    return PaymentTransaction.builder()
                             .id(paymentTransactionEntity.getId())
                             .uuid(paymentTransactionEntity.getUuid())
                             .type(paymentTransactionEntity.getType())
                             .amount(paymentTransactionEntity.getAmount())
                             .status(paymentTransactionEntity.getStatus())
                             .customerEmail(paymentTransactionEntity.getCustomerEmail())
                             .customerPhone(paymentTransactionEntity.getCustomerPhone())
                             .reference(paymentTransactionEntity.getReference() != null
                                            ? paymentTransactionEntity.getReference().getId()
                                            : null)
                             .merchantId(paymentTransactionEntity.getMerchant().getId())
                             .merchantUserName(paymentTransactionEntity.getMerchant().getUsername())
                             .build();
  }

  public static List<PaymentTransaction> paymentTransactionEntitesToDtos(
      List<PaymentTransactionEntity> paymentTransactionEntites) {

    List<PaymentTransaction> paymentTransactions = new ArrayList<>();

    for (PaymentTransactionEntity paymentTransactionEntity : paymentTransactionEntites) {
      paymentTransactions.add(paymentTransactionEntityToDto(paymentTransactionEntity));
    }

    return paymentTransactions;
  }
}
