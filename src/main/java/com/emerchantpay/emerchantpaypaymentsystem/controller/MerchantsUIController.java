package com.emerchantpay.emerchantpaypaymentsystem.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emerchantpay.emerchantpaypaymentsystem.model.Merchant;
import com.emerchantpay.emerchantpaypaymentsystem.model.MerchantResponse;
import com.emerchantpay.emerchantpaypaymentsystem.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ui")
public class MerchantsUIController {

  private final MerchantService merchantService;

  @SuppressWarnings("unused")
  @GetMapping("/merchants")
  public MerchantResponse getMerchants() {
    return new MerchantResponse(merchantService.getMerchants());
  }

  @SuppressWarnings("unused")
  @DeleteMapping("/merchants/{id}")
  public ResponseEntity<Void> deleteMerchant(@PathVariable Long id) {
    merchantService.deleteMerchant(id);

    return ResponseEntity.noContent().build();
  }

  @SuppressWarnings("unused")
  @PutMapping("/merchants/{id}")
  ResponseEntity<Void> updateMerchant(@PathVariable Long id,
                                      @RequestBody @Valid Merchant merchant) {
    merchantService.updateMerchant(id, merchant);

    return ResponseEntity.noContent().build();
  }
}
