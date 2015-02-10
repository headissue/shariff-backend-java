package com.headissue.sharecount.provider;

import org.json.JSONArray;
import org.json.JSONObject;

public class Facebook extends ShareCountProvider {

  private static final String queryUrl = "https://api.facebook.com/method/links.getStats?format=json&urls=";
  private static final String name = "facebook";

  public Facebook() {
    super(queryUrl, name);
  }

  protected int parseCount(String json) {
    JSONArray o = new JSONArray(json);
    JSONObject entry = (JSONObject) o.get(0);
    return entry.getInt("total_count");
  }
}
