package io.patriot_framework.generator.device.consumer.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.patriot_framework.generator.device.consumer.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class ServerHandler extends SimpleChannelInboundHandler<Object> {
    private final UUID uuid;
    private final Storage storage;

    public static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    public ServerHandler(UUID uuid, Storage storage) {
        this.uuid = uuid;
        this.storage = storage;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        HttpData data = null;

        if (msg instanceof FullHttpRequest) {
            String[] address = ctx.channel().remoteAddress().toString().split(":");

            data = new HttpBuilder((FullHttpRequest) msg)
                    .setUUID(uuid)
                    .setHost(address[0].split("/")[1])
                    .setPort(Integer.parseInt(address[1]))
                    .build();

            HttpRequest request = (HttpRequest) msg;

            if (HttpUtil.is100ContinueExpected(request)) {
                writeResponse(ctx);
            }

            LastHttpContent trailer = (LastHttpContent) msg;

            StringBuilder responseData = new StringBuilder();
            responseData.append(Arrays.toString(data.getPayload())).append("\n");
            writeResponse(ctx, trailer, responseData, request);
        }

        if (data == null) {
            LOGGER.error("null HttpData!");
            return;
        }

        storage.write(data);

        LOGGER.info("Method: " + (data.getMeta()).getRequestMethod());
        LOGGER.info("Endpoint: " + (data.getMeta()).getEndpoint());
        LOGGER.info("Protocol version: " + (data.getMeta()).getProtocolVersion());
        LOGGER.info("Request body: " + new String(data.getPayload(), StandardCharsets.UTF_8));
        LOGGER.info("Storage size: " + storage.size() + "\n");
    }

    private void writeResponse(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE, Unpooled.EMPTY_BUFFER, false);
        ctx.write(response);
    }

    private void writeResponse(ChannelHandlerContext ctx, LastHttpContent trailer, StringBuilder responseData, HttpRequest request) {
        boolean keepAlive = HttpUtil.isKeepAlive(request);

        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, trailer.decoderResult()
                .isSuccess() ? HttpResponseStatus.OK : HttpResponseStatus.BAD_REQUEST, Unpooled.copiedBuffer(responseData.toString(), CharsetUtil.UTF_8), false);

        httpResponse.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (keepAlive) {
            httpResponse.headers()
                    .setInt(HttpHeaderNames.CONTENT_LENGTH, httpResponse.content()
                            .readableBytes());
            httpResponse.headers()
                    .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        ctx.write(httpResponse);

        if (!keepAlive) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                    .addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        LOGGER.error("Error occurred", cause);
    }
}
