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

package eu.andreschepers.javawebmvcrestapidemo.service;

import eu.andreschepers.javawebmvcrestapidemo.client.GoogleBookApiClient;
import eu.andreschepers.javawebmvcrestapidemo.client.dto.IndustryIdentifier;
import eu.andreschepers.javawebmvcrestapidemo.client.dto.VolumeInfo;
import eu.andreschepers.javawebmvcrestapidemo.client.dto.VolumeResource;
import eu.andreschepers.javawebmvcrestapidemo.client.dto.VolumeResources;
import io.swagger.client.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class GoogleBooksApiServiceTest {

    private static final String TITLE = "book title";
    private static final List<String> AUTHORS = List.of("James Joyce", "Ernest Hemingway");
    private static final String ISBN10 = "ISBN10";
    private static final String ISBN13 = "ISBN13";

    @Mock
    private GoogleBookApiClient client;

    @InjectMocks
    private GoogleBooksApiService googleBooksApiService;

    @Test
    void testSuccessfulRetrievalOfBooks() {
        when(client.getBooksResponse(anyString(), anyString())).thenReturn(createVolumeResources());
        List<Book> books = googleBooksApiService.getBooks("", "");
        assertEquals(TITLE, books.get(0).getTitel());
        assertEquals("01 January 1234", books.get(0).getPublicatieDatum());
        assertTrue(books.get(0).getAuteurs().containsAll(AUTHORS));
        assertEquals(ISBN10, books.get(0).getIsbn10());
        assertEquals(ISBN13, books.get(0).getIsbn13());
    }

    @Test
    void testNullScenarios() {
        VolumeResources volumeResources = createVolumeResources();

        VolumeResource volumeResource = volumeResources.getItems().get(0);
        volumeResource.getVolumeInfo().setIndustryIdentifiers(null);
        volumeResource.getVolumeInfo().setAuthors(null);
        when(client.getBooksResponse(anyString(), anyString())).thenReturn(volumeResources);
        List<Book> books = googleBooksApiService.getBooks("", "");
        assertNull(books.get(0).getAuteurs());
        assertNull(books.get(0).getIsbn10());
        assertNull(books.get(0).getIsbn13());

        volumeResource.setVolumeInfo(null);
        assertTrue(googleBooksApiService.getBooks("", "").isEmpty());

        volumeResources.setItems(null);
        assertTrue(googleBooksApiService.getBooks("", "").isEmpty());

        when(client.getBooksResponse(anyString(), anyString())).thenReturn(null);
        assertTrue(googleBooksApiService.getBooks("", "").isEmpty());
    }

    private VolumeResources createVolumeResources() {
        VolumeResources volumeResources = new VolumeResources();
        volumeResources.setItems(createVolumeResourceList());
        return volumeResources;
    }

    private List<VolumeResource> createVolumeResourceList() {
        VolumeResource vr = new VolumeResource();
        VolumeInfo volumeInfo = new VolumeInfo();
        volumeInfo.setPublishedDate("1234-01-01");
        volumeInfo.setTitle(TITLE);
        volumeInfo.setAuthors(AUTHORS);
        volumeInfo.setIndustryIdentifiers(createIndustryIdentifiers());
        vr.setVolumeInfo(volumeInfo);
        return List.of(vr);
    }

    private List<IndustryIdentifier> createIndustryIdentifiers() {
        IndustryIdentifier ii = new IndustryIdentifier();
        ii.setType("ISBN_10");
        ii.setIdentifier(ISBN10);
        IndustryIdentifier ii2 = new IndustryIdentifier();
        ii2.setType("ISBN_13");
        ii2.setIdentifier(ISBN13);
        return List.of(ii, ii2);
    }
}
