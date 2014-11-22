kill -9 $( lsof -i :8080 -t )
mvn tomcat7:run
