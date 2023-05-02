INSERT INTO emerchantpay_user
(`type`, username, password, email, is_account_non_expired, is_account_non_locked,
 is_credentials_non_expired, is_enabled, description, status, total_transaction_sum, phone, balance)
VALUES ('CUSTOMER', 'johndoe', '$2a$10$guKLOIMY0qKG3wld6p41NeIMoiAKdZCXvrnK6f6WtNKmF5CQPw61W', 'johndoe@example.com', 1, 1, 1, 1,
        'A customer account', NULL, 0.00, '+123456789', 1000.00);

INSERT INTO emerchantpay_user
(`type`, username, password, email, is_account_non_expired, is_account_non_locked,
 is_credentials_non_expired, is_enabled, description, status, total_transaction_sum, phone, balance)
VALUES ('ADMIN', 'admin', '$2a$10$guKLOIMY0qKG3wld6p41NeIMoiAKdZCXvrnK6f6WtNKmF5CQPw61W',
        'admin@gmail.com', 1, 1, 1, 1, NULL, NULL, NULL, NULL, NULL);

INSERT INTO emerchantpay_user
(`type`, username, password, email, is_account_non_expired, is_account_non_locked,
 is_credentials_non_expired, is_enabled, description, status, total_transaction_sum, phone, balance)
VALUES ('MERCHANT', 'Exel', '$2a$10$X8e3l5rEPLOyLJTPByScreObsyZlcRRIKYiEUwURD.tQ1gFzB5Im.',
        'exel@gmail.com', 1, 1, 1, 1, 'exel description', 'ACTIVE', 0.00, NULL, NULL);

INSERT INTO emerchantpay.payment_transaction
(`type`, status, amount, customer_email, customer_phone, merchant_id, reference_id)
VALUES ("AUTHORIZE", 'APPROVED', 5.00, 'customer1@gmail.com', '898-989', 3, NULL);

INSERT INTO emerchantpay.payment_transaction
(`type`, status, amount, customer_email, customer_phone, merchant_id, reference_id)
VALUES ("CHARGE", 'APPROVED', 5.00, 'customer2@gmail.com', '11111-11', 3, 1);

INSERT INTO emerchantpay.payment_transaction
(`type`, status, amount, customer_email, customer_phone, merchant_id, reference_id)
VALUES ("CHARGE", 'APPROVED', 6.00, 'customer2@gmail.com', '11111-11', 3, 2);

INSERT INTO emerchantpay.payment_transaction
(`type`, status, amount, customer_email, customer_phone, merchant_id, reference_id)
VALUES ("CHARGE", 'APPROVED', 9.00, 'customer2@gmail.com', '11111-11', 3, 2);

INSERT INTO emerchantpay.payment_transaction
(`type`, status, amount, customer_email, customer_phone, merchant_id, reference_id)
VALUES ("CHARGE", 'APPROVED', 9.00, 'customer2@gmail.com', '11111-11', 3, null);

INSERT INTO emerchantpay.payment_transaction
(`type`, status, amount, customer_email, customer_phone, merchant_id, reference_id)
VALUES ("CHARGE", 'APPROVED', 95.00, 'customer2@gmail.com', '11111-11', 3, 4);