package com.naver.mynetty.nio;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
	public static void main(String[] args) throws Exception {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		Selector selector = Selector.open();

		serverSocketChannel.socket().bind(new InetSocketAddress(6666));

		serverSocketChannel.configureBlocking(false);

		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("注册后 selectionKey 数量=" + selector.keys().size());

		while (true) {
			if (selector.select(1000) == 0) {
				continue;
			}
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			System.out.println("selectionKeys 数量=" + selectionKeys.size());

			Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
			while (keyIterator.hasNext()) {
				SelectionKey key = keyIterator.next();
				if (key.isAcceptable()) {
					SocketChannel socketChannel = serverSocketChannel.accept();
					System.out.println("客户端连接成功，生成一个 socketChannel " + socketChannel.hashCode());
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
					System.out.println("客户端连接后，注册的 selectionKey 数量=" + selector.keys().size());
				}
				if (key.isReadable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					ByteBuffer buffer = (ByteBuffer) key.attachment();
					channel.read(buffer);
					System.out.println("Form 客户端: " + new String(buffer.array()));
				}
				keyIterator.remove();
			}
		}
	}
}
