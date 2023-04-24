package com.emerchantpay.emerchantpaypaymentsystem.entiry;

import java.math.BigDecimal;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("CUSTOMER")
public class CustomerEntity extends EmerchantpayUserEntity {

  private String phone;
  private BigDecimal balance;

  public CustomerEntity() {
  }

  public CustomerEntity(String username,
                        String password,
                        String email,
                        boolean isAccountNonExpired,
                        boolean isAccountNonLocked,
                        boolean isCredentialsNonExpired,
                        boolean isEnabled,
                        String phone,
                        BigDecimal balance) {
    super(username,
          password,
          email,
          isAccountNonExpired,
          isAccountNonLocked,
          isCredentialsNonExpired,
          isEnabled);
    this.phone = phone;
    this.balance = balance;
  }
}
