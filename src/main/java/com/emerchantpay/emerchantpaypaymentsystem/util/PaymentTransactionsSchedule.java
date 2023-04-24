package com.emerchantpay.emerchantpaypaymentsystem.util;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.PaymentTransactionEntity;
import com.emerchantpay.emerchantpaypaymentsystem.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class PaymentTransactionsSchedule {

//  private final static Long ONE_HOUR_IN_MS = 1000 * 60 * 60L;

  private final PaymentTransactionRepository paymentTransactionRepository;


  @Scheduled(fixedDelay = 3600000)
  public void scheduleFixedDelayTask() {

    Instant currentTime = Instant.now().minusMillis(30000);

    List<PaymentTransactionEntity> old = paymentTransactionRepository
        .findAllByCreationTimeBefore(currentTime);

    List<PaymentTransactionEntity> sorted = old
        .stream()
        .sorted(Comparator.comparing(PaymentTransactionEntity::getCreationTime).reversed())
        .toList();

    for (PaymentTransactionEntity entity : sorted) {
      try {
        paymentTransactionRepository.deleteById(entity.getId());
      } catch (DataIntegrityViolationException ignore) {
      }
    }
    log.info(
        "Transactions, older than 1 hour has been deleted (except the ones which has reference to"
            + " them from transactions earlier than 1 hour)");
  }
}
