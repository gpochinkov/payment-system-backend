package com.emerchantpay.emerchantpaypaymentsystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.emerchantpay.emerchantpaypaymentsystem.entiry.AdminEntity;
import com.emerchantpay.emerchantpaypaymentsystem.entiry.CustomerEntity;
import com.emerchantpay.emerchantpaypaymentsystem.entiry.MerchantEntity;
import com.emerchantpay.emerchantpaypaymentsystem.model.EmerchantpayUserType;
import com.emerchantpay.emerchantpaypaymentsystem.model.MerchantStatus;
import com.emerchantpay.emerchantpaypaymentsystem.repository.AdminRepository;
import com.emerchantpay.emerchantpaypaymentsystem.repository.CustomerRepository;
import com.emerchantpay.emerchantpaypaymentsystem.repository.MerchantRepository;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unused")
@Slf4j
@Profile("csv")
@Component
public class CSVRunner implements CommandLineRunner {

  @Value("${csvlocation}")
  private String csvLocation;
  @Autowired
  private AdminRepository adminRepository;
  @Autowired
  private MerchantRepository merchantRepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {

    log.info("Ready for CSV fetch !");
    log.info("The file location is here: " + csvLocation);
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader(csvLocation));
    } catch (FileNotFoundException e) {
      log.error("There is something wrong with the directory or the csv file name!");
      return;
    }
    String line;
    int lineNumber = 1;

    List<MerchantEntity> merchants = new ArrayList<>();
    List<AdminEntity> admins = new ArrayList<>();
    List<CustomerEntity> customers = new ArrayList<>();

    while ((line = reader.readLine()) != null) {
      String[] values = line.split(",");
      if (values.length == 0) {
        continue;
      }
      if (values[0].equalsIgnoreCase(EmerchantpayUserType.MERCHANT.toString())) {
        if (values.length < 11) {
          log.error("On line {}, there are a less values than needed!", lineNumber);
          continue;
        }
        try {
          MerchantEntity merchant = new MerchantEntity(values[1],
                                                       passwordEncoder.encode(values[2]),
                                                       values[3],
                                                       Boolean.parseBoolean(values[4]),
                                                       Boolean.parseBoolean(values[5]),
                                                       Boolean.parseBoolean(values[6]),
                                                       Boolean.parseBoolean(values[7]),
                                                       values[8],
                                                       values[9].equalsIgnoreCase(
                                                           MerchantStatus.ACTIVE.toString())
                                                           ? MerchantStatus.ACTIVE
                                                           : MerchantStatus.INACTIVE,
                                                       new BigDecimal(values[10]));
          merchants.add(merchant);
        } catch (NumberFormatException e) {
          log.error("On line {}, the merchant's balance value must be a number!", lineNumber);
        }
      } else if (values[0].equalsIgnoreCase(EmerchantpayUserType.ADMIN.toString())) {
        if (values.length < 8) {
          log.error("On line {}, there are a less values than needed!", lineNumber);
          continue;
        }
        AdminEntity admin = new AdminEntity(values[1],
                                            passwordEncoder.encode(values[2]),
                                            values[3],
                                            Boolean.parseBoolean(values[4]),
                                            Boolean.parseBoolean(values[5]),
                                            Boolean.parseBoolean(values[6]),
                                            Boolean.parseBoolean(values[7]));
        admins.add(admin);
      } else if (values[0].equalsIgnoreCase(EmerchantpayUserType.CUSTOMER.toString())) {
        if (values.length < 10) {
          log.error("On line {}, there are a less values than needed!", lineNumber);
          continue;
        }
        try {
          CustomerEntity customer = new CustomerEntity(values[1],
                                                       passwordEncoder.encode(values[2]),
                                                       values[3],
                                                       Boolean.parseBoolean(values[4]),
                                                       Boolean.parseBoolean(values[5]),
                                                       Boolean.parseBoolean(values[6]),
                                                       Boolean.parseBoolean(values[7]),
                                                       values[8],
                                                       new BigDecimal(values[9]));
          customers.add(customer);
        } catch (NumberFormatException e) {
          log.error("On line {}, the customer's balance value must be a number!", lineNumber);
        }

      }
      lineNumber++;
    }

    adminRepository.saveAll(admins);
    merchantRepository.saveAll(merchants);
    customerRepository.saveAll(customers);

    reader.close();

    log.info("CSV fetched !");


    System.exit(0);

  }
}
