package com.headissue.sharecount.provider;

import org.json.JSONObject;

public class Twitter implements ShareCountProvider {
  @Override
  public String getQueryUrl(String forUrl) {
    return "https://cdn.api.twitter.com/1/urls/count.json?url=" + forUrl;
  }

  @Override
  public String getName() {
    return "twitter";
  }

  @Override
  public int parseCount(String json) {
    return new JSONObject(json).getInt("count");
  }
}
