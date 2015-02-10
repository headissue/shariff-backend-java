package com.headissue.sharecount.provider;

import org.json.JSONObject;

public class LinkedIn extends ShareCountProvider {

  private static final String queryUrl = "https://www.linkedin.com/countserv/count/share?format=json&url=";
  private static final String name = "linkedin";

  public LinkedIn() {
    super(queryUrl, name);
  }

  public int parseCount(String json) {
    return new JSONObject(json).getInt("count");
  }
}
