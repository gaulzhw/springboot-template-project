--CREATE DATABASE test;
USE test;

--创建account表
CREATE TABLE IF NOT EXISTS `account`(
   `name` VARCHAR(20),
   `password` VARCHAR(20),
   `nick_name` VARCHAR(20),
   PRIMARY KEY ( `name` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;