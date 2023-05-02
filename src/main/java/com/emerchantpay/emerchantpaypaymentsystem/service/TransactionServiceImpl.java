package com.emerchantpay.emerchantpaypaymentsystem.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.CustomerEntity;
import com.emerchantpay.emerchantpaypaymentsystem.entiry.MerchantEntity;
import com.emerchantpay.emerchantpaypaymentsystem.entiry.PaymentTransactionEntity;
import com.emerchantpay.emerchantpaypaymentsystem.exception.ResourceNotFoundException;
import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransaction;
import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransactionStatus;
import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransactionType;
import com.emerchantpay.emerchantpaypaymentsystem.repository.CustomerRepository;
import com.emerchantpay.emerchantpaypaymentsystem.repository.MerchantRepository;
import com.emerchantpay.emerchantpaypaymentsystem.repository.PaymentTransactionRepository;
import com.emerchantpay.emerchantpaypaymentsystem.util.TransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

  private final PaymentTransactionRepository paymentTransactionRepository;
  private final CustomerRepository customerRepository;
  private final MerchantRepository merchantRepository;

  @Transactional
  @Override
  public void authorizeTransaction(BigDecimal lockAmount, Long customerId, Long merchantId) {

    if (lockAmount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ValidationException("lockAmount must be > 0");
    }

    CustomerEntity customerEntity = Optional.of(customerRepository.findById(customerId)).get()
                                            .orElseThrow(() -> new ResourceNotFoundException(
                                                "Customer does not exist"));

    if (customerEntity.getBalance().compareTo(lockAmount) < 0) {
      throw new ValidationException("Customer balance must be higher than lockAmount");
    }

    MerchantEntity merchantEntity = Optional.of(merchantRepository.findById(merchantId)).get()
                                            .orElseThrow(() -> new ResourceNotFoundException(
                                                "Merchant does not exist"));

    customerEntity.setBalance(customerEntity.getBalance().subtract(lockAmount));

    customerRepository.save(customerEntity);

    PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity(
        PaymentTransactionType.AUTHORIZE,
        lockAmount,
        PaymentTransactionStatus.APPROVED,
        customerEntity.getEmail(),
        customerEntity.getPhone(),
        merchantEntity,
        null
    );

    paymentTransactionRepository.save(paymentTransactionEntity);
  }

  @Transactional
  @Override
  public void chargeTransaction(BigDecimal chargeAmount, Long authorisedTransactionId) {

    if (chargeAmount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ValidationException("chargeAmount must be > 0.");
    }

    PaymentTransactionEntity referredTransaction = Optional
        .of(paymentTransactionRepository.findById(authorisedTransactionId)).get().orElseThrow(
            () -> new ResourceNotFoundException("The referred transaction does not exists."));

    if (!referredTransaction.getType().equals(PaymentTransactionType.AUTHORIZE)) {
      throw new ValidationException("The referred transaction must be of type AUTHORIZE.");
    }

    if (referredTransaction.getAmount().compareTo(chargeAmount) < 0) {
      throw new ValidationException(
          "The amount of the transaction must be maximum the amount of the authorized transaction"
              + ".");
    }

    CustomerEntity customer = customerRepository.findByEmail(
        referredTransaction.getCustomerEmail());
    MerchantEntity merchant = referredTransaction.getMerchant();

    if (referredTransaction.getStatus().equals(PaymentTransactionStatus.ERROR)
        || referredTransaction.getStatus().equals(PaymentTransactionStatus.REVERSED)) {

      PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity(
          PaymentTransactionType.CHARGE,
          chargeAmount,
          PaymentTransactionStatus.ERROR,
          customer.getEmail(),
          customer.getPhone(),
          merchant,
          referredTransaction
      );

      paymentTransactionRepository.save(paymentTransactionEntity);

      log.info("Only approved or refunded transactions can be referenced.");
    } else if (referredTransaction.getStatus().equals(PaymentTransactionStatus.APPROVED)) {
      PaymentTransactionEntity transaction = new PaymentTransactionEntity(
          PaymentTransactionType.CHARGE,
          chargeAmount,
          PaymentTransactionStatus.APPROVED,
          customer.getEmail(),
          customer.getPhone(),
          merchant,
          referredTransaction
      );

      paymentTransactionRepository.save(transaction);

      merchant.setTotalTransactionSum(merchant.getTotalTransactionSum().add(chargeAmount));

      merchantRepository.save(merchant);
    }
  }

  @Transactional
  @Override
  public void refundTransaction(BigDecimal refundAmount, Long chargeTransactionId) {

    if (refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ValidationException("refundAmount must be > 0.");
    }

    PaymentTransactionEntity referredTransaction = Optional.of(
        paymentTransactionRepository.findById(chargeTransactionId)).get().orElseThrow(
        () -> new ResourceNotFoundException("The referred transaction does not exists."));

    if (!referredTransaction.getType().equals(PaymentTransactionType.CHARGE)) {

      throw new ValidationException("The referred transaction must be of type CHARGE.");
    }

    if (referredTransaction.getAmount().compareTo(refundAmount) < 0) {
      throw new ValidationException("The refund cannot be bigger than the CHARGE transaction.");
    }

    CustomerEntity customer = customerRepository.findByEmail(
        referredTransaction.getCustomerEmail());
    MerchantEntity merchant = referredTransaction.getMerchant();

    PaymentTransactionEntity transaction = new PaymentTransactionEntity(
        PaymentTransactionType.REFUND,
        refundAmount,
        PaymentTransactionStatus.APPROVED,
        customer.getEmail(),
        customer.getPhone(),
        merchant,
        referredTransaction
    );
    paymentTransactionRepository.save(transaction);

    referredTransaction.setStatus(PaymentTransactionStatus.REFUNDED);
    paymentTransactionRepository.save(referredTransaction);

    merchant.setTotalTransactionSum(merchant.getTotalTransactionSum().subtract(refundAmount));
    merchantRepository.save(merchant);

  }

  @Transactional
  @Override
  public void reversalTransaction(Long authorisedTransactionId) {

    PaymentTransactionEntity referredTransaction = Optional.of(
        paymentTransactionRepository.findById(authorisedTransactionId)).get().orElseThrow(
        () -> new ResourceNotFoundException("The referred transaction does not exists."));

    if (referredTransaction.getStatus().equals(PaymentTransactionStatus.REVERSED)) {
      throw new ValidationException("The referred transaction cannot be in REVERSED state.");
    }

    CustomerEntity customer = customerRepository.findByEmail(
        referredTransaction.getCustomerEmail());
    MerchantEntity merchant = referredTransaction.getMerchant();

    PaymentTransactionEntity transaction = new PaymentTransactionEntity(
        PaymentTransactionType.REVERSAL,
        null,
        PaymentTransactionStatus.APPROVED,
        customer.getEmail(),
        customer.getPhone(),
        merchant,
        referredTransaction
    );

    paymentTransactionRepository.save(transaction);

    referredTransaction.setStatus(PaymentTransactionStatus.REVERSED);
    paymentTransactionRepository.save(referredTransaction);

    customer.setBalance(customer.getBalance().add(referredTransaction.getAmount()));
    customerRepository.save(customer);
  }

  @Override
  public List<PaymentTransaction> getTransactions() {
    return TransactionMapper.paymentTransactionEntitesToDtos(
        paymentTransactionRepository.findAll());
  }
}
