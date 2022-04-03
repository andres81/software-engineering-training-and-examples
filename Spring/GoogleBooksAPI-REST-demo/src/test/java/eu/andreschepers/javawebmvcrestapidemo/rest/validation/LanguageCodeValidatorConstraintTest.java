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
import org.hibernate.validator.constraintvalidation.HibernateConstraintViolationBuilder;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LanguageCodeValidatorConstraintTest {

    @Mock
    private LanguageCodeUtil languageCodeUtil;

    @InjectMocks
    private LanguageCodeValidatorConstraint constraint;

    @Captor
    private final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    void givenNullLanguageCode_thenPass() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContextImpl.class);
        assertTrue(constraint.isValid(null, context));
    }

    @Test
    void givenLanguageCode_whenValidCode_thenPass() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContextImpl.class);
        when(languageCodeUtil.isValidISOLanguage(anyString())).thenReturn(true);
        assertTrue(constraint.isValid("NL", context));
    }

    @Test
    void givenInvalidLanguageCode_thenConstraintViolation() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContextImpl.class);
        HibernateConstraintViolationBuilder builder = mock(HibernateConstraintViolationBuilder.class);
        when(languageCodeUtil.isValidISOLanguage(anyString())).thenReturn(false);
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
        constraint.isValid("not a language code", context);

        verify(context, times(1)).buildConstraintViolationWithTemplate(messageCaptor.capture());
        assertEquals("{error.invalid-language-code}", messageCaptor.getValue());
    }
}