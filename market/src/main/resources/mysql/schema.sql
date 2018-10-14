CREATE DATABASE IF NOT EXISTS market;

ALTER DATABASE market
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE market;


CREATE TABLE IF NOT EXISTS user(
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_name VARCHAR(30),
  password VARCHAR(30),
  email VARCHAR(30),
  INDEX(user_name)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS product(
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  product_name VARCHAR(30) UNIQUE,
  price DOUBLE,
  stock INTEGER,
  INDEX(product_name)
) engine=InnoDB;

