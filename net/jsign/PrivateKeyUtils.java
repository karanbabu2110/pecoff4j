/**
 * Copyright 2017 Emmanuel Bourg
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

package net.jsign;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyException;
import java.security.PrivateKey;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8DecryptorProviderBuilder;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.operator.InputDecryptorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.pkcs.PKCSException;

/**
 * Helper class for loading private keys (PVK or PEM, encrypted or not).
 * 
 * @author Emmanuel Bourg
 * @since 2.0
 */
public class PrivateKeyUtils {

    private PrivateKeyUtils() {
    }

    /**
     * Load the private key from the specified file. Supported formats are PVK and PEM,
     * encrypted or not. The type of the file is inferred from its extension (<tt>.pvk</tt>
     * for PVK files, <tt>.pem</tt> for PEM files).
     * 
     * @param file
     * @param password
     */
    public static PrivateKey load(File file, String password) throws KeyException {
        try {
            if (file.getName().endsWith(".pvk")) {//NO i18N
                return PVK.parse(file, password);
            } else if (file.getName().endsWith(".pem")) {//NO i18N
                return readPrivateKeyPEM(file, password);
            }
        } catch (Exception e) {
            throw new KeyException("Failed to load the private key from " + file, e);//NO i18N
        }
        
        throw new IllegalArgumentException("Unsupported private key format (PEM or PVK file expected");//NO i18N
    }

    private static PrivateKey readPrivateKeyPEM(File file, String password) throws IOException, GeneralSecurityException, OperatorCreationException, PKCSException {
        try (FileReader reader = new FileReader(file)) {
            PEMParser parser = new PEMParser(reader);
            Object object = parser.readObject();
            
            if (object == null) {
                throw new IllegalArgumentException("No key found in " + file);//NO i18N
            }
            
            BouncyCastleProvider provider = new BouncyCastleProvider();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(provider);

            if (object instanceof PEMEncryptedKeyPair) {
                // PKCS1 encrypted key
                PEMDecryptorProvider decryptionProvider = new JcePEMDecryptorProviderBuilder().setProvider(provider).build(password.toCharArray());
                PEMKeyPair keypair = ((PEMEncryptedKeyPair) object).decryptKeyPair(decryptionProvider);
                return converter.getPrivateKey(keypair.getPrivateKeyInfo());

            } else if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
                // PKCS8 encrypted key
                InputDecryptorProvider decryptionProvider = new JceOpenSSLPKCS8DecryptorProviderBuilder().setProvider(provider).build(password.toCharArray());
                PrivateKeyInfo info = ((PKCS8EncryptedPrivateKeyInfo) object).decryptPrivateKeyInfo(decryptionProvider);
                return converter.getPrivateKey(info);
                
            } else if (object instanceof PEMKeyPair) {
                // PKCS1 unencrypted key
                return converter.getKeyPair((PEMKeyPair) object).getPrivate();
                
            } else if (object instanceof PrivateKeyInfo) {
                // PKCS8 unencrypted key
                return converter.getPrivateKey((PrivateKeyInfo) object);
                
            } else {
                throw new UnsupportedOperationException("Unsupported PEM object: " + object.getClass().getSimpleName());//NO i18N
            }
        }
    }
}
