CREATE TABLE IF NOT EXISTS `chat-realtime-db`.`ofProfile` (
  `username` VARCHAR(64) NOT NULL,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(255) NULL,
  `avatar` VARCHAR(255) NULL,
  PRIMARY KEY (`username`));
