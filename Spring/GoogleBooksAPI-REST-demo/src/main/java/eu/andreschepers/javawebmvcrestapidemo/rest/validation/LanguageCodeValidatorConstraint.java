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

import eu.andreschepers.javawebmvcrestapidemo.util.LanguageCodeUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class LanguageCodeValidatorConstraint implements ConstraintValidator<LanguageCodeValidator, String> {

    private static final String INVALID_LANGUAGE_CODE_MESSAGE = "{error.invalid-language-code}";

    private final LanguageCodeUtil languageCodeUtil;

    @Override
    public boolean isValid(String languageCode, ConstraintValidatorContext context) {
        if (languageCode == null || languageCodeUtil.isValidISOLanguage(languageCode)) {
            return true;
        }
        ((ConstraintValidatorContextImpl) context)
            .buildConstraintViolationWithTemplate(INVALID_LANGUAGE_CODE_MESSAGE)
            .addConstraintViolation();
        return false;
    }
}
