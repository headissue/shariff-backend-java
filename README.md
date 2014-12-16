# shariff-backend-java [![Build Status](https://travis-ci.org/headissue/shariff-backend-java.svg?branch=master)](https://travis-ci.org/headissue/shariff-backend-java)

A java backend for [https://github.com/heiseonline/shariff](https://github.com/heiseonline/shariff)

## Deploy

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

To use your own server, simply build the app (`mvn package`) and deploy the resulting war to your favourite application server.

## Test locally

    mvn tomcat7:run

Should result in 'Running war on http://localhost:8080'

Open [http://localhost:8080/?url=www.example.com](http://localhost:8080/?url=www.example.com) to receive
    
```json
{"reddit":25,"facebook":474517,"twitter":10237,"linkedin":0,"delicious":0,"stumbleupon":5882,"pinterest":1,"google-plus":28387}
```

## Configuration

see [Config Class](./src/main/java/com/headissue/sharecount/proxy/Config.java)

Configure the proxy via environment variables:
 
```bash
# here you should provide a way to contact you. this information will be sent with the 
# user-agent header
SHARECOUNT_PROXY_MAINTAINER=my.domain.com
# a semicolon delimited list of domains, regular expressions are possible
SHARECOUNT_PROXY_DOMAINLIST=.*
```

## Roadmap

* restrict length of parameter
* configuration through environment variables
* parallel requests to all provider
* cache the requested counts


*suggestions and feedback welcome*
