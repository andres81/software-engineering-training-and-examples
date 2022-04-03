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

package eu.andreschepers.javawebmvcrestapidemo.client.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PublishedDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final int LENGTH_DATE_YEAR = 4;
    private static final int LENGTH_DATE_ISO = 10;

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String publishedDate = p.getText();
        if (publishedDate.length() == LENGTH_DATE_YEAR) {
            return LocalDate.ofYearDay(Integer.parseInt(publishedDate), 1);
        } else if (publishedDate.length() >= LENGTH_DATE_ISO) {
            String cutDateString = publishedDate.substring(0, LENGTH_DATE_ISO);
            return LocalDate.parse(cutDateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } else {
            return null;
        }
    }
}
