package com.emerchantpay.emerchantpaypaymentsystem.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Data
public class EmerchantpayUser {

  public EmerchantpayUser() {
  }

  private Long id;
  private EmerchantpayUserType type;
  private String username;
  private String password;
  private String email;
  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;
}
