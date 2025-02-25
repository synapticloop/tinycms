package com.synapticloop.tinycms.server.handler;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RestHandler implements HttpRequestHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestHandler.class);

	@Override
	public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
		String resourcePath = httpRequest.getRequestLine().getUri();
		String[] split = resourcePath.split("/");
		// first one is always 'rest'
		// second one is the function
		if(split.length < 3) {
			// return 404
			return404Response(httpResponse, resourcePath);
			return;
		}

		// at this point we will attempt to look up the response
		// are we doing a GET or a POST
		String method = httpRequest.getRequestLine().getMethod();

		String action = split[2];
		System.out.println(action + ":" + method);
		switch (action) {
			case "":
				return404Response(httpResponse, resourcePath);
				return;
				case "collection":
					returnCollectionResponse(method, action, split);
					return;
		}

		System.out.println(method);

	}

	private void returnCollectionResponse(String method, String action, String[] split) {
		System.out.println(action);
	}

	private static void return404Response(HttpResponse httpResponse, String resourcePath) {
		LOGGER.info("[ ERROR: 404 ] {}", resourcePath);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", true);
		jsonObject.put("message", "Could not find the REST endpoint.");

		httpResponse.setStatusCode(HttpStatus.SC_NOT_FOUND);
		httpResponse.setEntity(new ByteArrayEntity(jsonObject.toString().getBytes(StandardCharsets.UTF_8), ContentType.create("application/json")));
	}
}