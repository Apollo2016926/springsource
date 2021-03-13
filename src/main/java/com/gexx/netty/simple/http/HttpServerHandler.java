package com.gexx.netty.simple.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * @Description 读取客户端数据
     * @author gexx
     * @Date 2021/3/12
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof HttpRequest) {
            System.out.println("msg 类型" + msg.getClass());
            HttpRequest httpRequest = (HttpRequest) msg;
            //过滤资源
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                System.out.println("favicon.ico ，无效");
                return;
            }

            ByteBuf buff = Unpooled.copiedBuffer("Hello 我是服务器", CharsetUtil.UTF_16);

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buff);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buff.readableBytes());

            ctx.writeAndFlush(response);
        }

    }


}
