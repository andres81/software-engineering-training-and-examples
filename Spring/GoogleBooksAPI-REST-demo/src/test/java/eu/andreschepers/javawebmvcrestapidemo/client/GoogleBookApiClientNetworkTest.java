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
import eu.andreschepers.javawebmvcrestapidemo.client.exception.ClientReadTimeoutException;
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

@Slf4j
@SpringBootTest(properties={"client.google.url=http://localhost:1234"})
@ActiveProfiles("networktest")
class GoogleBookApiClientNetworkTest {

    private static final int API_PORT = 1234;
    private static final String PROPER_SEARCH_STRING = "PROPER_SEARCH_STRING";
    private static final String SUCCESSFUL_RESPONSE_BODY = "SUCCESSFUL_RESPONSE_BODY";

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
                .withFixedDelay(11)
                .withStatus(HttpStatus.OK.value())
                .withBody(SUCCESSFUL_RESPONSE_BODY)));
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @Test
    void givenValidRequest_whenServerSuccesfullyResponse_thenProperReturnValid() {
        assertThrows(ClientReadTimeoutException.class, () -> client.getBooksResponse("en", PROPER_SEARCH_STRING));
    }
}