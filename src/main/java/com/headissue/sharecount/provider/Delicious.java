package com.headissue.sharecount.provider;

import org.json.JSONArray;
import org.json.JSONObject;

public class Delicious extends ShareCountProvider {

  private static final String queryUrl = "https://api.del.icio.us/v2/json/urlinfo/data?url=";
  private static final String name = "delicious";

  public Delicious() {
    super(queryUrl, name);
  }

  public int parseCount(String json) {
    JSONArray jsonArray = new JSONArray(json);
    if (jsonArray.length() > 0) {
      JSONObject jsonObject = (JSONObject) jsonArray.get(0);
      return jsonObject.getInt("total_posts");
    } else return 0;
  }
}
