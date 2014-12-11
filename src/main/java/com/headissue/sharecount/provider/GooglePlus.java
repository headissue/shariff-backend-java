package com.headissue.sharecount.provider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GooglePlus implements ShareCountProvider, NeedsPostRequest {
  @Override
  public String getJsonResponse(String forUrl) throws MalformedURLException {
    String body = "[{\"method\":\"pos.plusones.get\",\"id\":\"p\",\"params\":{\"nolog\":true,\"id\":\"" + forUrl + "\",\"source\":\"widget\",\"userId\":\"@viewer\",\"groupId\":\"@self\"},\"jsonrpc\":\"2.0\",\"key\":\"p\",\"apiVersion\":\"v1\"}]";

    URL url = new URL(getQueryUrl(forUrl));
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) url.openConnection();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (connection != null) {
      try {
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type",
            "application/json");
        connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
      } catch (ProtocolException e) {
        e.printStackTrace();
      }
      OutputStreamWriter writer = null;
      try {
        writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(body);
        writer.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
      BufferedReader reader = null;
      StringBuilder sb = new StringBuilder();
      try {
        reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()));
        for (String line; (line = reader.readLine()) != null; ) {
          sb.append(line);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if (writer != null) {
          writer.close();
        }
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      return sb.toString();
    }
    return null;
  }

  @Override
  public String getQueryUrl(String forUrl) {
    return "https://clients6.google.com/rpc?key=AIzaSyCKSbrvQasunBoV16zDH9R33D88CeLr9gQ";
  }

  @Override
  public String getName() {
    return "google-plus";
  }

  @Override
  public int parseCount(String json) {
    JSONArray a = new JSONArray(json);
    if (a.length() > 0) {
      return ((JSONObject) a.get(0)).getJSONObject("result").getJSONObject("metadata").getJSONObject("globalCounts")
          .getInt
              ("count");
    }
    return 0;
  }
}
