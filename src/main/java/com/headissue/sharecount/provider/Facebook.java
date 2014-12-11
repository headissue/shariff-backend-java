package com.headissue.sharecount.provider;

import org.json.JSONArray;
import org.json.JSONObject;

public class Facebook implements ShareCountProvider {
  @Override
  public String getQueryUrl(String forUrl) {
    return "https://api.facebook.com/method/links.getStats?format=json&urls=" + forUrl;
  }

  @Override
  public String getName() {
    return "facebook";
  }

  @Override
  public int parseCount(String json) {
    JSONArray o = new JSONArray(json);
    JSONObject entry = (JSONObject) o.get(0);
    return entry.getInt("total_count");
  }
}
