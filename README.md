# ATM-simulator
ATM simulator made in Java, using three-tier architecture (client, server, database).

## Requirements

In order to run this program, you need to have a MySQL server installed in your system. You can download it [here](https://dev.mysql.com/downloads/).

## Running

In order to run the above program, you need to make the following steps:
* Run ```MySQLDatabase.java``` in order to create the database with the table and populate it with records.
* Run ```Server.java``` in order to start the server to listen for connections.
* Run ```Client.java``` multiple times to perform ATM operations.
