package eu.andreschepers.shoppingcentreapiservices.webservice;

import eu.andreschepers.shoppingcentreapiservices.BakeryRequestType;
import eu.andreschepers.shoppingcentreapiservices.BakeryResponseType;
import eu.andreschepers.shoppingcentreapiservices.ObjectFactory;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;

@Endpoint
@Component
public class BakeryEndpoint {

    private static final String NAMESPACE_URI = "https://www.andreschepers.eu/bakery/schemas";

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "BakeryRequest")
    public JAXBElement<BakeryResponseType> makeBakeryRequest(@RequestPayload BakeryRequestType request) {
        BakeryResponseType response = new BakeryResponseType();

        return new ObjectFactory().createBakeryResponse(response);
    }
}