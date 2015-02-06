package com.headissue.sharecount.proxy;

import java.util.Map;

public class Config {

  private static Config instance = null;

  final static String MAINTAINER = "SHARECOUNT_PROXY_MAINTAINER";
  final static String DOMAINLIST = "SHARECOUNT_PROXY_DOMAINLIST";
  final static String CACHESIZE = "SHARECOUNT_CACHE_SIZE";
  final static String CACHEEXPIRY = "SHARECOUNT_CACHE_EXPIRY_MS";

  String maintainer = "defaultMaintainer";
  String domainList = ".*";
  int cacheSize = 1000;
  long cacheExpiryMilliSeconds = 1000 * 60 * 5;

  public static synchronized Config getInstance() {
    if (instance == null)
      instance = ConfigBuilder.buildFromEnv();
    return instance;
  }

  public String getMaintainer() {
    return maintainer;
  }

  public String getDomainList() {
    return domainList;
  }

  public int getCacheSize() {
    return cacheSize;
  }

  public long getCacheExpiryMilliSeconds() {
    return cacheExpiryMilliSeconds;
  }

  public Config(Map map) {
    assert map != null;

    domainList = ".*";
    maintainer = "https://github.com/headissue/shariff-backend-java";

    if (map.get(DOMAINLIST) != null) {
      domainList = (String) map.get(DOMAINLIST);
    }
    if (map.get(MAINTAINER) != null) {
      maintainer = (String) map.get(MAINTAINER);
    }
    if (map.get(CACHEEXPIRY) != null) {
      cacheExpiryMilliSeconds = (Integer) map.get(CACHEEXPIRY);
    }
    if (map.get(CACHESIZE) != null) {
      cacheSize = (Integer) map.get(CACHESIZE);
    }
  }

}