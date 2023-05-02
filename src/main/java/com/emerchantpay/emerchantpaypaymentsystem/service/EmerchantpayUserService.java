package com.emerchantpay.emerchantpaypaymentsystem.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.EmerchantpayUserEntity;
import com.emerchantpay.emerchantpaypaymentsystem.repository.EmerchantpayUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmerchantpayUserService implements UserDetailsService {

  private final EmerchantpayUserRepository emerchantpayUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<EmerchantpayUserEntity> users = emerchantpayUserRepository.findByUsername(username);

    if (users.size() > 0) {
      return users.get(0);
    }

    log.error("No such username!");
    return null;
  }
}
