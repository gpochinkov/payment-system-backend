package com.emerchantpay.emerchantpaypaymentsystem.util;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.EmerchantpayUserEntity;
import com.emerchantpay.emerchantpaypaymentsystem.model.EmerchantpayUser;

public class EmerchantpayUserMapper {

  public static EmerchantpayUser EmerchantpayUserEntityToDto(
      EmerchantpayUserEntity emerchantpayUserEntity) {
    return EmerchantpayUser.builder()
                           .id(emerchantpayUserEntity.getId())
                           .username(emerchantpayUserEntity.getUsername())
                           .password(emerchantpayUserEntity.getPassword())
                           .email(emerchantpayUserEntity.getEmail())
                           .email(emerchantpayUserEntity.getEmail())
                           .isAccountNonExpired(emerchantpayUserEntity.isAccountNonExpired())
                           .isAccountNonLocked(emerchantpayUserEntity.isAccountNonLocked())
                           .isCredentialsNonExpired(
                               emerchantpayUserEntity.isCredentialsNonExpired())
                           .isEnabled(emerchantpayUserEntity.isEnabled())
                           .build();
  }
}
