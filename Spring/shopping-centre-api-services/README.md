# Shopping centre API

The Shopping Centre API is the Shopping malls interface for the customers, but also the shops
in the mall, to create orders, check delivery times and much more.

It serves as a demo project for webservices. Hopefully it will grow into something big.

## QuickStart

Build the project with the wrapper for maven:
    
    ./mvnw clean install

Then run:

    ./mvnw spring-boot:run

The wsdl is available at:

    http://localhost:8080/ws/bakery.wsdl

You can use the following curl command to test the endpoint from CLI:

    curl --header "Content-Type: text/xml;charset=UTF-8" \
         --data @soap.txt http://localhost:8080/ws     
