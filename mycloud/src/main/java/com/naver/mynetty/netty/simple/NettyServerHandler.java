package com.naver.mynetty.netty.simple;

import com.alibaba.csp.sentinel.util.TimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.channel().eventLoop().execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5 * 1000);
					ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端 execute", CharsetUtil.UTF_8));
					System.out.println("channel code = " + ctx.channel().hashCode());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

//		ctx.channel().eventLoop().schedule(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(5 * 1000);
//					ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端 schedule", CharsetUtil.UTF_8));
//					System.out.println("channel code = " + ctx.channel().hashCode());
//				} catch (Exception ex) {
//					ex.printStackTrace();
//				}
//			}
//		}, 5, TimeUnit.SECONDS);

		System.out.println("服务器读取线程 " + Thread.currentThread().getName() + " channel = " + ctx.channel());
		System.out.println("server ctx = " + ctx);
		Channel channel = ctx.channel();
		ChannelPipeline pipeline = ctx.pipeline();

		ByteBuf buf = (ByteBuf) msg;
		System.out.println("客户端发送消息是: " + buf.toString(CharsetUtil.UTF_8));
		System.out.println("客户端地址：" + channel.remoteAddress());

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端 channelReadComplete", CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
