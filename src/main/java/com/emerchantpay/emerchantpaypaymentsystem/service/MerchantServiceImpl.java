package com.emerchantpay.emerchantpaypaymentsystem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.MerchantEntity;
import com.emerchantpay.emerchantpaypaymentsystem.model.Merchant;
import com.emerchantpay.emerchantpaypaymentsystem.repository.MerchantRepository;
import com.emerchantpay.emerchantpaypaymentsystem.util.MerchantMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MerchantServiceImpl implements MerchantService {

  private final MerchantRepository merchantRepository;

  @Override
  public List<Merchant> getMerchants() {
    return MerchantMapper.merchantEntitesToDtos(merchantRepository.findAll());
  }

  @Transactional
  @Override
  public void deleteMerchant(Long id) {
    merchantRepository.deleteById(id);
  }

  @Override
  public void updateMerchant(Long id, MerchantEntity merchantEntity) {
    String password = merchantRepository.findById(merchantEntity.getId()).get().getPassword();
    merchantEntity.setPassword(password);
    merchantRepository.save(merchantEntity);
  }
}
