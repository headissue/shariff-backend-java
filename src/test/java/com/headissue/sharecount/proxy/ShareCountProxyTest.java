package com.headissue.sharecount.proxy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ShareCountProxyTest {

  ShareCountProxy p = new ShareCountProxy();

  private class TestProxy extends ShareCountProxy {
    @Override
    protected String getCounts(String forUrl) {
      return "{}";
    }
  }

  public class NullOutputStream extends OutputStream {
    @Override
    public void write(int b) throws IOException {
    }
  }

  HttpServletRequest request;
  HttpServletResponse response;


  @Before
  public void setup() throws IOException {
    p.setConfig(ConfigBuilder.buildTestConfig());
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    when(request.getRequestURI()).thenReturn("/");
    when(request.getParameter("url")).thenReturn("http://example.com");
    when(response.getWriter()).thenReturn(new PrintWriter(new NullOutputStream()));
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

  @Test
  public void testAllowOriginHeaderOnGet() throws ServletException, IOException {
    ShareCountProxy proxy = new TestProxy();
    proxy.doGet(request, response);
    Mockito.verify(response).addHeader("Access-Control-Allow-Origin", "*");
  }

  @Test
  public void testAllowOriginHeaderOnOptions() throws ServletException, IOException {
    ShareCountProxy proxy = new TestProxy();
    proxy.doGet(request, response);
    Mockito.verify(response).addHeader("Access-Control-Allow-Origin", "*");
  }

}
