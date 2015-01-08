package com.headissue.sharecount.provider;

import org.json.JSONObject;

public class StumbleUpon implements ShareCountProvider {
  @Override
  public String getQueryUrl(String forUrl) {
    return "https://www.stumbleupon.com/services/1.01/badge.getinfo?url=" + forUrl;
  }

  @Override
  public String getName() {
    return "stumbleupon";
  }

  @Override
  public int parseCount(String json) {
    JSONObject jsonObject = new JSONObject(json).getJSONObject("result");
    if (jsonObject.getBoolean("in_index") == true) {
      return jsonObject.getInt("views");
    } else {
      return 0;
    }
  }
}
