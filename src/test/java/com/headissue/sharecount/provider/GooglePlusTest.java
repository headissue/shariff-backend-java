package com.headissue.sharecount.provider;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GooglePlusTest {

  @Test
  public void testParseCount() throws Exception {
    String json = "" +
        "[ " +
          "{  \"id\": \"p\",  \"result\": " +
            "{   \"kind\": \"pos#plusones\",   \"id\": \"http://www.yegor256.com/2014/07/31/travis-and-rultor.html\",   \"isSetByViewer\": false,   \"metadata\": " +
              "{    \"type\": \"URL\",    \"globalCounts\": " +
                "{     \"count\": 3.0    " +
                "}   " +
              "},   \"abtk\": \"AEIZW7Qp0wvTkShJ5pD9IOTapnEyHm15u8lWopPp6qEe28rI3a+YGvMz8QMhQgVRpgvAvztgJP+L\"  " +
            "} " +
          "}" +
        "]";
  int count = new GooglePlus().parseCount(json);
  assertEquals(3, count);
  }
}