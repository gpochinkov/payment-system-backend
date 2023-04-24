package com.emerchantpay.emerchantpaypaymentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.MerchantEntity;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {
}
