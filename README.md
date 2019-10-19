# spring-integration-amqp-async-support
Spring integration supports using intefaces using Gateways but these calls are not async. If you need async support then need to define the interfaces to return a Future. 
This simple wrapper allows to use spring integration framework for interfaces defined wihtout async support but consume them asynchronously.
