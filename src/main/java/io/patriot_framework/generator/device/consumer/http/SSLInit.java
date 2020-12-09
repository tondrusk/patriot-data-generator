package io.patriot_framework.generator.device.consumer.http;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class SSLInit {
    public static SSLContext createSslContext() throws IOException, KeyStoreException, CertificateException,
            NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {

        char[] passphrase = "Patriot".toCharArray();

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("src/main/resources/sslcerts/server_keystore.jks"), passphrase);

        KeyStore keyStoreTrust = KeyStore.getInstance("JKS");
        keyStoreTrust.load(new FileInputStream("src/main/resources/sslcerts/server_truststore.jks"), passphrase);

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, passphrase);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStoreTrust);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        return sslContext;
    }
}
