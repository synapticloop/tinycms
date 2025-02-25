package com.synapticloop.tinycms.server;

import com.synapticloop.tinycms.server.handler.ApiHandler;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;

import java.util.concurrent.TimeUnit;

public class Server {
	private HttpServer httpServer;

	public Server() {}

	public void start() throws Exception {
		ServerBootstrap bootstrap = ServerBootstrap
				.bootstrap()
				.setListenerPort(8080);

		httpServer = bootstrap.create();

		bootstrap.registerHandler("/api/*", new ApiHandler())

		// Attempt to start the server
		try {
			httpServer.start();
//			LOGGER.info("Server started on port {}", httpServer.getLocalPort());
			Runtime.getRuntime().addShutdownHook(new Thread(httpServer::stop));
			httpServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (Exception e) {
			throw new Exception("Could not start the server.", e);
		}

	}
}
