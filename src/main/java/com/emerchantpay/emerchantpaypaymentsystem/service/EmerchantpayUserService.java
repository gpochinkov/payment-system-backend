package com.emerchantpay.emerchantpaypaymentsystem.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.EmerchantpayUserEntity;
import com.emerchantpay.emerchantpaypaymentsystem.repository.EmerchantpayUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmerchantpayUserService implements UserDetailsService {

  private final EmerchantpayUserRepository emerchantpayUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<EmerchantpayUserEntity> users = emerchantpayUserRepository.findByUsername(username);
    return users.get(0);
  }
}
