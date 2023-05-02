package com.emerchantpay.emerchantpaypaymentsystem;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
@Profile("scheduler")
public class PaymentTransactionsSchedule {

  //  private final static Long ONE_HOUR_IN_MS = 1000 * 60 * 60L;

  private final PaymentTransactionRepository paymentTransactionRepository;

  @SuppressWarnings("unused")
  @Scheduled(fixedDelay = 3600000, initialDelay = 3600000)
  public void scheduleFixedDelayTask() {

    Instant theTimeOneHourAgo = Instant.now().minusMillis(3600000);

    List<PaymentTransactionEntity> old = paymentTransactionRepository
        .findAllByCreationTimeBefore(theTimeOneHourAgo);

    List<PaymentTransactionEntity> sorted = old
        .stream()
        .sorted(Comparator.comparing(PaymentTransactionEntity::getCreationTime).reversed())
        .toList();

    for (PaymentTransactionEntity trx : sorted) {
      try {
        paymentTransactionRepository.deleteById(trx.getId());
      } catch (DataIntegrityViolationException ignore) {
        log.error(
            "Transaction ID: {} cannot be deleted because it is referred by another transaction",
            trx.getId());
      }
    }
    log.info(
        "Transactions, older than 1 hour has been deleted (except the ones which has been referenced");
  }
}
