package com.emerchantpay.emerchantpaypaymentsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.AdminEntity;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
  List<AdminEntity> findAll();
}
