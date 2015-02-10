package com.headissue.sharecount.provider;

import org.json.JSONObject;

public class StumbleUpon extends ShareCountProvider {

  private static final String queryUrl = "https://www.stumbleupon.com/services/1.01/badge.getinfo?url=";
  private static final String name = "stumbleupon";

  public StumbleUpon() {
    super(queryUrl, name);
  }

  protected int parseCount(String json) {
    JSONObject jsonObject = new JSONObject(json).getJSONObject("result");
    if (jsonObject.getBoolean("in_index") == true) {
      return jsonObject.getInt("views");
    } else {
      return 0;
    }
  }
}
