package com.headissue.sharecount.proxy;

import com.headissue.sharecount.provider.Delicious;
import com.headissue.sharecount.provider.Facebook;
import com.headissue.sharecount.provider.GooglePlus;
import com.headissue.sharecount.provider.LinkedIn;
import com.headissue.sharecount.provider.Pinterest;
import com.headissue.sharecount.provider.Reddit;
import com.headissue.sharecount.provider.ShareCountProvider;
import com.headissue.sharecount.provider.StumbleUpon;
import com.headissue.sharecount.provider.Twitter;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShareCountProxy extends HttpServlet {

  private Config config;

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

  public ShareCountProxy() {
    config = ConfigBuilder.buildFromEnv();
  }

  public void setConfig(Config config) {
    this.config = config;
  }

  @Override
  protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    addCacheHeaders(resp);
    resp.addHeader("allow", "GET");
    resp.addHeader("Access-Control-Allow-Origin", "*");
    resp.setStatus(200);
    resp.setContentLength(0);
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String forUrl = req.getParameter("url");
    if (forUrl == null) {
      // access to root will displays a welcome page
      Writer out = resp.getWriter();
      out.write("Hello!\nshariff-backend-java is running.\n" +
        "Request the sharecounts like: /?url=http://example.com\n\n" +
        "~~~\nhttps://github.com/headissue/shariff-backend-java");
      return;

    } else {
      try {
        validateUrl(forUrl);
      } catch (ValidationException e) {
        resp.sendError(400, e.getMessage());
        return;
      }
      String json = getCounts(forUrl);
      PrintWriter out = resp.getWriter();
      addCacheHeaders(resp);
      resp.addHeader("Content-Type", "application/json");
      resp.addHeader("Access-Control-Allow-Origin", "*");
      resp.addHeader("Access-Control-Expose-Headers", "Content-Type");
      out.print(json);
    }
  }

  /**
   * compares the url with a list of patterns from the config
   * throws an exception if the url is not whitelisted
   * @param forUrl
   * @throws ValidationException
   */
  protected void validateUrl(String forUrl) throws ValidationException {
    String domainWhiteListValue = config.getDomainList();
    String[] domains = new String[0];
    if (domainWhiteListValue != null) {
      domains = domainWhiteListValue.split(";");
    }
    for (int i = 0; i < domains.length; i++) {
      String domain = domains[i];
      Pattern p = Pattern.compile(domain);
      Matcher m = p.matcher(forUrl);
      if (m.matches()) {
        // this url is allowed to be requested sharecounts for
        return;
      }
    }
    // nothing matches
    throw new ValidationException("Disallowed query.");
  }

  private void addCacheHeaders(HttpServletResponse resp) {
    int fortySevenSeconds = 1000 * 47;
    resp.addHeader("Cache-Control", "max-age=" + fortySevenSeconds);
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
    resp.addHeader("Expires", dateFormat.format(new Date(new Date().getTime() + fortySevenSeconds)));
  }

  protected String getCounts(String forUrl) {
    if (!forUrl.startsWith("http") || !forUrl.contains("://")) {
      forUrl = "http://" + forUrl;
    }
    try {
      forUrl = URLEncoder.encode(forUrl, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    // signals the cache that we need an item soon
    for (ShareCountProvider p : countProvider) {
      p.prefetch(forUrl);
    }
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    for (int i = 0; i < countProvider.length; i++) {
      ShareCountProvider provider = countProvider[i];
      String name = provider.getName();
      int count = provider.getCount(forUrl);
      sb.append("\"").append(name).append("\"").append(":").append(count);
      if ((i + 1) < countProvider.length) {
        sb.append(",");
      }
    }
    sb.append("}");
    return sb.toString();
  }


  protected class ValidationException extends Exception {

    public ValidationException(String s) {
      super(s);
    }
  }
}
