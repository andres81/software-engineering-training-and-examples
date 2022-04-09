/*
 * Copyright (C) 2022 André Schepers
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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