package com.headissue.sharecount.proxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ProviderRequest {

  String queryUrl;

  public ProviderRequest(String queryUrl) {
    this.queryUrl = queryUrl;
  }

  public String execute() throws IOException {
    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(3000)
        .setConnectionRequestTimeout(3000)
        .setSocketTimeout(3000)
        .build();
    CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(config).build();
    try {
      HttpGet httpget = new HttpGet(queryUrl);
      httpget.addHeader("User-Agent", getUserAgent());
      httpget.addHeader("Connection", "Keep-Alive");

      ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

        public String handleResponse(
            final HttpResponse response) throws IOException {
          int status = response.getStatusLine().getStatusCode();
          if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
          } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
          }
        }

      };
      return httpclient.execute(httpget, responseHandler);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } finally {
      httpclient.close();
    }
    return null;
  }

  protected String getUserAgent() {
    return "sharecountbot (" + Config.getInstance().getMaintainer() + ")";
  }
}
