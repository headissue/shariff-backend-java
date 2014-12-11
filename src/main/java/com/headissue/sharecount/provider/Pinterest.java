package com.headissue.sharecount.provider;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pinterest implements ShareCountProvider {
  @Override
  public String getQueryUrl(String forUrl) {
    return "https://widgets.pinterest.com/v1/urls/count.json?source=6&url=" + forUrl;
  }

  @Override
  public String getName() {
    return "pinterest";
  }

  @Override
  public int parseCount(String json) {
    Pattern p = Pattern.compile("\\{.*\\}");
    Matcher m = p.matcher(json);
    if (m.find()) {
      JSONObject o = new JSONObject(m.group(0));
      return o.getInt("count");
    }
    return 0;
  }
}
