package com.headissue.sharecount.proxy;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class ShareCountProxyTest {

  ShareCountProxy p = new ShareCountProxy();

  @Before
  public void setup() {
    p.setConfig(ConfigBuilder.buildTestConfig());
  }

  @Test
  public void testValidFullQualified() {
    String allowed = "http://www.example.com";
    try {
      p.validateUrl(allowed);
    } catch (ShareCountProxy.ValidationException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testValidWithoutProtocol() {
    String allowed = "www.sub.example.org";
    try {
      p.validateUrl(allowed);
    } catch (ShareCountProxy.ValidationException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testInValid() {
    String disallowed = "https://";
    try {
      p.validateUrl(disallowed);
      fail();
    } catch (ShareCountProxy.ValidationException e) {

    }
  }
}
