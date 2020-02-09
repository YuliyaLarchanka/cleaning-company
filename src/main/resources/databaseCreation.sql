CREATE DATABASE cleaning_company CHARACTER SET cp1251 COLLATE cp1251_general_ci;
USE cleaning_company;

CREATE TABLE account
(
  account_id INT PRIMARY KEY AUTO_INCREMENT,
  account_type TINYINT NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20),
  email VARCHAR(64) UNIQUE NOT NULL,
  account_password VARCHAR(64) NOT NULL,
  money DECIMAL(10,2) NOT NULL,
  phone_number VARCHAR(15),
  street VARCHAR(30),
  house VARCHAR(5),
  apartment VARCHAR(5),
  entrance VARCHAR(5),
  floor VARCHAR(5),
  intercom_code VARCHAR(10)
);

CREATE TABLE promo_code
(
  promo_code_id INT PRIMARY KEY AUTO_INCREMENT,
	promo_code_value VARCHAR(15) NOT NULL,
  discount_percentage TINYINT NOT NULL
);

CREATE TABLE catalog_item
(
  catalog_item_id INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  multiple_supported BOOL NOT NULL,
  is_active BOOL NOT NULL,
  image_name VARCHAR(30) NOT NULL
);

CREATE TABLE account_order
(
  account_order_id INT PRIMARY KEY AUTO_INCREMENT,
	account_id INT,
  promo_code_id INT,
	total_cost DECIMAL(10,2) NOT NULL,
  payment_method TINYINT NOT NULL,
  account_order_status TINYINT NOT NULL,
  date_time timestamp NOT NULL,
	FOREIGN KEY (account_id) REFERENCES account (account_id),
	FOREIGN KEY (promo_code_id) REFERENCES promo_code (promo_code_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE order_item
(
  order_item_id INT PRIMARY KEY AUTO_INCREMENT,
	catalog_item_id INT,
  account_order_id INT,
	amount TINYINT NOT NULL,
  FOREIGN KEY (catalog_item_id) REFERENCES catalog_item (catalog_item_id),
  FOREIGN KEY (account_order_id) REFERENCES account_order (account_order_id)
);