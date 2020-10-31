package io.patriot_framework.generator.device.consumer.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;
import io.patriot_framework.generator.device.consumer.Storage;

import javax.net.ssl.SSLEngine;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private final UUID uuid;
    private final Storage storage;
    private final boolean ssl;

    public static final String[] SUITES = {"TLS_DHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384"};
    public static final String[] PROTOCOLS = {"TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3"};

    public ServerInitializer(UUID uuid, boolean ssl, Storage storage) {
        this.uuid = uuid;
        this.ssl = ssl;
        this.storage = storage;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException,
            IOException, KeyManagementException, KeyStoreException {

        ChannelPipeline pipeline = channel.pipeline();

        if (ssl) {
            SSLEngine engine = SSLInit.createSslContext().createSSLEngine();
            engine.setEnabledProtocols(PROTOCOLS);
            engine.setEnabledCipherSuites(SUITES);
            engine.setUseClientMode(false);

            pipeline.addLast("ssl", new SslHandler(engine));
        }

        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(4096));
        pipeline.addLast("handler", new ServerHandler(uuid, storage));
    }
}
