package com.emerchantpay.emerchantpaypaymentsystem.util;

import java.util.ArrayList;
import java.util.List;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.MerchantEntity;
import com.emerchantpay.emerchantpaypaymentsystem.model.EmerchantpayUserType;
import com.emerchantpay.emerchantpaypaymentsystem.model.Merchant;

public class MerchantMapper {

  public static Merchant merchantEntityToDto(MerchantEntity merchantEntity) {
    return Merchant.builder()
                   .id(merchantEntity.getId())
                   .type(EmerchantpayUserType.MERCHANT)
                   .username(merchantEntity.getUsername())
                   .email(merchantEntity.getEmail())
                   .isAccountNonExpired(merchantEntity.isAccountNonExpired())
                   .isAccountNonLocked(merchantEntity.isAccountNonLocked())
                   .isCredentialsNonExpired(merchantEntity.isCredentialsNonExpired())
                   .isEnabled(merchantEntity.isEnabled())
                   .description(merchantEntity.getDescription())
                   .status(merchantEntity.getStatus())
                   .totalTransactionSum(merchantEntity.getTotalTransactionSum())
                   .build();
  }

  public static List<Merchant> merchantEntitesToDtos(List<MerchantEntity> merchantEntities) {

    List<Merchant> merchants = new ArrayList<>();

    for (MerchantEntity merchantEntity : merchantEntities) {
      merchants.add(merchantEntityToDto(merchantEntity));
    }

    return merchants;
  }

  public static MerchantEntity merchantDtoToEntity(Merchant merchant) {
    return MerchantEntity.builder()
                         .id(merchant.getId())
                         .username(merchant.getUsername())
                         .password(merchant.getPassword())
                         .email(merchant.getEmail())
                         .isAccountNonExpired(merchant.isAccountNonExpired())
                         .isAccountNonLocked(merchant.isAccountNonLocked())
                         .isCredentialsNonExpired(merchant.isCredentialsNonExpired())
                         .isEnabled(merchant.isEnabled())
                         .description(merchant.getDescription())
                         .status(merchant.getStatus())
                         .totalTransactionSum(merchant.getTotalTransactionSum())
                         .build();
  }

  public static List<MerchantEntity> merchantDtosToEntities(List<Merchant> merchans) {

    List<MerchantEntity> merchantEntities = new ArrayList<>();

    for (Merchant merchant : merchans) {
      merchantEntities.add(merchantDtoToEntity(merchant));
    }

    return merchantEntities;
  }
}
