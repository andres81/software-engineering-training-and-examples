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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoogleBooksApiService {

    private static final int LENGTH_DATE_YEAR = 4;
    private static final int LENGTH_YEAR_MONTH = 7;
    private static final String ISBN10 = "ISBN_10";
    private static final String ISBN13 = "ISBN_13";

    private final GoogleBookApiClient client;

    public List<Book> getBooks(String languageCode, String searchText) {
        VolumeResources response = client.getBooksResponse(languageCode, searchText);
        if (response == null || response.getItems() == null) {
            return Collections.emptyList();
        }
        return response.getItems().stream()
            .map(VolumeResource::getVolumeInfo)
            .map(this::processVolumeResource)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private Book processVolumeResource(VolumeInfo volumeInfo) {
        Book book = new Book();
        if (volumeInfo == null) {
            return null;
        }
        book.setAuteurs(volumeInfo.getAuthors());
        processIsbns(book, volumeInfo.getIndustryIdentifiers());
        book.setTitel(volumeInfo.getTitle());
        book.setPublicatieDatum(getPublicationDateString(volumeInfo.getPublishedDate()));
        return book;
    }

    private String getPublicationDateString(String publishedDate) {
        if (publishedDate == null) {
            return null;
        }
        if (publishedDate.length() == LENGTH_DATE_YEAR) {
            return publishedDate;
        } else if (publishedDate.length() >= LENGTH_YEAR_MONTH) {
            String year = publishedDate.substring(0, 4);
            String month = publishedDate.substring(5, 7);
            StringBuilder sb = new StringBuilder("");
            if (publishedDate.length() > LENGTH_YEAR_MONTH) {
                String day = publishedDate.substring(8, 10);
                sb.append(day).append(" ");
            }
            return sb.append(processMonth(month))
                .append(" ")
                .append(year)
                .toString();
        } else {
            log.info("Returning null: [{}]", publishedDate);
            return null;
        }
    }

    private String processMonth(String month) {
        int index;
        try {
            index = Integer.parseInt(month);
        } catch (Exception ex) {
            log.error("Could not process month value: [{}]", month);
            return "_";
        }
        return Month.of(index).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    private void processIsbns(Book book, List<IndustryIdentifier> industryIdentifiers) {
        if (industryIdentifiers == null) {
            return;
        }
        industryIdentifiers.forEach(id -> {
            if (ISBN10.equals(id.getType())) {
                book.setIsbn10(id.getIdentifier());
            } else if (ISBN13.equals(id.getType())) {
                book.setIsbn13(id.getIdentifier());
            }
        });
    }
}
