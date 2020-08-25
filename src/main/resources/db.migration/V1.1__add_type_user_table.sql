ALTER TABLE `chat-realtime-db`.`ofUser`
ADD COLUMN `type` VARCHAR(45) NULL DEFAULT NULL AFTER `modificationDate`;