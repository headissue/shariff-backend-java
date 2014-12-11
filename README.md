# shariff-backend-java

A java backend for [https://github.com/heiseonline/shariff](https://github.com/heiseonline/shariff)

## Test

    mvn tomcat7:run

Should result in 'Running war on http://localhost:8080'

Open [http://localhost:8080/?url=www.example.com](http://localhost:8080/?url=www.example.com) to receive

    {"reddit":25,"facebook":474517,"twitter":0,"linkedin":0,"delicious":0,"stumbleupon":5882,"pinterest":0,"google-plus":0}

## Roadmap

* whitelist domains to be requested
* restrict length of parameter
* parallel requests to all provider
* cache the requested counts


*suggestions and feedback welcome*
    
