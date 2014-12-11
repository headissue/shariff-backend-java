package com.headissue.sharecount.provider;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;

public class RedditServer extends TestServer {
  HttpRequestHandler handler = new HttpRequestHandler() {
    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
      httpResponse.setHeader("Content-Type", "application/json");
      httpResponse.setEntity(new StringEntity("{\"kind\": \"Listing\", \"data\": {\"modhash\": \"\", \"children\": [1,2,3], \"after\": null, \"before\": null}}"));
    }
  };

  public void setUp() throws Exception {
    super.setUp(handler);
  }
}
