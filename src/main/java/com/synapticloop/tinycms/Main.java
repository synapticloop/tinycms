package com.synapticloop.tinycms;

import com.synapticloop.tinycms.server.Server;

public class Main {
	public static void main(String[] args) throws Exception {
		Server server = new Server();

		server.start();
	}
}
