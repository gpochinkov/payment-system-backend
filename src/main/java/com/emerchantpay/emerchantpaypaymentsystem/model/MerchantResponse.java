package com.emerchantpay.emerchantpaypaymentsystem.model;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class MerchantResponse {
  private final List<Merchant> merchants;
}
