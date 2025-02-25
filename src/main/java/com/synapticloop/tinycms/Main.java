package com.synapticloop.tinycms;

import com.synapticloop.tinycms.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		server.start();
	}
}
