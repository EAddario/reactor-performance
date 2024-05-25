# reactor-performance
A simple project to compare and illustrate the advantages of reactive programming over imperative, using [project reactor](https://projectreactor.io/).

## Usage
The project is composed by three services: a backend service and a two "gateway" services, one implementing a reactive style and the other, an imperative. Both will in turn call the backend service which does all of the work.

### blocking-gateway
The `blocking-gateway` service is developed following the traditional Spring MVC approach, and relies on a Tomcat web server listening on port 8081. It exposes the following endpoints:

| Name                       | Purpose                                              |
|----------------------------|------------------------------------------------------|
| /blocking/fibonacci/{num}  | Calculates the Fibonacci number of num               |
| /blocking/fibonacci/random | Calculates a random Fibonacci number                 |
| /blocking/name/{num}       | Generates num random names returning the most common |
| /blocking/nameslist        | Returns a list of names                              |

The service also implements a similar set of APIs to demonstrate the effects of calling a reactive endpoint from/to a blocking service. The endpoints are prefaced with `/reactive` instead of `/blocking`.

### reactive-gateway
The `reactive-gateway` service is developed following a functional style and relies on a Netty web server listening on port 8082. It exposes the following endpoints:

| Name                       | Purpose                                              |
|----------------------------|------------------------------------------------------|
| /reactive/fibonacci/{num}  | Calculates the Fibonacci number of num               |
| /reactive/fibonacci/random | Calculates a random Fibonacci number                 |
| /reactive/name/{num}       | Generates num random names returning the most common |
| /reactive/nameslist        | Returns a list of names                              |
| /reactive/nameslist/stream | Streams a list of names                              |

The service also implements a similar set of APIs to demonstrate the effects of calling a blocking endpoint from/to a reactive service. The endpoints are prefaced with `/blocking` instead of `/reactive`.

### backend-service
The `backend-service` service provides two groups of APIs. One following the Spring MVC approach (`/rest`), and the other, a functional style (`/functional`). It uses a Netty web server listening on port 8080, and supports _blocking → blocking_; _blocking → reactive_; _reactive → blocking_; and _reactive → reactive_ calls. It exposes the following endpoints:

| Name                                  | Purpose                                              |
|---------------------------------------|------------------------------------------------------|
| /rest/blocking/fibonacci/{num}        | Calculates the Fibonacci number of num               |
| /rest/blocking/fibonacci/random       | Calculates a random Fibonacci number                 |
| /rest/blocking/name/{num}             | Generates num random names returning the most common |
| /rest/blocking/nameslist              | Returns a list of names                              |
| /rest/reactive/fibonacci/{num}        | Calculates the Fibonacci number of num               |
| /rest/reactive/fibonacci/random       | Calculates a random Fibonacci number                 |
| /rest/reactive/name/{num}             | Generates num random names returning the most common |
| /rest/reactive/nameslist              | Returns a list of names                              |
| /rest/reactive/nameslist/stream       | Streams a list of names                              |
| /functional/blocking/fibonacci/{num}  | Calculates the Fibonacci number of num               |
| /functional/blocking/fibonacci/random | Calculates a random Fibonacci number                 |
| /functional/blocking/name/{num}       | Generates num random names returning the most common |
| /functional/blocking/nameslist        | Returns a list of names                              |
| /functional/reactive/fibonacci/{num}  | Calculates the Fibonacci number of num               |
| /functional/reactive/fibonacci/random | Calculates a random Fibonacci number                 |
| /functional/reactive/name/{num}       | Generates num random names returning the most common |
| /functional/reactive/nameslist        | Returns a list of names                              |
| /functional/reactive/nameslist/stream | Streams a list of names                              |

### running the reactor-performance project
There are several ways to run the project. You can load and run from your favourite IDE, or you could use any of the below approaches, but bear in mind that both examples assume you'll have three terminals open at the root of the project (_./reactor-performance_) and that **Java 22** is the default version.

#### using maven:

From terminal 1, type: `mvn -f backend-service/ clean compile exec:exec`

From terminal 2, type: `mvn -f blocking-gateway/ clean compile exec:exec`

From terminal 3, type: `mvn -f reactive-gateway/ clean compile exec:exec`

#### using java:
From terminal 1, type:
```
mvn -f backend-service/ clean package
java --enable-preview -XX:+AllowRedefinitionToAddDeleteMethods -XX:+EnableDynamicAgentLoading -jar ./backend-service/target/backend-service-exec.jar
```
From terminal 2, type:
```
mvn -f blocking-gateway/ clean package
java --enable-preview -jar ./blocking-gateway/target/blocking-gateway-exec.jar
```
From terminal 3, type:
```
mvn -f reactive-gateway/ clean package
java --enable-preview -XX:+AllowRedefinitionToAddDeleteMethods -XX:+EnableDynamicAgentLoading -jar ./reactive-gateway/target/reactive-gateway-exec.jar
```
The project includes [performance-test](./performance-test), a *nix script to stress-test the system. It uses [h2load](https://nghttp2.org/documentation/h2load-howto.html) to perform the benchmark. Instructions on how to install or use _h2load_ are beyond the scope of this project, but Google or your favorite LLM AI will help.

Alternatively, any other web server benchmark tool like [_ab_](https://httpd.apache.org/docs/2.4/programs/ab.html) or [_httperf_](https://github.com/httperf/httperf) will do, provided you adjust the script parameters accordingly.

Executing `performance-test -h` will print the following message:
```
Usage: performance-test

Tests reactive vs blocking servers

Options:
  -c | --clients - Number of concurrent clients. Defaults to 1
  -f | --fibonacci - Fibonacci number to calculate. Defaults to 16
  -n | --names - Number of random names to generate. Defaults to 256
  -o | --output <filename> - Save output to named file. Defaults to stdout only
  -r | --requests  - Number of requests across all clients. Defaults to 1
  -s | --streams - Number of HTTP pipelining requests. Defaults to 1
  -t | --threads - Number of threads to use. Defaults to 1
  -h | --help - Displays usage information and then exits
```
For example, the command `performance-test -c 10 -r 10 -t 10 -s 10` will launch ten clients, each running on its own thread, and will perform one request per client to each of the gateway services.

## Release History
* v1.0.0 - Initial release

## Known Issues
None

## Motivation
TBA
