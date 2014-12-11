package com.headissue.sharecount.provider;

import org.apache.http.localserver.LocalTestServer;
import org.apache.http.protocol.HttpRequestHandler;

public class TestServer {

  private LocalTestServer server = null;
  public String address;

  public void setUp(HttpRequestHandler handler) throws Exception {
    server = new LocalTestServer(null, null);
    server.register("/*", handler);
    server.start();

    // report how to access the server
    address = "http://" + server.getServiceAddress().getHostName() + ":"
        + server.getServiceAddress().getPort();
    System.out.println("LocalTestServer available at " + address);

  }

  public void tearDown() throws Exception {
    server.stop();
  }
}
