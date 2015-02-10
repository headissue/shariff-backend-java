package com.headissue.sharecount.provider;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pinterest extends ShareCountProvider {


  private static final String queryUrl = "https://widgets.pinterest.com/v1/urls/count.json?source=6&url=";
  private static final String name = "pinterest";

  public Pinterest() {
    super(queryUrl, name);
  }

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
