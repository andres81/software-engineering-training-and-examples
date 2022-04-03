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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Slf4j
@Component
public class X509CertificateUtil {

    private static final String PEM_FIRST_LINE = "-----BEGIN CERTIFICATE-----";
    private static final String PEM_LAST_LINE = "-----END CERTIFICATE-----";
    private static final String CERTIFICATE_TYPE = "X.509";

    public X509Certificate certificateFromString(String x509Certificate) {
        if (x509Certificate == null) {
            throw new NullPointerException();
        }
        String cleanedCertificate = cleanCertificateString(x509Certificate);
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decodeBase64(cleanedCertificate));
        try {
            return (X509Certificate) CertificateFactory.getInstance(CERTIFICATE_TYPE)
                .generateCertificate(bais);
        } catch (CertificateException e) {
            log.error("Could not create X509Certificate from String");
            throw new IllegalStateException();
        }
    }

    private String cleanCertificateString(String certificate) {
        return certificate.replace(PEM_FIRST_LINE, "")
            .replaceAll(System.lineSeparator(), "")
            .replace(PEM_LAST_LINE, "");
    }
}
