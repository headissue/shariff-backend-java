package com.headissue.sharecount.provider;

import org.json.JSONObject;

public class Reddit implements ShareCountProvider {
  @Override
  public String getQueryUrl(String forUrl) {
    return "https://www.reddit.com/api/info.json?url=" + forUrl;
  }

  @Override
  public String getName() {
    return "reddit";
  }

  @Override
  public int parseCount(String json) {
    JSONObject o = new JSONObject(json);
    return o.getJSONObject("data").getJSONArray("children").length();
  }

}
