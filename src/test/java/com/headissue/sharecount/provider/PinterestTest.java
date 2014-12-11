package com.headissue.sharecount.provider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PinterestTest {

  @Test
  public void testParseCount() throws Exception {
    String json = "receiveCount({\"url\":\"http://www.yegor256.com/2014/07/31/travis-and-rultor.html\",\"count\":3})";
    int count = new Pinterest().parseCount(json);
    assertEquals(3, count);
  }
}