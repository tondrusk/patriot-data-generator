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
import java.util.UUID;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private final UUID uuid;
    private final Storage storage;
    private final SSLInit sslInit;

    private String[] suites = {"TLS_DHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384"};
    private static String[] protocols = {"TLSv1.2", "TLSv1.1", "TLSv1"};

    public ServerInitializer(UUID uuid, Storage storage, SSLInit sslInit, String[] suites) {
        this.uuid = uuid;
        this.storage = storage;
        this.sslInit = sslInit;
        if (suites.length != 0) {
            this.suites = suites;
        }
    }

    @Override
    protected void initChannel(SocketChannel channel) {

        ChannelPipeline pipeline = channel.pipeline();

        if (sslInit != null) {
            SSLEngine engine = sslInit.createSslContext().createSSLEngine();
            engine.setEnabledProtocols(protocols);
            engine.setEnabledCipherSuites(suites);
            engine.setUseClientMode(false);

            pipeline.addLast("ssl", new SslHandler(engine));
        }

        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(4096));
        pipeline.addLast("handler", new ServerHandler(uuid, storage));
    }
}

