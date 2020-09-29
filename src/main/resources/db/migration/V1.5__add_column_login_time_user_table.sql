SELECT count(*)
           INTO @exist
FROM information_schema.columns
WHERE table_schema = 'chat-realtime-db'
  and COLUMN_NAME = 'loginTime'
  AND table_name = 'ofUser'
LIMIT 1;

set @query = IF(@exist <= 0,
                'ALTER TABLE `chat-realtime-db`.`ofUser`  ADD COLUMN `loginTime` Bigint(20) NULL DEFAULT NULL AFTER `logoutTime`',
                'select \'Column Exists\' status');

prepare stmt from @query;

EXECUTE stmt;