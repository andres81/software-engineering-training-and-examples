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

package eu.andreschepers.javawebmvcrestapidemo.configuration;

import eu.andreschepers.javawebmvcrestapidemo.util.KeyStoreUtil;
import eu.andreschepers.javawebmvcrestapidemo.util.X509CertificateUtil;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({GoogleBooksApiClientProperties.class, RestConfigurationProperties.class})
public class GoogleClientConfiguration {

    private final KeyStoreUtil keyStoreUtil;
    private final X509CertificateUtil certificateUtil;
    private final GoogleBooksApiClientProperties properties;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector((httpClient())))
            .build();
    }

    private HttpClient httpClient() {
        Timeout timeout = properties.getTimeout();
        return HttpClient.create(clientConnectionProvider())
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout.getConnectionMs())
            .secure(t -> t.sslContext(sslContext()))
            .responseTimeout(Duration.ofMillis(timeout.getReadMs()))
            .doOnConnected(conn ->
                conn.addHandlerLast(new WriteTimeoutHandler(timeout.getReadMs(), TimeUnit.MILLISECONDS))
            );
    }

    private ConnectionProvider clientConnectionProvider() {
        return ConnectionProvider.builder("custom")
            .maxConnections(properties.getMaxConnections())
            .pendingAcquireTimeout(Duration.ofSeconds(properties.getTimeout().getConnectionRequestMs()))
            .build();
    }

    private SslContext sslContext() {
        try {
            TrustManagerFactory tmfactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmfactory.init(truststore());
            return SslContextBuilder.forClient()
                .trustManager(tmfactory)
                .build();
        } catch (Exception e) {
            log.error("SslContext could not be initialised.", e);
            throw new IllegalStateException();
        }
    }

    private KeyStore truststore() {
        X509Certificate x509Certificate = certificateUtil.certificateFromString(properties.getServercertificate());
        return keyStoreUtil.trustStore("alias", "password", x509Certificate);
    }
}
