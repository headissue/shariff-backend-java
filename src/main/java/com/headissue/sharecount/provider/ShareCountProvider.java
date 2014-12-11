package com.headissue.sharecount.provider;

import org.json.JSONException;

public interface ShareCountProvider {

  public String getQueryUrl(String forUrl);

  public String getName();

  public int parseCount(String json) throws JSONException;
}
