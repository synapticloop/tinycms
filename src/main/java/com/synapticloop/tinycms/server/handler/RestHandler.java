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
		if(split.length == 1) {
			// return 404
			LOGGER.info("[ ERROR: 404 ] {}", resourcePath);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", true);
			jsonObject.put("message", "Could not find the resourcePath '" + resourcePath + "'");
			httpResponse.setStatusCode(HttpStatus.SC_NOT_FOUND);
			httpResponse.setEntity(new ByteArrayEntity(jsonObject.toString().getBytes(StandardCharsets.UTF_8), ContentType.create("application/json")));
			return;
		}

		// at this point we will attempt to look up the response

	}
}