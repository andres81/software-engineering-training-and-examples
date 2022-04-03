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

package eu.andreschepers.javawebmvcrestapidemo.util;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;

@Component
public class LanguageCodeUtil {

    private static final Set<String> ISO_LANGUAGES = Set.of(Locale.getISOLanguages());

    public boolean isValidISOLanguage(String s) {
        if (s == null) {
            return false;
        }
        return ISO_LANGUAGES.contains(s);
    }
}
