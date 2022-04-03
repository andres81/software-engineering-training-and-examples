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

import eu.andreschepers.javawebmvcrestapidemo.client.dto.VolumeResources;
import eu.andreschepers.javawebmvcrestapidemo.client.exception.*;
import eu.andreschepers.javawebmvcrestapidemo.configuration.GoogleBooksApiClientProperties;
import io.netty.channel.ConnectTimeoutException;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class GoogleBookApiClient {

    private final WebClient webClient;
    private final GoogleBooksApiClientProperties properties;

    public VolumeResources getBooksResponse(String languageCode, String searchText) {
        try {
            return makeRequest(languageCode, searchText).block();
        } catch (WebClientRequestException ex) {
            if (ex.getCause() instanceof ReadTimeoutException) {
                log.error("Connection read timeout: [{}]", ex.getMessage());
                throw new ClientReadTimeoutException();
            } else if (ex.getCause() instanceof ConnectTimeoutException) {
                log.error("Connection setup timeout: [{}]", ex.getMessage());
                throw new ClientConnectionTimeoutException();
            }
            log.error("Something went wrong while makeing a request to Google Books API: [{}]", ex.getMessage(), ex);
            throw new ClientException();
        }
    }

    private Mono<VolumeResources> makeRequest(String languageCode, String searchText) {
        return webClient.get()
            .uri(properties.getUrl(), uri -> addQueryParams(uri, languageCode, searchText))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, this::handle4xxResponse)
            .onStatus(HttpStatus::is5xxServerError, this::handle5xxResponse)
            .bodyToMono(VolumeResources.class);
    }

    private URI addQueryParams(UriBuilder uri, String languageCode, String searchText) {
        if (languageCode != null) {
            uri.queryParam("langRestrict", languageCode);
        }
        return uri.queryParam("q", searchText)
            .queryParam("orderBy", "newest").build();
    }

    private Mono<? extends Throwable> handle4xxResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
            .map(errorResponse -> {
                log.error("Client HTTP error: [{}] - [{}]", errorResponse, clientResponse.statusCode());
                return new Client4xxException();
            });
    }

    private Mono<? extends Throwable> handle5xxResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
            .map(errorResponse -> {
                log.error("Server HTTP error: [{}] - [{}]", errorResponse, clientResponse.statusCode());
                return new Client5xxException();
            });
    }
}
