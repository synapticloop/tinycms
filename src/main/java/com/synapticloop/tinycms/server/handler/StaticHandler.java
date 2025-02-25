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
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
/**
 * <p>The static handler servers up the static resources from </p>
 */
public class StaticHandler implements HttpRequestHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(StaticHandler.class);

	public static final String JSON_KEY_ERROR = "error";
	public static final String JSON_KEY_MESSAGE = "message";

	public static final ContentType CONTENT_TYPE_JSON = ContentType.create("application/json", StandardCharsets.UTF_8);
	public static final ContentType CONTENT_TYPE_TEXT = ContentType.create("text/plain", StandardCharsets.UTF_8);

	private static final Map<String, ContentType> CONTENT_TYPE_MAP = new HashMap<>();
	static {
		CONTENT_TYPE_MAP.put(".json", ContentType.create("application/json"));
		CONTENT_TYPE_MAP.put(".css", ContentType.create("text/css"));
		CONTENT_TYPE_MAP.put(".js", ContentType.create("text/javascript "));
		CONTENT_TYPE_MAP.put(".png", ContentType.create("image/png"));
		CONTENT_TYPE_MAP.put(".woff", ContentType.create("font/woff"));
		CONTENT_TYPE_MAP.put(".woff2", ContentType.create("font/woff2"));
		CONTENT_TYPE_MAP.put(".html", ContentType.create("text/html"));
	}

	private static final Map<String, byte[]> CONTENT_CACHE = new HashMap<>();
	private static final Map<String, ContentType> CONTENT_TYPE_CACHE = new HashMap<>();
	private static final Map<String, Integer> CONTENT_RESPONSE_CODE_CACHE = new HashMap<>();

	/**
	 * @param httpRequest  The request
	 * @param httpResponse The response
	 * @param httpContext The context
	 *
	 * @throws HttpException if something went wrong
	 * @throws IOException if something went wrong
	 */
	@Override public void handle(
			HttpRequest httpRequest,
			HttpResponse httpResponse,
			HttpContext httpContext) throws HttpException, IOException {


		String resourcePath = "/webapp" + httpRequest.getRequestLine().getUri();

		int indexOf = resourcePath.indexOf('?');
		if(indexOf != -1) {
			resourcePath = resourcePath.substring(0, indexOf);
		}

		if(resourcePath.startsWith("/webapp/collection") ||
			resourcePath.startsWith("/webapp/schema") ||
			resourcePath.startsWith("/webapp/data") ||
			resourcePath.equals("/webapp/")) {
			resourcePath = "/webapp/index.html";
		}

		serveResource(resourcePath, httpResponse);
	}

	/**
	 * <p>Serve a resourcePath from the class loader and cache the response on
	 * first access.  This will return the cached response if it exists.</p>
	 *
	 * <p><strong> NOTE:</strong> that this is not supposed to be performant as it
	 * serves the in-built Panl web apps.</p>
	 *
	 * @param resourcePath the path to the resource
	 * @param response The response object to write to
	 */
	public static void serveResource(String resourcePath, HttpResponse response) {
		if(CONTENT_CACHE.containsKey(resourcePath)) {
			response.setStatusCode(CONTENT_RESPONSE_CODE_CACHE.get(resourcePath));
			response.setEntity(new ByteArrayEntity(CONTENT_CACHE.get(resourcePath), CONTENT_TYPE_CACHE.get(resourcePath)));
			LOGGER.info("[     CACHED ] {}", resourcePath);
			return;
		}

		try (InputStream resourceAsStream = StaticHandler.class.getResourceAsStream(resourcePath)) {
			if (null != resourceAsStream) {
				byte[] content = resourceAsStream.readAllBytes();

				ContentType contentType = getContentType(resourcePath);

				CONTENT_CACHE.put(resourcePath, content);
				CONTENT_TYPE_CACHE.put(resourcePath, contentType);
				CONTENT_RESPONSE_CODE_CACHE.put(resourcePath, HttpStatus.SC_OK);

				LOGGER.info("[     SERVED ] {}", resourcePath);
			} else {
				LOGGER.info("[ ERROR: 404 ] {}", resourcePath);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(JSON_KEY_ERROR, true);
				jsonObject.put(JSON_KEY_MESSAGE, "Could not find the resourcePath '" + resourcePath + "'");

				CONTENT_CACHE.put(resourcePath, jsonObject.toString().getBytes());
				CONTENT_TYPE_CACHE.put(resourcePath, CONTENT_TYPE_JSON);
				CONTENT_RESPONSE_CODE_CACHE.put(resourcePath, HttpStatus.SC_NOT_FOUND);
			}
		} catch (IOException ioex) {
			LOGGER.info("[ ERROR: 500 ] {}", resourcePath);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(JSON_KEY_ERROR, true);
			jsonObject.put(JSON_KEY_MESSAGE, "Could not serve the resourcePath '" + resourcePath + "'");
			CONTENT_CACHE.put(resourcePath, jsonObject.toString().getBytes());
			CONTENT_TYPE_CACHE.put(resourcePath, CONTENT_TYPE_JSON);
			CONTENT_RESPONSE_CODE_CACHE.put(resourcePath, HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}

		response.setStatusCode(CONTENT_RESPONSE_CODE_CACHE.get(resourcePath));
		response.setEntity(new ByteArrayEntity(CONTENT_CACHE.get(resourcePath), CONTENT_TYPE_CACHE.get(resourcePath)));
	}

	/**
	 * <p>Simply get the content type from the extension helper method.  Just a
	 * quick lookup on the extension of the file.</p>
	 *
	 * <p>There is a limited number of content types that this will work on, with
	 * the default for unknown extensions being 'text/plain'.</p>
	 *
	 * @param resource The resource to look up the extension for
	 *
	 * @return The content type for the resource
	 */
	private static ContentType getContentType(String resource) {
		int lastIndexOf = resource.lastIndexOf('.');
		if(lastIndexOf != -1) {
			String extension = resource.substring(lastIndexOf);
			return(CONTENT_TYPE_MAP.getOrDefault(extension, CONTENT_TYPE_TEXT));
		}
		return(CONTENT_TYPE_TEXT);
	}
}
