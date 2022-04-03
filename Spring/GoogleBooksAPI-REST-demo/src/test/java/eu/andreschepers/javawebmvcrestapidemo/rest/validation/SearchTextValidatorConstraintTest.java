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
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchTextValidatorConstraintTest {

    @Mock
    private RestConfigurationProperties properties;

    @InjectMocks
    private SearchTextValidatorConstraint constraint;

    @Captor
    private final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    void givenSearchText_whenSearchTextNotTooLong_thenPass() {
        ConstraintValidatorContext context = mock(ConstraintValidatorContextImpl.class);
        when(properties.getMaxSearchTextLength()).thenReturn(2);
        assertTrue(constraint.isValid("NL", context));
    }

    @Test
    void givenTooLongSearchText_thenConstraintViolation() {
        HibernateConstraintValidatorContext context = mock(ConstraintValidatorContextImpl.class);
        HibernateConstraintViolationBuilder builder = mock(HibernateConstraintViolationBuilder.class);
        when(properties.getMaxSearchTextLength()).thenReturn(2);
        when((context).addMessageParameter(any(), any())).thenReturn(context);
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
        constraint.isValid("search text too long", context);

        verify(context, times(1)).buildConstraintViolationWithTemplate(messageCaptor.capture());
        assertEquals("{error.searchtext}", messageCaptor.getValue());
    }

    @Test
    void givenNullSearchText_thenConstraintViolation() {
        HibernateConstraintValidatorContext context = mock(ConstraintValidatorContextImpl.class);
        HibernateConstraintViolationBuilder builder = mock(HibernateConstraintViolationBuilder.class);
        when(properties.getMaxSearchTextLength()).thenReturn(2);
        when((context).addMessageParameter(any(), any())).thenReturn(context);
        when(context.buildConstraintViolationWithTemplate(any())).thenReturn(builder);
        constraint.isValid(null, context);

        verify(context, times(1)).buildConstraintViolationWithTemplate(messageCaptor.capture());
        assertEquals("{error.searchtext}", messageCaptor.getValue());
    }
}