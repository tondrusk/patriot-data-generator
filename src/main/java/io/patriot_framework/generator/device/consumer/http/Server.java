package io.patriot_framework.generator.device.consumer.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.patriot_framework.generator.Data;
import io.patriot_framework.generator.device.AbstractDevice;
import io.patriot_framework.generator.device.consumer.Consumer;
import io.patriot_framework.generator.device.consumer.Storage;
import io.patriot_framework.generator.device.consumer.exceptions.ConsumerException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.InetSocketAddress;
import java.nio.channels.UnresolvedAddressException;
import java.util.List;
import java.util.UUID;

public class Server extends AbstractDevice implements Consumer, AutoCloseable {
    private final UUID uuid = UUID.randomUUID();
    private final String serverHost;
    private final int serverPort;
    private final Storage storage;
    private SSLInit sslInit;
    private String[] suites = new String[]{};
    private ChannelFuture future;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public Server(String serverHost, int serverPort, Storage storage) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.storage = storage;
    }

    public Server(String serverHost, int serverPort, Storage storage, SSLInit sslInit) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.storage = storage;
        this.sslInit = sslInit;
    }

    public Server(String serverHost, int serverPort, Storage storage, SSLInit sslInit, String[] suites) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.storage = storage;
        this.sslInit = sslInit;
        this.suites = suites;
    }

    @Override
    public void run() throws ConsumerException {
        try {
            System.out.println(String.format("Trying to start HTTP%s server on port %d", sslInit != null ? "S" : "", serverPort));

            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(serverHost, serverPort))
                    .childHandler(new ServerInitializer(uuid, storage, sslInit, suites))
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            future = bootstrap.bind().sync();
            System.out.println(String.format("Started HTTP%s server on port %d", sslInit != null ? "S" : "", serverPort));
        } catch (UnresolvedAddressException e) {
            throw new ConsumerException("invalid hostname", e);
        } catch (InterruptedException e) {
            throw new ConsumerException("server was interrupted", e);
        }
    }

    @Override
    public void close() {
        try {
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (future != null) {
                future.channel().closeFuture().sync();
                System.out.println(String.format("Stopped HTTP%s server on port %d", sslInit != null ? "S" : "", serverPort));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Data> requestData(Object... params) {
        throw new NotImplementedException();
    }
}
