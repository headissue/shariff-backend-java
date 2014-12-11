package com.headissue.sharecount.provider;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class RedditTest {

  @Test
  public void testParseCount() throws Exception {
    String json = "{\"kind\": \"Listing\", \"data\": {\"modhash\": \"\", \"children\": [1,2,3], \"after\": null, \"before\": null}}";
    ShareCountProvider p = new Reddit();
    int count = p.parseCount(json);
    assertEquals(3, count);
  }

  @Test
  public void testLocalServer() throws IOException {
    RedditServer server = new RedditServer();
    try {
      server.setUp();
    } catch (Exception e) {
      e.printStackTrace();
    }

    URL requestUrl = new URL(server.address);
    BufferedReader in = new BufferedReader(new InputStreamReader(requestUrl.openStream()));

    String inputLine = "";
    StringBuilder sb = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      sb.append(inputLine);
    }
    in.close();
    assertEquals("{\"kind\": \"Listing\", \"data\": {\"modhash\": \"\", \"children\": [1,2,3], \"after\": null, \"before\": null}}", sb.toString());

  }
}