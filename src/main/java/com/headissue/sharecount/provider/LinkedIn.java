package com.headissue.sharecount.provider;

import org.json.JSONObject;

public class LinkedIn implements ShareCountProvider {
  @Override
  public String getQueryUrl(String forUrl) {
    return "https://www.linkedin.com/countserv/count/share?format=json&url=" + forUrl;
  }

  @Override
  public String getName() {
    return "linkedin";
  }

  @Override
  public int parseCount(String json) {
    return new JSONObject(json).getInt("count");
  }
}
