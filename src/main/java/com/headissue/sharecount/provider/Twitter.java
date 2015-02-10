package com.headissue.sharecount.provider;

import org.json.JSONObject;

public class Twitter extends ShareCountProvider {

  private static final String queryUrl = "https://cdn.api.twitter.com/1/urls/count.json?url=";
  private static final String name = "twitter";

  public Twitter() {
    super(queryUrl, name);
  }

  public int parseCount(String json) {
    return new JSONObject(json).getInt("count");
  }
}
