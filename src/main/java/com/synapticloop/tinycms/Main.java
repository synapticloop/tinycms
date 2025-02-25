package com.synapticloop.tinycms;

import com.synapticloop.tinycms.server.Server;
import com.synapticloop.tinycms.sqlite.h2zero.ConnectionManagerInitialiserOverride;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		LOGGER.info("Initialising database");
		ConnectionManagerInitialiserOverride.initialise();
		LOGGER.info("Initialised database");

		Server server = new Server();
		server.start();
	}
}
