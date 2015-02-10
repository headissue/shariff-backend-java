package com.headissue.sharecount.provider;

import org.json.JSONObject;

public class Reddit extends ShareCountProvider {

  private static final String queryUrl = "https://www.reddit.com/api/info.json?url=";
  private static final String name = "reddit";

  public Reddit() {
    super(queryUrl, name);
  }

  public int parseCount(String json) {
    JSONObject o = new JSONObject(json);
    return o.getJSONObject("data").getJSONArray("children").length();
  }

}
