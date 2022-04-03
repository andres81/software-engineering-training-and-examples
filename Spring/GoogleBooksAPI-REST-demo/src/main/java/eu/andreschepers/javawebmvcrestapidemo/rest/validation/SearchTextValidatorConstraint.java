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

package eu.andreschepers.javawebmvcrestapidemo.rest.validation;

import eu.andreschepers.javawebmvcrestapidemo.configuration.RestConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class SearchTextValidatorConstraint implements ConstraintValidator<SearchTextValidator, String> {

    private static final String INVALID_SEARCHTEXT = "{error.searchtext}";

    private final RestConfigurationProperties properties;

    @Override
    public boolean isValid(String searchText, ConstraintValidatorContext constraintValidatorContext) {
        if (searchText == null || searchText.length() > properties.getMaxSearchTextLength()) {
            ((ConstraintValidatorContextImpl) constraintValidatorContext).addMessageParameter("length", properties.getMaxSearchTextLength())
                .buildConstraintViolationWithTemplate(INVALID_SEARCHTEXT)
                .addConstraintViolation();
            return false;
        }
        return true;
    }
}
