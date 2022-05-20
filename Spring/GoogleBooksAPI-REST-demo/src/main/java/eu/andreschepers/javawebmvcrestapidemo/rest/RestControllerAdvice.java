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

import brave.Span;
import brave.Tracer;
import eu.andreschepers.javawebmvcrestapidemo.client.exception.*;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.client.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestControllerAdvice {

    private final Tracer tracer;

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .filter(StringUtils::isNotBlank)
            .findAny().orElse("");
        return ResponseEntity.badRequest().body(createErrorResponse(message));
    }

    @ExceptionHandler({ClientException.class, ClientConnectionTimeoutException.class, ClientReadTimeoutException.class,
            Client4xxException.class, Client5xxException.class})
    public ResponseEntity<ErrorResponse> handleClientExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createErrorResponse("Service temporarily unavailable."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleNonCaughtExceptions(Exception e) {
        log.error("Caught unhandled exception: [{}]", e.getMessage(), e);
        return ResponseEntity.internalServerError()
            .body(createErrorResponse("Something went wrong on the server side. Please contact us!"));
    }

    private ErrorResponse createErrorResponse(String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        Span span = tracer.currentSpan();
        if (span != null) {
            errorResponse.setTraceId(tracer.currentSpan().context().traceIdString());
        }
        return errorResponse;
    }
}
