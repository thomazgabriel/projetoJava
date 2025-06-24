para empacotar mvn -s settings.xml clean package

para compilar e executar mvn -s settings.xml clean compile exec:java

baixar sqlite

baixar driver sqlite

https://github.com/xerial/sqlite-jdbc/releases

mover driver para dist/

executar:

java -cp ".;sqlite-jdbc-3.50.1.0.jar" Main