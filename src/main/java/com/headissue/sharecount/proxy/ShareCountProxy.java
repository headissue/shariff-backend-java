package com.headissue.sharecount.proxy;

import com.headissue.sharecount.provider.Delicious;
import com.headissue.sharecount.provider.Facebook;
import com.headissue.sharecount.provider.GooglePlus;
import com.headissue.sharecount.provider.LinkedIn;
import com.headissue.sharecount.provider.NeedsPostRequest;
import com.headissue.sharecount.provider.Pinterest;
import com.headissue.sharecount.provider.Reddit;
import com.headissue.sharecount.provider.ShareCountProvider;
import com.headissue.sharecount.provider.StumbleUpon;
import com.headissue.sharecount.provider.Twitter;
import org.json.JSONException;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShareCountProxy extends HttpServlet {

  private ShareCountProvider[] countProvider =
    new ShareCountProvider[]{
      new Reddit(),
      new Facebook(),
      new Twitter(),
      new LinkedIn(),
      new Delicious(),
      new StumbleUpon(),
      new Pinterest(),
      new GooglePlus()
    };

  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    addCacheHeaders(resp);
    resp.addHeader("allow", "GET");
    resp.addHeader("Access-Control-Allow-Origin", "*");
    resp.setStatus(200);
    resp.setContentLength(0);
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    // other accces than root should yield 404
    if (!"/".equals(req.getRequestURI())) {
      resp.sendError(404, "" +
        "Request the sharecounts like: /?url=http://example.com");
      return;
    }

    String forUrl = req.getParameter("url");
    if (forUrl == null) {
      // access to root will displays a welcome page
      Writer out = resp.getWriter();
      out.write("Hello!\nshariff-backend-java is running.\n" +
        "Request the sharecounts like: /?url=http://example.com\n\n" +
        "~~~\nhttps://github.com/headissue/shariff-backend-java");
      return;

    } else {
      String json = getCounts(forUrl);
      PrintWriter out = resp.getWriter();
      addCacheHeaders(resp);
      resp.addHeader("Content-Type", "application/json");
      resp.addHeader("Access-Control-Allow-Origin", "*");
      resp.addHeader("Access-Control-Expose-Headers", "Content-Type");
      out.print(json);
    }
  }

  private void addCacheHeaders(HttpServletResponse resp) {
    int fortySevenSeconds = 1000 * 47;
    resp.addHeader("Cache-Control", "max-age=" + fortySevenSeconds);
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    resp.addHeader("Expires", dateFormat.format(new Date(new Date().getTime() + fortySevenSeconds)));
  }

  private String getCounts(String forUrl) {
    if (!forUrl.startsWith("http") || !forUrl.contains("://")) {
      forUrl = "http://" + forUrl;
    }
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    for (int i = 0; i < countProvider.length; i++) {
      ShareCountProvider provider = countProvider[i];
      String queryUrl = null;
      try {
        queryUrl = provider.getQueryUrl(URLEncoder.encode(forUrl, "UTF-8"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      String name = provider.getName();
      String json = null;

      // TODO do not use an interface for this distinction
      if (provider instanceof NeedsPostRequest) {
        try {
          json = ((NeedsPostRequest) provider).getJsonResponse(forUrl);
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
      } else {
        try {
          json = new ProviderRequest(queryUrl).execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      int count = 0;
      if (json != null) {
        try {
          count = provider.parseCount(json);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
      sb.append("\"").append(name).append("\"").append(":").append(count);
      if ((i + 1) < countProvider.length) {
        sb.append(",");
      }
    }
    sb.append("}");
    return sb.toString();
  }
}
