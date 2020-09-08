# docker-compose openfire

This docker setup contains a stack with:
* openfire, mysql, redis

## What is Openfire?
Openfire is a real time collaboration (RTC) server licensed under the Open Source Apache License. It uses the only widely adopted open protocol for instant messaging, XMPP (also called Jabber). (https://www.igniterealtime.org/projects/openfire/)

## Step by step build enviroment
* First you need install docker that's for your os (windows, linux, macos...)
* Now, we start building enviroment:
* cd to root project and run cmd:
```bash
$ mvn clean install -DskipTests
$ docker-compose up --build
```
(Note: If you want to run app like hide process, use:)
```bash
$ docker-compose up -d --build
```
* After enviroment built, we can wait more 30s - 1 min (cause mysql can need little time for setup)
* Openfire Admin Console > http://localhost:9090 
##### Connection information: 
##### XMPP Domain Name:	
##### dimagesharevn.develop
##### Server Host Name (FQDN):
##### dimagesharevn.develop
##### Mysql connection: 
##### jdbc:mysql://mysql_database:3306/chat-realtime-db?rewriteBatchedStatements=true 
##### username: chat-app
##### password: dimagesharevn
(You can set up admin account controller like this: email: admin@example.com/ password: dimagesharevn). Then, when login admin controller, username/password is: admin/dimagesharevn

* You need install REST API plugin : In tab Plugins, click Avaiables plugin, find REST API plugin and click (+) to add plugin
* Click Tabl Server > Server setting > REST API and tick:
###### Enabled - REST API requests will be processed
###### Tick Secret key auth and fill key string: F0Q8t0rOY8hhodIe
###### Tick Enabled - Logging Enabled
###### Save setting

***Now we can run app on base endpoint: http://localhost:8080/api/ and enjoy !***
## Destroy each other container (recommendation)
**Note: Because our container has enviroments like openfire, mysql, redis data, so you might keep it, and only remove "chat-app" If you 're really need . Now, if you remove each container, first, you need getting its id. Run:**
```bash
$ docker ps
```
After get container id, you stop, remove by:
```bash
$ docker container stop [id]
```

Remove by id:
```bash
$ docker container rm [id]
```

## Destropy all container
Oke, if you're needing remove all container for restart, you can choice once options bellow:
* If you use "docker" commandline:

* If you use "docker-compose" commandline, choice once options:
```bash
$ docker-compose down --rmi all
$ docker-compose down -v
$ docker-compose rm
Stop all:
$ docker-compose stop

```