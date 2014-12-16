package com.headissue.sharecount.proxy;

import java.util.Properties;

public class Config {
  private static Properties defaultProps = new Properties();
  static {
    try {
      defaultProps.load(Config.class.getResourceAsStream("proxy.properties"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static String getProperty(String key) {
    return defaultProps.getProperty(key);
  }
}