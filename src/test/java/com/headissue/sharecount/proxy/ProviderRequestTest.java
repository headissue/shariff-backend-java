package com.headissue.sharecount.proxy;

import com.headissue.sharecount.provider.SlowServer;
import org.junit.Test;

import java.net.SocketTimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProviderRequestTest {

  @Test
  public void testTimeout() throws Exception {
    SlowServer server = new SlowServer();
    try {
      server.setUp();
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      new ProviderRequest(server.address).execute();
      fail("Expected timeout did not occur");
    } catch (SocketTimeoutException e) {}
  }

  @Test
  public void testUserAgent() {
    ProviderRequest r = new ProviderRequest("");
    assertEquals("sharecountbot (https://github.com/headissue/shariff-backend-java)",r.getUserAgent());
  }
}