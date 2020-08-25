
CREATE TABLE IF NOT EXISTS `ofExtComponentConf` (
  `subdomain` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `wildcard` tinyint(4) NOT NULL,
  `secret` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `permission` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`subdomain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofExtComponentConf` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofGroup`
--

CREATE TABLE IF NOT EXISTS `ofGroup` (
  `groupName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`groupName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofGroup` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofGroupProp`
--

CREATE TABLE IF NOT EXISTS `ofGroupProp` (
  `groupName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `propValue` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`groupName`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofGroupProp` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofGroupUser`
--

CREATE TABLE IF NOT EXISTS `ofGroupUser` (
  `groupName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `administrator` tinyint(4) NOT NULL,
  PRIMARY KEY (`groupName`,`username`,`administrator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofGroupUser` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofID`
--


CREATE TABLE IF NOT EXISTS `ofID` (
  `idType` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


LOCK TABLES `ofID` WRITE;
INSERT INTO `ofID` VALUES (18,1),(19,1),(23,1),(25,6),(26,2),(27,1);
UNLOCK TABLES;

--
-- Table structure for table `ofMucAffiliation`
--

CREATE TABLE IF NOT EXISTS `ofMucAffiliation` (
  `roomID` bigint(20) NOT NULL,
  `jid` text COLLATE utf8_unicode_ci NOT NULL,
  `affiliation` tinyint(4) NOT NULL,
  PRIMARY KEY (`roomID`,`jid`(70))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofMucAffiliation` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofMucConversationLog`
--
CREATE TABLE IF NOT EXISTS `ofMucConversationLog` (
  `roomID` bigint(20) NOT NULL,
  `messageID` bigint(20) NOT NULL,
  `sender` text COLLATE utf8_unicode_ci NOT NULL,
  `nickname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `logTime` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `subject` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `body` text COLLATE utf8_unicode_ci,
  `stanza` text COLLATE utf8_unicode_ci,
  KEY `ofMucConversationLog_time_idx` (`logTime`),
  KEY `ofMucConversationLog_msg_id` (`messageID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `ofMucConversationLog`
--

LOCK TABLES `ofMucConversationLog` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofMucMember`
--

CREATE TABLE IF NOT EXISTS `ofMucMember` (
  `roomID` bigint(20) NOT NULL,
  `jid` text COLLATE utf8_unicode_ci NOT NULL,
  `nickname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `firstName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `faqentry` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`roomID`,`jid`(70))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofMucMember` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofMucRoom`
--
CREATE TABLE IF NOT EXISTS `ofMucRoom` (
  `serviceID` bigint(20) NOT NULL,
  `roomID` bigint(20) NOT NULL,
  `creationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `modificationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `naturalName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lockedDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `emptyDate` char(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `canChangeSubject` tinyint(4) NOT NULL,
  `maxUsers` int(11) NOT NULL,
  `publicRoom` tinyint(4) NOT NULL,
  `moderated` tinyint(4) NOT NULL,
  `membersOnly` tinyint(4) NOT NULL,
  `canInvite` tinyint(4) NOT NULL,
  `roomPassword` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `canDiscoverJID` tinyint(4) NOT NULL,
  `logEnabled` tinyint(4) NOT NULL,
  `subject` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rolesToBroadcast` tinyint(4) NOT NULL,
  `useReservedNick` tinyint(4) NOT NULL,
  `canChangeNick` tinyint(4) NOT NULL,
  `canRegister` tinyint(4) NOT NULL,
  `allowpm` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`serviceID`,`name`),
  KEY `ofMucRoom_roomid_idx` (`roomID`),
  KEY `ofMucRoom_serviceid_idx` (`serviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofMucRoom` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofMucRoomProp` (
  `roomID` bigint(20) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `propValue` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`roomID`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofMucRoomProp` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofMucService`
--

CREATE TABLE IF NOT EXISTS `ofMucService` (
  `serviceID` bigint(20) NOT NULL,
  `subdomain` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isHidden` tinyint(4) NOT NULL,
  PRIMARY KEY (`subdomain`),
  KEY `ofMucService_serviceid_idx` (`serviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofMucService` WRITE;
INSERT INTO `ofMucService` VALUES (1,'conference',NULL,0);
UNLOCK TABLES;

--
-- Table structure for table `ofMucServiceProp`
--

CREATE TABLE IF NOT EXISTS `ofMucServiceProp` (
  `serviceID` bigint(20) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `propValue` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`serviceID`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofMucServiceProp` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofOffline`
--

CREATE TABLE IF NOT EXISTS `ofOffline` (
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `messageID` bigint(20) NOT NULL,
  `creationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `messageSize` int(11) NOT NULL,
  `stanza` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`username`,`messageID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofOffline` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofPresence`
--

CREATE TABLE IF NOT EXISTS `ofPresence` (
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `offlinePresence` text COLLATE utf8_unicode_ci,
  `offlineDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


LOCK TABLES `ofPresence` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofPrivacyList`
--

CREATE TABLE IF NOT EXISTS `ofPrivacyList` (
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `isDefault` tinyint(4) NOT NULL,
  `list` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`username`,`name`),
  KEY `ofPrivacyList_default_idx` (`username`,`isDefault`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofPrivacyList` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `ofProperty`
--

CREATE TABLE IF NOT EXISTS `ofProperty` (
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `propValue` text COLLATE utf8_unicode_ci NOT NULL,
  `encrypted` int(11) DEFAULT NULL,
  `iv` char(24) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofProperty` WRITE;
INSERT INTO `ofProperty` VALUES ('passwordKey','gdkBPRX82nY2Sw4',0,NULL),('plugin.restapi.allowedIPs','',0,NULL),('plugin.restapi.enabled','true',0,NULL),('plugin.restapi.httpAuth','secret',0,NULL),('plugin.restapi.secret','F0Q8t0rOY8hhodIe',0,NULL),('plugin.restapi.serviceLoggingEnabled','true',0,NULL),('provider.admin.className','org.jivesoftware.openfire.admin.DefaultAdminProvider',0,NULL),('provider.auth.className','org.jivesoftware.openfire.auth.DefaultAuthProvider',0,NULL),('provider.group.className','org.jivesoftware.openfire.group.DefaultGroupProvider',0,NULL),('provider.lockout.className','org.jivesoftware.openfire.lockout.DefaultLockOutProvider',0,NULL),('provider.securityAudit.className','org.jivesoftware.openfire.security.DefaultSecurityAuditProvider',0,NULL),('provider.user.className','org.jivesoftware.openfire.user.DefaultUserProvider',0,NULL),('provider.vcard.className','org.jivesoftware.openfire.vcard.DefaultVCardProvider',0,NULL),('update.lastCheck','1598252555354',0,NULL),('xmpp.auth.anonymous','false',0,NULL),('xmpp.domain','dimagesharevn.develop',0,NULL),('xmpp.socket.ssl.active','true',0,NULL);

UNLOCK TABLES;


CREATE TABLE IF NOT EXISTS `ofPubsubAffiliation` (
  `serviceID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `nodeID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `jid` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `affiliation` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`serviceID`,`nodeID`,`jid`(70))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofPubsubAffiliation` WRITE;
INSERT INTO `ofPubsubAffiliation` VALUES ('pubsub','','dimagesharevn.develop','owner');
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofPubsubDefaultConf` (
  `serviceID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `leaf` tinyint(4) NOT NULL,
  `deliverPayloads` tinyint(4) NOT NULL,
  `maxPayloadSize` int(11) NOT NULL,
  `persistItems` tinyint(4) NOT NULL,
  `maxItems` int(11) NOT NULL,
  `notifyConfigChanges` tinyint(4) NOT NULL,
  `notifyDelete` tinyint(4) NOT NULL,
  `notifyRetract` tinyint(4) NOT NULL,
  `presenceBased` tinyint(4) NOT NULL,
  `sendItemSubscribe` tinyint(4) NOT NULL,
  `publisherModel` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `subscriptionEnabled` tinyint(4) NOT NULL,
  `accessModel` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `language` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `replyPolicy` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `associationPolicy` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `maxLeafNodes` int(11) NOT NULL,
  PRIMARY KEY (`serviceID`,`leaf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofPubsubDefaultConf` WRITE;
INSERT INTO `ofPubsubDefaultConf` VALUES ('pubsub',0,0,0,0,0,1,1,1,0,0,'publishers',1,'open','English',NULL,'all',-1),('pubsub',1,1,5120,0,1,1,1,1,0,1,'publishers',1,'open','English',NULL,'all',-1);
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofPubsubItem` (
  `serviceID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `nodeID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `jid` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `creationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `payload` mediumtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`serviceID`,`nodeID`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofPubsubItem` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofPubsubNode` (
  `serviceID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `nodeID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `leaf` tinyint(4) NOT NULL,
  `creationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `modificationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `parent` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deliverPayloads` tinyint(4) NOT NULL,
  `maxPayloadSize` int(11) DEFAULT NULL,
  `persistItems` tinyint(4) DEFAULT NULL,
  `maxItems` int(11) DEFAULT NULL,
  `notifyConfigChanges` tinyint(4) NOT NULL,
  `notifyDelete` tinyint(4) NOT NULL,
  `notifyRetract` tinyint(4) NOT NULL,
  `presenceBased` tinyint(4) NOT NULL,
  `sendItemSubscribe` tinyint(4) NOT NULL,
  `publisherModel` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `subscriptionEnabled` tinyint(4) NOT NULL,
  `configSubscription` tinyint(4) NOT NULL,
  `accessModel` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `payloadType` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `bodyXSLT` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dataformXSLT` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `creator` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `language` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `replyPolicy` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `associationPolicy` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `maxLeafNodes` int(11) DEFAULT NULL,
  PRIMARY KEY (`serviceID`,`nodeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofPubsubNode` WRITE;
INSERT INTO `ofPubsubNode` VALUES ('pubsub','',0,'001598252515640','001598252515640',NULL,0,0,0,0,1,1,1,0,0,'publishers',1,0,'open','','','','dimagesharevn.develop','','English','',NULL,'all',-1);
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofPubsubNodeGroups` (
  `serviceID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `nodeID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `rosterGroup` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  KEY `ofPubsubNodeGroups_idx` (`serviceID`,`nodeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


LOCK TABLES `ofPubsubNodeGroups` WRITE;

UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofPubsubNodeJIDs` (
  `serviceID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `nodeID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `jid` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `associationType` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`serviceID`,`nodeID`,`jid`(70))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


LOCK TABLES `ofPubsubNodeJIDs` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofPubsubSubscription` (
  `serviceID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `nodeID` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `id` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `jid` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `owner` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `state` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `deliver` tinyint(4) NOT NULL,
  `digest` tinyint(4) NOT NULL,
  `digest_frequency` int(11) NOT NULL,
  `expire` char(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `includeBody` tinyint(4) NOT NULL,
  `showValues` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `subscriptionType` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `subscriptionDepth` tinyint(4) NOT NULL,
  `keyword` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`serviceID`,`nodeID`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofPubsubSubscription` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofRemoteServerConf` (
  `xmppDomain` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `remotePort` int(11) DEFAULT NULL,
  `permission` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`xmppDomain`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


LOCK TABLES `ofRemoteServerConf` WRITE;

UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofRoster` (
  `rosterID` bigint(20) NOT NULL,
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `jid` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `sub` tinyint(4) NOT NULL,
  `ask` tinyint(4) NOT NULL,
  `recv` tinyint(4) NOT NULL,
  `nick` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`rosterID`),
  KEY `ofRoster_unameid_idx` (`username`),
  KEY `ofRoster_jid_idx` (`jid`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofRoster` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofRosterGroups` (
  `rosterID` bigint(20) NOT NULL,
  `rank` tinyint(4) NOT NULL,
  `groupName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`rosterID`,`rank`),
  KEY `ofRosterGroup_rosterid_idx` (`rosterID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofRosterGroups` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofSASLAuthorized` (
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `principal` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`username`,`principal`(200))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


LOCK TABLES `ofSASLAuthorized` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofSecurityAuditLog` (
  `msgID` bigint(20) NOT NULL,
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `entryStamp` bigint(20) NOT NULL,
  `summary` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `node` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `details` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`msgID`),
  KEY `ofSecurityAuditLog_tstamp_idx` (`entryStamp`),
  KEY `ofSecurityAuditLog_uname_idx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


LOCK TABLES `ofSecurityAuditLog` WRITE;
INSERT INTO `ofSecurityAuditLog` VALUES (1,'admin',1598252533605,'Successful admin console login attempt','dimagesharevn.develop','The user logged in successfully to the admin console from address 0:0:0:0:0:0:0:1. '),(2,'admin',1598253942274,'created new user my','dimagesharevn.develop','name = ha my, email = my@gmail.com, admin = false'),(3,'admin',1598259955257,'Successful admin console login attempt','dimagesharevn.develop','The user logged in successfully to the admin console from address 0:0:0:0:0:0:0:1. '),(4,'admin',1598259978494,'reloaded plugin restapi','dimagesharevn.develop',NULL),(5,'admin',1598319760208,'Successful admin console login attempt','dimagesharevn.develop','The user logged in successfully to the admin console from address 0:0:0:0:0:0:0:1. ');
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofUser` (
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `storedKey` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `serverKey` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `salt` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `iterations` int(11) DEFAULT NULL,
  `plainPassword` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `encryptedPassword` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `creationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  `modificationDate` char(15) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`username`),
  KEY `ofUser_cDate_idx` (`creationDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofUser` WRITE;
INSERT INTO `ofUser` VALUES ('admin','piOob0DlXlHD84uRtLyPENmrads=','L5InkxVExSbOc1BXvoxMLXK9waU=','wNFU7JM87YAsC46s1TTW3pnx7PLA7ozA',4096,NULL,'d314de0266f2b7089a0eb7dd49a610c6edf0583c2d42132d55ac96a916b823cb659c3ddeae1ae029','Administrator','admin@example.com','001598252513698','0');
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofUserFlag` (
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `startTime` char(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `endTime` char(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`username`,`name`),
  KEY `ofUserFlag_sTime_idx` (`startTime`),
  KEY `ofUserFlag_eTime_idx` (`endTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
LOCK TABLES `ofUserFlag` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofUserProp` (
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `propValue` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`username`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofUserProp` WRITE;
INSERT INTO `ofUserProp` VALUES ('admin','console.rows_per_page','/session-summary.jsp=25');
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofVCard` (
  `username` varchar(64) COLLATE utf8_unicode_ci NOT NULL,
  `vcard` mediumtext COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `ofVCard` WRITE;
UNLOCK TABLES;

CREATE TABLE IF NOT EXISTS `ofVersion` (
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


LOCK TABLES `ofVersion` WRITE;
INSERT INTO `ofVersion` VALUES ('openfire',30);

UNLOCK TABLES;