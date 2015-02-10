package com.headissue.sharecount.provider;

import com.headissue.sharecount.proxy.Config;
import com.headissue.sharecount.proxy.ProviderRequest;
import org.cache2k.Cache;
import org.cache2k.CacheBuilder;
import org.cache2k.CacheSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public abstract class ShareCountProvider {

  private String queryUrl;
  private String name;
  private final CacheSource<String, Integer> cacheSource;

  protected Cache<String, Integer> cache;

  protected ShareCountProvider(String queryUrl, String name) {
    this.queryUrl = queryUrl;
    this.name = name;
    this.cacheSource = new CacheSource<String, Integer>() {
      @Override
      public Integer get(String forUrl) {
        try {
          return getCountInternal(forUrl);
        } catch (IOException e) {
          e.printStackTrace();
        }
        return 0;
      }
    };
    this.cache = CacheBuilder
      .newCache(String.class, Integer.class)
      .source(cacheSource)
      .name(this.getClass().getName())
      .expiryDuration(Config.getInstance().getCacheExpiryMilliSeconds(), TimeUnit.MILLISECONDS)
      .maxSize(Config.getInstance().getCacheSize())
      .build();
  }

  protected Integer getCountInternal(String forUrl) throws IOException {
    String json = getJsonResponse(forUrl);
    return parseCount(json);
  }

  protected String getJsonResponse(String forUrl) throws IOException {
    ProviderRequest providerRequest = new ProviderRequest(buildQueryUrl(this.queryUrl, forUrl));
    return providerRequest.execute();
  }

  public String getName() {
    return name;
  }

  protected int parseCount(String json){
    throw new UnsupportedOperationException("Do not call, overwrite this method in the subclass instead!");
  }

  public void prefetch(String o) {
    cache.prefetch(o);
  }

  public int getCount(String o) {
    return cache.get(o);
  }

  protected String buildQueryUrl (String providerUrl, String query) {
    return providerUrl + query;
  }
}
