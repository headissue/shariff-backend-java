package com.headissue.sharecount.proxy;

import java.util.Map;

public class Config {

  private static Config instance = null;

  final static String MAINTAINER = "SHARECOUNT_PROXY_MAINTAINER";
  final static String DOMAINLIST = "SHARECOUNT_PROXY_DOMAINLIST";

  String maintainer;
  String domainList;

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
  }

}