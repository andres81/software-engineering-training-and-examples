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

import eu.andreschepers.javawebmvcrestapidemo.rest.validation.LanguageCodeValidator;
import eu.andreschepers.javawebmvcrestapidemo.rest.validation.SearchTextValidator;
import eu.andreschepers.javawebmvcrestapidemo.service.GoogleBooksApiService;
import io.swagger.client.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class BookController {

    private final GoogleBooksApiService service;

    @GetMapping(value="/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getBooks(
            @LanguageCodeValidator @RequestParam(required = false, value = "language") String languageCode,
            @SearchTextValidator @RequestParam("searchText") String searchText) {

        return ResponseEntity.ok(service.getBooks(languageCode, searchText));
    }
}
