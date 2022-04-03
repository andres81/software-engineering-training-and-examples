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

package eu.andreschepers.javawebmvcrestapidemo.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import eu.andreschepers.javawebmvcrestapidemo.client.dto.VolumeResources;
import eu.andreschepers.javawebmvcrestapidemo.client.exception.Client4xxException;
import eu.andreschepers.javawebmvcrestapidemo.client.exception.Client5xxException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class GoogleBookApiClientTest {

    private static final int API_PORT = 5678;
    private static final String SERVER_ERROR_RESPONSE = "CAUSE_SERVER_ERROR_RESPONSE";
    private static final String CLIENT_ERROR_RESPONSE = "CAUSE_CLIENT_ERROR_RESPONSE";
    private static final String PROPER_SEARCH_STRING = "PROPER_SEARCH_STRING";
    private static final String SUCCESSFUL_RESPONSE_BODY = "{\"items\": []}";

    private static WireMockServer wireMockServer;

    @Autowired
    private GoogleBookApiClient client;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(
            wireMockConfig()
                .port(API_PORT)
                .notifier(new ConsoleNotifier(true)));
        wireMockServer.start();

        wireMockServer.stubFor(get(urlPathMatching("/"))
            .withQueryParam("q", matching(PROPER_SEARCH_STRING))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withStatus(HttpStatus.OK.value())
                .withBody(SUCCESSFUL_RESPONSE_BODY)));

        wireMockServer.stubFor(get(urlPathMatching("/"))
            .withQueryParam("q", matching(CLIENT_ERROR_RESPONSE))
            .willReturn(aResponse()
                .withStatus(HttpStatus.BAD_REQUEST.value())
                .withBody("Something went wrong with your request!")));

        wireMockServer.stubFor(get(urlPathMatching("/"))
            .withQueryParam("q", matching(SERVER_ERROR_RESPONSE))
            .willReturn(aResponse()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .withBody("Something went wrong server side!")));
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @Test
    void givenValidRequest_whenServerSuccesfullyResponse_thenProperReturnValid() {
        VolumeResources volumeResources = client.getBooksResponse(null, PROPER_SEARCH_STRING);
        assertTrue(volumeResources.getItems().isEmpty());
    }

    @Test
    void givenValidRequest_whenServerSideError_thenProperResponseHandling() {
        assertThrows(Client5xxException.class, () -> client.getBooksResponse(null, SERVER_ERROR_RESPONSE));
    }

    @Test
    void givenInvalidRequest_thenProperResponseHandling() {
        assertThrows(Client4xxException.class, () -> client.getBooksResponse(null, CLIENT_ERROR_RESPONSE));
    }
}