package com.headissue.sharecount.provider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FacebookTest {

  @Test
  public void testParseCount() throws Exception {
    String json = "[{\"url\":\"http:\\/\\/www.yegor256.com\\/2014\\/07\\/31\\/travis-and-rultor.html\",\"normalized_url\":\"http:\\/\\/www.yegor256.com\\/2014\\/07\\/31\\/travis-and-rultor.html\",\"share_count\":0,\"like_count\":0,\"comment_count\":0,\"total_count\":5,\"click_count\":0,\"comments_fbid\":927580697268032,\"commentsbox_count\":0}]";

    int count = new Facebook().parseCount(json);
    assertEquals(5, count);

  }
}