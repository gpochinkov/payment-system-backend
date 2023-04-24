package com.emerchantpay.emerchantpaypaymentsystem.entiry;

import java.math.BigDecimal;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.emerchantpay.emerchantpaypaymentsystem.model.MerchantStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("MERCHANT")
@SuperBuilder
public class MerchantEntity extends EmerchantpayUserEntity {

  private String description;
  @Enumerated(EnumType.STRING)
  private MerchantStatus status;
  private BigDecimal totalTransactionSum;

  public MerchantEntity() {
  }

  public MerchantEntity(String username,
                        String password,
                        String email,
                        boolean isAccountNonExpired,
                        boolean isAccountNonLocked,
                        boolean isCredentialsNonExpired,
                        boolean isEnabled,
                        String description,
                        MerchantStatus status,
                        BigDecimal totalTransactionSum) {
    super(username,
          password,
          email,
          isAccountNonExpired,
          isAccountNonLocked,
          isCredentialsNonExpired,
          isEnabled);
    this.description = description;
    this.status = status;
    this.totalTransactionSum = totalTransactionSum;
  }
}
