package com.synapticloop.tinycms.server.handler;

import com.sun.net.httpserver.HttpHandler;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;

public class ApiHandler implements HttpRequestHandler {
	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {

	}
}
