package com.headissue.sharecount.proxy;

import org.junit.Test;

import static org.junit.Assert.fail;

public class ShareCountProxyTest {

  @Test
  public void testValidFullQualified() {
    String allowed = "http://www.test.com";
    ShareCountProxy p = new ShareCountProxy();
    try {
      p.validateUrl(allowed);
    } catch (ShareCountProxy.ValidationException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testValidWithoutProtocol() {
    String allowed = "www.sub.test.de";
    ShareCountProxy p = new ShareCountProxy();
    try {
      p.validateUrl(allowed);
    } catch (ShareCountProxy.ValidationException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testInValidWithoutProtocol() {
    String disallowed = "www.test.de";
    ShareCountProxy p = new ShareCountProxy();
    try {
      p.validateUrl(disallowed);
      fail();
    } catch (ShareCountProxy.ValidationException e) {

    }
  }



}
