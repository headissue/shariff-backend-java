package com.headissue.sharecount.provider;

import org.json.JSONArray;
import org.json.JSONObject;

public class Delicious implements ShareCountProvider {


  @Override
  public String getQueryUrl(String forUrl) {
    return "https://api.del.icio.us/v2/json/urlinfo/data?url=" + forUrl;
  }

  @Override
  public String getName() {
    return "delicious";
  }

  @Override
  public int parseCount(String json) {

    JSONArray jsonArray = new JSONArray(json);
    if (jsonArray.length() > 0) {
      JSONObject jsonObject = (JSONObject) jsonArray.get(0);
      return jsonObject.getInt("total_posts");
    } else return 0;
  }
}
