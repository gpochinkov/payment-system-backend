CREATE TABLE `emerchantpay_user`
(
    `id`                         bigint auto_increment,
    `type`                       varchar(30) NOT NULL,
    `username`                   varchar(255)         DEFAULT NULL,
    `password`                   varchar(255)         DEFAULT NULL,
    `email`                      varchar(255)         DEFAULT NULL,
    `is_account_non_expired`     bit(1)      NOT NULL,
    `is_account_non_locked`      bit(1)      NOT NULL,
    `is_credentials_non_expired` bit(1)      NOT NULL,
    `is_enabled`                 bit(1)      NOT NULL,
    `description`                varchar(255)         DEFAULT NULL,
    `status`                     varchar(30)          DEFAULT NULL,
    `total_transaction_sum`      decimal(19, 2)       DEFAULT NULL,
    `phone`                      varchar(255)         DEFAULT NULL,
    `balance`                    decimal(19, 2)       DEFAULT NULL,
    `creation_time`              datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_update_time`           datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `payment_transaction`
(
    `id`               bigint auto_increment,
    `uuid`             varchar(36)          DEFAULT (UUID()),
    `type`             varchar(30) NOT NULL,
    `status`           varchar(30)          DEFAULT NULL,
    `amount`           decimal(19, 2)       DEFAULT NULL,
    `customer_email`   varchar(255)         DEFAULT NULL,
    `customer_phone`   varchar(255)         DEFAULT NULL,
    `merchant_id`      bigint      NOT NULL,
    `reference_id`     bigint               DEFAULT NULL,
    `creation_time`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `payment_transaction_uuid` (`uuid`),
    CONSTRAINT `payment_transaction_emerchantpay_user_fk` FOREIGN KEY (`merchant_id`) REFERENCES `emerchantpay_user` (`id`),
    CONSTRAINT `payment_transaction_payment_transaction_fk` FOREIGN KEY (`reference_id`) REFERENCES `payment_transaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
