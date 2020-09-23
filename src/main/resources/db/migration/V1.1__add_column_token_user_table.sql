SELECT count(*)
           INTO @exist
FROM information_schema.columns
WHERE table_schema = 'chat-realtime-db'
  and COLUMN_NAME = 'token'
  AND table_name = 'ofUser'
LIMIT 1;

set @query = IF(@exist <= 0,
                'ALTER TABLE `chat-realtime-db`.`ofUser`  ADD COLUMN `token` VARCHAR(250) NULL DEFAULT NULL AFTER `email`',
                'select \'Column Exists\' status');

prepare stmt from @query;

EXECUTE stmt;