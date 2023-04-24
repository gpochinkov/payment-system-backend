package com.emerchantpay.emerchantpaypaymentsystem.entiry;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("ADMIN")
public class AdminEntity extends EmerchantpayUserEntity {

  public AdminEntity() {
  }

  public AdminEntity(String username,
                     String password,
                     String email,
                     boolean isAccountNonExpired,
                     boolean isAccountNonLocked,
                     boolean isCredentialsNonExpired,
                     boolean isEnabled) {
    super(username,
          password,
          email,
          isAccountNonExpired,
          isAccountNonLocked,
          isCredentialsNonExpired,
          isEnabled);
  }
}
