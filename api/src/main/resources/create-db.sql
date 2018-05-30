--创建account表
CREATE TABLE IF NOT EXISTS `account`(
   `name` VARCHAR(20),
   `password` VARCHAR(20),
   PRIMARY KEY ( `name` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;