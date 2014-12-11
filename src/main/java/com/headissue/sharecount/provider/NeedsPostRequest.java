package com.headissue.sharecount.provider;

import java.net.MalformedURLException;

public interface NeedsPostRequest {
  public String getJsonResponse(String forUrl) throws MalformedURLException;
}
