
# Event sourcing and CQRS with axon

## Application
The scope of this POC is to implement a simple application using event sourcing as a single source of truth and CQRS. 
This covers a simple use case of an application to allow configure a product catalog(only bags) for an online e-commerce site.

The whole idea of this project is to manage product configuration and push them to various endpoints that the enviroments can consume or subscribe to.

how it works:
1- A business user creates 3 bags.
2 - The bags are logged as events.
3 - A projection will show the current changes
4-  A rest endpoint will allow to push those changes to a DEV endpoint which will be used by a dev server to consume configuration
5-  Same endpoint will allow to propagate those changes from DEV to LIVE and create an snapshot.


## Arhitecture 
REST API powered by Commands and Queries.

Commands creates events which are stored in the axon event store and they are also consumed by projections which are used by Queries.
Nicely decoupled architecture that powers scalability.

* Events are the source of truth and projections use them to represent them based on the application requirements.
* Projections are been kept in memory but if required they can easily be store in a DB.
* Reads and writes are fully decoupled allowing the application to scale each individual part separately.
* CQRS (Command Query Responsibility Segregation) allows you to have separate models for reading and writing.

## About Axon
Axon provides a unified, productive way of developing Java applications that can evolve without significant refactoring from a monolith to Event-Driven microservices. Axon includes both a programming model as well as specialized infrastructure to provide enterprise ready operational support for the programming model - especially for scaling and distributing mission critical business applications. The programming model is provided by the popular Axon Framework while Axon Server is the infrastructure part of Axon, all open sourced.