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

package eu.andreschepers.javawebmvcrestapidemo.rest;

import brave.Tracer;
import eu.andreschepers.javawebmvcrestapidemo.client.exception.ClientConnectionTimeoutException;
import eu.andreschepers.javawebmvcrestapidemo.configuration.RestConfigurationProperties;
import eu.andreschepers.javawebmvcrestapidemo.service.GoogleBooksApiService;
import eu.andreschepers.javawebmvcrestapidemo.util.LanguageCodeUtil;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@Import(TestConfigurationBooksTest.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Tracer tracer;

    @MockBean
    private RestConfigurationProperties properties;

    @MockBean
    private GoogleBooksApiService service;

    @Test
    void getBooks() throws Exception {
        when(properties.getMaxSearchTextLength()).thenReturn(20);
        mockMvc.perform(get("/books?searchText=james joyce&language=fr"))
            .andExpect(status().isOk());
        verify(service, times(1)).getBooks("fr", "james joyce");
    }

    @Test
    void testUnhandledExceptionCaught() throws Exception {
        when(properties.getMaxSearchTextLength()).thenReturn(20);
        when(service.getBooks(anyString(), anyString())).thenThrow(new ClientConnectionTimeoutException());
        String response = mockMvc.perform(get("/books?searchText=james joyce&language=fr"))
            .andExpect(status().is(HttpStatus.SC_SERVICE_UNAVAILABLE))
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("{\"message\":\"Service temporarily unavailable.\",\"traceId\":null}", response);
    }

    @Test
    void testConstraintViolationHandling() throws Exception {
        when(service.getBooks(anyString(), anyString())).thenThrow(new ClientConnectionTimeoutException());
        String response = mockMvc.perform(get("/books?searchText=james joyce&language=fr"))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("{\"message\":\"Invalid search text. Please check your search text that it is max [0] long and try again.\",\"traceId\":null}", response);

        when(properties.getMaxSearchTextLength()).thenReturn(20);
        response = mockMvc.perform(get("/books?searchText=james joyce&language=frr"))
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("{\"message\":\"Invalid language code. Make sure the given language code is a valid ISO-639-1 code.\",\"traceId\":null}", response);
    }
}

@TestConfiguration
class TestConfigurationBooksTest {

    @Bean
    public LanguageCodeUtil languageCodeUtil() {
        return new LanguageCodeUtil();
    }
}