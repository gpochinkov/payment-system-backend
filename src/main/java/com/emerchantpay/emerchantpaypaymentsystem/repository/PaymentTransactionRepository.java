package com.emerchantpay.emerchantpaypaymentsystem.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.PaymentTransactionEntity;
import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransactionStatus;

@Repository
public interface PaymentTransactionRepository
    extends JpaRepository<PaymentTransactionEntity, Long> {

  List<PaymentTransactionEntity> findAll();

  List<PaymentTransactionEntity> findByStatus(PaymentTransactionStatus status);

    List<PaymentTransactionEntity> findAllByCreationTimeBefore(Instant cutoffTime);



}
