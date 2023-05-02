package com.emerchantpay.emerchantpaypaymentsystem.service;

import java.util.List;
import java.util.Optional;
import javax.validation.ValidationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.MerchantEntity;
import com.emerchantpay.emerchantpaypaymentsystem.model.Merchant;
import com.emerchantpay.emerchantpaypaymentsystem.repository.MerchantRepository;
import com.emerchantpay.emerchantpaypaymentsystem.util.MerchantMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MerchantServiceImpl implements MerchantService {

  private final MerchantRepository merchantRepository;

  @Override
  public List<Merchant> getMerchants() {
    return MerchantMapper.merchantEntitesToDtos(merchantRepository.findAll());
  }

  @Override
  public void deleteMerchant(Long id) {
    try {
      deleteMerchantTransaction(id);
    } catch (DataIntegrityViolationException e) {
      throw new ValidationException(
          "The merchant has at least one transaction and cannot be deleted");
    } catch (EmptyResultDataAccessException ignored) {
    }
  }

  @Transactional
  private void deleteMerchantTransaction(Long id) {
    merchantRepository.deleteById(id);
  }

  @Override
  public void updateMerchant(Long id, Merchant merchant) {
    MerchantEntity newMerchantEntity = MerchantMapper.merchantDtoToEntity(merchant);
    newMerchantEntity.setId(id);

    Optional<MerchantEntity> merchantEntityOpt = merchantRepository.findById(id);

    if(merchantEntityOpt.isEmpty()){
      throw new ValidationException("Merchant not found!");
    }

    String password = merchantEntityOpt.get().getPassword();
    newMerchantEntity.setPassword(password);
    merchantRepository.save(newMerchantEntity);
  }
}
