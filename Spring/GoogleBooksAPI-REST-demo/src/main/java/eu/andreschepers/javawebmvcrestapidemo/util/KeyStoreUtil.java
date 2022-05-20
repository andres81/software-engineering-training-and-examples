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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KeyStoreUtil {

    private static final String TYPE = "pkcs12";

    /**
     * https://stackoverflow.com/questions/51404007/creating-a-truststore-in-java
     * @param alias
     * @param password
     * @param certificate
     * @return
     */
    public KeyStore trustStore(String alias, String password, X509Certificate certificate) {
        if (alias == null || password == null || certificate == null) {
            throw new IllegalArgumentException();
        }
        try {
            KeyStore trustStore = KeyStore.getInstance(TYPE);
            trustStore.load(null, password.toCharArray());
            trustStore.setCertificateEntry(alias, certificate);
            return trustStore;
        } catch (NoSuchAlgorithmException | IOException | KeyStoreException | CertificateException e) {
            throw new IllegalStateException();
        }
    }
}
