package com.synapticloop.tinycms.server;

import com.synapticloop.tinycms.server.handler.ApiHandler;
import com.synapticloop.tinycms.server.handler.RestHandler;
import com.synapticloop.tinycms.server.handler.StaticHandler;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Server {
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

	public Server() {}

	public void start() throws Exception {
		ServerBootstrap bootstrap = ServerBootstrap
				.bootstrap()
				.setListenerPort(8080);


		bootstrap.registerHandler("/api/*", new ApiHandler());
		bootstrap.registerHandler("/rest/*", new RestHandler());
		bootstrap.registerHandler("/*", new StaticHandler());
		bootstrap.registerHandler("/collection*", new StaticHandler());
		bootstrap.registerHandler("/schema*", new StaticHandler());
		bootstrap.registerHandler("/data*", new StaticHandler());

		HttpServer httpServer = bootstrap.create();

		// Attempt to start the server
		try {
			LOGGER.info("Starting server on port {}", httpServer.getLocalPort());
			httpServer.start();
			LOGGER.info("Server started on port {}", httpServer.getLocalPort());
			Runtime.getRuntime().addShutdownHook(new Thread(httpServer::stop));
			httpServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (Exception e) {
			throw new Exception("Could not start the server.", e);
		}

	}
}
