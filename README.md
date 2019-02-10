# bitcoin API

[![Build Status](https://travis-ci.org/brubom/bitcoin-api.svg?branch=master)](https://travis-ci.org/brubom/bitcoin-api)

This is how I solved the challenge. 

# So what?
  - I'm reallying on GateWay class to abstract [Coindesk](https://www.coindesk.com/api) api call
  - Service class manages the transformation and filter. After a first update from coindesk api, I'm storing values in memory (the idea was to use REDIS), so the next calls are faster
  - I'm using streams to filter and map coindesk api json into a service dto.
  - latest rate executes on O(1), historical rates O(N)
  - I'm using spring Scheduled to run prices updates. It will read cron value from application.properties
  - Spring boot does the magic
  - Swagger for documentation
  
  

### How to run
Just do a:
    
    gradew bootrun

And you're good to go.

### About the bonus
  - Input validation to avoid non-text inputs
  - Swagger docs [swaggerpage](http://localhost:8080/swagger-ui.html)
  - I missed custom REST validations, need to bath my children.
  - Unfortunately, there is no integration test at this moment, I'm tired.
  
