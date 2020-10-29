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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.InetSocketAddress;
import java.nio.channels.UnresolvedAddressException;
import java.util.List;

public class Server extends AbstractDevice implements Consumer {
    private final String serverHost;
    private final int serverPort;
    private final Storage storage;
    private final boolean ssl;
    private ChannelFuture future;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public Server(String serverHost, int serverPort, boolean ssl, Storage storage) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.ssl = ssl;
        this.storage = storage;
    }

    @Override
    public void run() throws ConsumerException {
        try {
            LOGGER.info(String.format("Trying to start HTTP%s server on port %d", ssl ? "S" : "", serverPort));

            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(serverHost, serverPort))
                    .childHandler(new ServerInitializer(getUUID(), ssl, storage))
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            future = bootstrap.bind().sync();
            LOGGER.info(String.format("Started HTTP%s server on port %d", ssl ? "S" : "", serverPort));
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
                LOGGER.info(String.format("Stopped HTTP%s server on port %d", ssl ? "S" : "", serverPort));
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
