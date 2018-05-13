# Lion villain :zap:
The game we are developing is a kind of math game, which can be played competitively with a friend or play alone. The object of the game is to be the first person to push a lion across to another village.  

## Member :two_women_holding_hands:
1. Kwankaew Uttama (https://github.com/ploykwan)
2. Pimwalun Witchawanitchanun (https://github.com/pimwalun)

## Vision of program :computer:
### How to run
1 . If you want to be as a host of game, open ServerLauncher.jar
2. Open GameLauncher.jar, Insert IP of server, Enjoy it!

### Require Library
- mysql-connector-java-5.1.46-bin.jar - https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-installing.html
- ormlite-core-5.1.jar & ormlite-jdbc-5.1.jar - http://ormlite.com/releases/
- kryonet-2.21-all.jar - https://github.com/EsotericSoftware/kryonet

### Design Patterns
⁃ We use **object pool design pattern** concept but we use *Singleton* to create.
⁃ We use **Observer design pattern** in OnlineGame for sent a data from program to server and client, and use in OnePlayer for completed the object pool design pattern.
