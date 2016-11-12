FoodManager
===========

Daily food calorie tracker

Technologies:
* Postgres
* Java 8
* Guice
* JPA with Hibernate
* Jetty
* JAX-RS with Jersey
* Gson
* EmberCLI and Ember.js

===========
How to run
===========

1. Start postgres server
2. Run `database_structure.sql` to create database structure
3. Run ember build with `npm run build` to build front end app/javascript/css
4. Start jetty server with `./gradlew run -DconnectionString=<username>:<password>@<host>:<port>/<dbname> -DlocalDev=true`
5. Navigate to `http://localhost:8080/ui` to use app
