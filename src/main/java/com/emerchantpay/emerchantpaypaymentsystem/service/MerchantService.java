package com.emerchantpay.emerchantpaypaymentsystem.service;

import java.util.List;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.MerchantEntity;
import com.emerchantpay.emerchantpaypaymentsystem.model.Merchant;

public interface MerchantService {
  List<Merchant> getMerchants();

  void deleteMerchant(Long id);

  void updateMerchant(Long id, MerchantEntity merchantEntity);
}
