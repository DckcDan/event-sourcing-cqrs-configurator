
# Event sourcing and CQRS with the Axon framework.

## Application
The scope of this POC is to implement a simple REST API using event sourcing as a single source of truth and CQRS. 
This is a simple use case of an admin portal API that allows to configure a single product catalog namely bags. 

The configuration is stored as a stream of events, which allows retrieval of the product catalog history as well as having the current state
of the given product catalog.

It will also expose a number of endpoints that allows to push configuration from DRAFT, to DEV environment to UAT and to LIVE. 
The various environment will connect to this API to retrieve their configuration.


How it works:
1-  A business user creates 3 bags.
2 - The bags are logged as events.

As a result of that, the API will offer the following

* An endpoint to retrieve the history of changes on a given product catalog.
* An endpoint to retrieve the current state of the product catalog
* An endpoint to publish a drafted product catalog to DEV, UAT, and LIVE
* An endpoint for the various environments to consume DEV, UAT, and LIVE product configuration.

## Architecture 
REST API powered by the pattern CQRS (Command Query Responsibility Segregation).

Commands creates events which are stored in the axon event store and they are also consumed by projections which are used by Queries.
Nicely decoupled architecture that powers scalability.

* Events are the source of truth and projections use them to represent them based on the application requirements.
* Projections are been kept in memory but if required they can easily be store in a DB.
* Reads and writes are fully decoupled allowing the application to scale each individual part separately.
* CQRS (Command Query Responsibility Segregation) allows you to have separate models for reading and writing.

## About Axon
Axon provides a unified, productive way of developing Java applications that can evolve without significant refactoring from a monolith to Event-Driven microservices. Axon includes both a programming model as well as specialized infrastructure to provide enterprise ready operational support for the programming model - especially for scaling and distributing mission critical business applications. The programming model is provided by the popular Axon Framework while Axon Server is the infrastructure part of Axon, all open sourced.