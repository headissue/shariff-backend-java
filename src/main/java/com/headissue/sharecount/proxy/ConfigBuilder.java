package com.headissue.sharecount.proxy;

import java.util.HashMap;
import java.util.Map;

public class ConfigBuilder {

  public static Config buildTestConfig() {
    Map config = new HashMap();
    config.put(Config.MAINTAINER, "https://github.com/headissue/shariff-backend-java");
    config.put(Config.DOMAINLIST, ".*\\.example\\.com;.*\\.sub\\.example\\.org;www\\.example\\.org");
    return new Config(config);
  }

  public static Config buildFromEnv() {
    return new Config(System.getenv());
  }

}
