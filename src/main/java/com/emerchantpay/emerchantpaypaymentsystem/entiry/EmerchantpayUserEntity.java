package com.emerchantpay.emerchantpaypaymentsystem.entiry;

import java.util.Collection;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "emerchantpay_user")
@DiscriminatorColumn(name = "type",
                     discriminatorType = DiscriminatorType.STRING)
@SuperBuilder
public class EmerchantpayUserEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String password;
  private String email;
  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;

  public EmerchantpayUserEntity() {
  }

  public EmerchantpayUserEntity(
      String username,
      String password,
      String email,
      boolean isAccountNonExpired,
      boolean isAccountNonLocked,
      boolean isCredentialsNonExpired,
      boolean isEnabled) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.isAccountNonExpired = isAccountNonExpired;
    this.isAccountNonLocked = isAccountNonLocked;
    this.isCredentialsNonExpired = isCredentialsNonExpired;
    this.isEnabled = isEnabled;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
