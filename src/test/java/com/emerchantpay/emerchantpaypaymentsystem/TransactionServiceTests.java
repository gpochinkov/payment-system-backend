package com.emerchantpay.emerchantpaypaymentsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import javax.validation.ValidationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.CustomerEntity;
import com.emerchantpay.emerchantpaypaymentsystem.entiry.MerchantEntity;
import com.emerchantpay.emerchantpaypaymentsystem.entiry.PaymentTransactionEntity;
import com.emerchantpay.emerchantpaypaymentsystem.exception.ResourceNotFoundException;
import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransactionStatus;
import com.emerchantpay.emerchantpaypaymentsystem.model.PaymentTransactionType;
import com.emerchantpay.emerchantpaypaymentsystem.repository.CustomerRepository;
import com.emerchantpay.emerchantpaypaymentsystem.repository.MerchantRepository;
import com.emerchantpay.emerchantpaypaymentsystem.repository.PaymentTransactionRepository;
import com.emerchantpay.emerchantpaypaymentsystem.service.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {

  @Mock
  private PaymentTransactionRepository paymentTransactionRepository;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private MerchantRepository merchantRepository;

  @InjectMocks
  private TransactionServiceImpl transactionService;

  @Test
  void testAuthorizeTransaction() {
    BigDecimal lockAmount = BigDecimal.valueOf(100);
    Long customerId = 1L;
    Long merchantId = 2L;
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(customerId);
    customerEntity.setBalance(BigDecimal.valueOf(200));
    customerEntity.setEmail("customer@test.com");
    customerEntity.setPhone("123456789");

    MerchantEntity merchantEntity = new MerchantEntity();
    merchantEntity.setId(merchantId);

    PaymentTransactionEntity paymentTransactionEntity = new PaymentTransactionEntity(
        PaymentTransactionType.AUTHORIZE,
        lockAmount,
        PaymentTransactionStatus.APPROVED,
        customerEntity.getEmail(),
        customerEntity.getPhone(),
        merchantEntity,
        null
    );

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
    when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(merchantEntity));
    when(paymentTransactionRepository.save(any(PaymentTransactionEntity.class))).thenReturn(
        paymentTransactionEntity);

    // Act
    transactionService.authorizeTransaction(lockAmount, customerId, merchantId);

    // Assert
    verify(customerRepository, times(1)).findById(customerId);
    verify(merchantRepository, times(1)).findById(merchantId);
    verify(paymentTransactionRepository, times(1)).save(any(PaymentTransactionEntity.class));
    assertEquals(customerEntity.getBalance(), BigDecimal.valueOf(100));

    assertEquals(paymentTransactionEntity.getType(), PaymentTransactionType.AUTHORIZE);

    assertEquals(paymentTransactionEntity.getAmount(), BigDecimal.valueOf(100));
    assertEquals(paymentTransactionEntity.getStatus(), PaymentTransactionStatus.APPROVED);
    assertEquals(paymentTransactionEntity.getCustomerEmail(), "customer@test.com");
    assertEquals(paymentTransactionEntity.getCustomerPhone(), "123456789");
    assertEquals(paymentTransactionEntity.getMerchant(), merchantEntity);
    assertNull(paymentTransactionEntity.getReference());
  }

  @Test
  public void authorizeTransaction_failsWithValidationException_whenLockAmountIsZero() {
    // arrange
    BigDecimal lockAmount = BigDecimal.ZERO;
    Long customerId = 1L;
    Long merchantId = 2L;

    // act and assert
    ValidationException exception = assertThrows(
        ValidationException.class,
        () -> transactionService.authorizeTransaction(lockAmount, customerId, merchantId)
    );

    assertEquals("lockAmount must be > 0", exception.getMessage());

    // verify that the service method did not interact with the repositories
    verifyNoInteractions(customerRepository, merchantRepository, paymentTransactionRepository);
  }

  @Test
  public void authorizeTransaction_failsWithResourceNotFoundException_whenCustomerDoesNotExist() {
    // arrange
    BigDecimal lockAmount = BigDecimal.TEN;
    Long customerId = 1L;
    Long merchantId = 2L;

    // set up the customerRepository mock to return an empty Optional
    when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

    // act and assert
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> transactionService.authorizeTransaction(lockAmount, customerId, merchantId)
    );

    assertEquals("Customer does not exist", exception.getMessage());

    // verify that the service method only interacted with the customerRepository
    verify(customerRepository).findById(customerId);
    verifyNoMoreInteractions(customerRepository, merchantRepository, paymentTransactionRepository);
  }

  @Test
  public void authorizeTransaction_failsWithValidationException_whenCustomerBalanceIsLowerThanLockAmount() {
    // arrange
    BigDecimal lockAmount = BigDecimal.valueOf(1000);
    Long customerId = 1L;
    Long merchantId = 2L;

    // set up the customerRepository mock to return a customer entity with a balance lower than
    // lockAmount
    CustomerEntity customer = new CustomerEntity();
    customer.setBalance(BigDecimal.valueOf(500));
    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

    // act and assert
    ValidationException exception = assertThrows(
        ValidationException.class,
        () -> transactionService.authorizeTransaction(lockAmount, customerId, merchantId)
    );

    assertEquals("Customer balance must be higher than lockAmount", exception.getMessage());

    // verify that the service method interacted with the customerRepository but not with the
    // other mocks
    verify(customerRepository).findById(customerId);
    verifyNoMoreInteractions(customerRepository, merchantRepository, paymentTransactionRepository);
  }

  @Test
  public void authorizeTransaction_failsWithResourceNotFoundException_whenMerchantDoesNotExist() {
    // arrange
    BigDecimal lockAmount = BigDecimal.valueOf(1000);
    Long customerId = 1L;
    Long merchantId = 2L;

    // set up the merchantRepository mock to return an empty Optional when findById is called
    // with the merchant ID
    when(merchantRepository.findById(merchantId)).thenReturn(Optional.empty());

    // set up the customerRepository mock to return a customer entity with a balance higher than
    // lockAmount
    CustomerEntity customer = new CustomerEntity();
    customer.setBalance(BigDecimal.valueOf(1500));
    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

    // act and assert
    ResourceNotFoundException exception = assertThrows(
        ResourceNotFoundException.class,
        () -> transactionService.authorizeTransaction(lockAmount, customerId, merchantId)
    );

    assertEquals("Merchant does not exist", exception.getMessage());

    // verify that the service method interacted with the customerRepository and
    // merchantRepository, but not with the paymentTransactionRepository
    verify(customerRepository).findById(customerId);
    verify(merchantRepository).findById(merchantId);
    verifyNoMoreInteractions(customerRepository, merchantRepository, paymentTransactionRepository);
  }
}

