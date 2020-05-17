
# Event sourcing and CQRS with axon

##Arhitecture 
REST API powered by Commands and Queries.

Commands creates events which are stored in the axon event store and they are also consumed by projections which are used by Queries.
Nicely decoupled architecture that powers scalability.

Events are the source of truth and projections use them to represent them based on the application requirements.
Projections are been kept in memory but if required they can easily be store in a DB.
Reads and writes are fully decoupled allowing the application to scale each individual part separately.
CQRS (Command Query Responsibility Segregation) allows you to have separate models for reading and writing.

##About Axon
Axon provides a unified, productive way of developing Java applications that can evolve without significant refactoring from a monolith to Event-Driven microservices. Axon includes both a programming model as well as specialized infrastructure to provide enterprise ready operational support for the programming model - especially for scaling and distributing mission critical business applications. The programming model is provided by the popular Axon Framework while Axon Server is the infrastructure part of Axon, all open sourced.