# Su Corrientazo a Domicilio

Corrientazo is a Java project for dealing with a situation where a group of Drones are sent out to deliver lunches. It doesn't use any framework.

## Hexagonal Architecture

This project has been defined using Hexagonal Architecture to separate responsabilities, considering the concepts of Ports/Adapters. In this regard, the project is built using several layers to represent the different connections that can be made for being scalable.

![Image](https://tejidosjulieth.com/Arquitectura-Hexagonal.png)

The packages that make up the application and their relationship with Hexagonal Architecture are shown below:

**domain**: this module contains all the domain objects which are related to the problem that the project is dealing with. Here we can find the following: Drone which is the device that make the shipping, Route which represent the path to follow to get to the address destination and Grid which represents an square geological map for making the Drone to be aware about what it is its location.

**core**: this module contains all the Ports, that means, the java interfaces that the application use to connect to for using some functionality. So we can find in here ports for the layers Inbound, Outbound, Services and App, where each of them encapsulate an specific behaviour through the Adapters. Some implementations suggest to put all these layers within a parent module called Infrastruture. Nevertheless, it is also useful having these layers at the same level into the project for visual accomodation and for not complicating the files structure.

**inbound**: this module deal with reading text files, but we can create another package inside the Inbound module so we can get the same information from a database. The main reason of this module is dealing with different inputs.

**outbound**: this module deal with writing text files, but we can create another package inside the Outbound module so we can generate the same information to a database. The main reason of this module is dealing with different outputs.

**services**: this module deal with business logic, in this particular case Shippings, but different packages can be created to deal with some others processes.

**app**: this module deal with the methods to run an application, which imply that can be hardwired to an specific platform or framework. In this project for example it is not involved any particular framework like Spring, so for running the application plain Java is used and a factory pattern is used to connect to the ports in the core module and get an implementation from the Adapters.

## Unit tests and Coverage

Every module has its own set of unit test with JUnit4. Below is shown the result of the unit tests for each module and files:

**domain**<br/>
Element|Class, %|Method, %|Line, %
-------|--------|---------|-------
Drone|100% (1/1)|100% (8/8)|100% (35/35)
Grid|100% (1/1)|100% (5/5)|100% (53/53)
Route|100% (1/1)|100% (5/5)|100% (11/11)

**core**<br/>
Element|Class, %|Method, %|Line, %
-------|--------|---------|-------
FileEvent|100% (1/1)|100% (2/2)|100% (3/3)
FileListener|100% (1/1)|100% (3/3)|100% (4/4)

**inbound**<br/>
Element|Class, %|Method, %|Line, %
-------|--------|---------|-------
FileWatcherAdapter|100% (1/1)|100% (11/11)|94% (48/51)

**outbound**<br/>
Element|Class, %|Method, %|Line, %
-------|--------|---------|-------
FileWriterAdapter|100% (1/1)|100% (4/4)|100% (21/21)

**services**<br/>
Element|Class, %|Method, %|Line, %
-------|--------|---------|-------
DeliveryServiceAdapter|100% (1/1)|100% (5/5)|100% (18/18)

**app**<br/>
Element|Class, %|Method, %|Line, %
-------|--------|---------|-------
ConsoleAppFactory|100% (1/1)|100% (3/3)|100% (4/4)
Main|100% (2/2)|75% (3/4)|44% (26/59)

*Note*: BDD using Cucumber was configured for this project. In the **app** module inside the test directory there are the files main.feature, Runner and RunnerSteps which are up and running but the test itself need still some work.

## Assumptions

First of all, below is shown the cartesian plane assigned to the Drone before making the deliveries and it can be seen that there is 10 blocks  (customizable) around from the headquarter.

![Image](https://tejidosjulieth.com/Initial-Position.png)

It is known that every Drone can carry 3 lunches once it start the shipping. So we can assume that each Drone return to the headquarter only when it finish to deliver, that said, then the routes are actually nested routes, that means, the next route description start from the last delivery destination. So let's see which would it be the path followed by a Drone with the following routes:

Routes|
------|
AAAAIAA|
DDDAIAD|
AAIADAD|

*Note*: Just remember, **A** means the Drone Advance, **I** means the Drone turn left (90 grades) and **D** means the Drone turn right (-90 grades)

*First delivery*: black arrow is the initial position, blue arrows are the movements and orange arrow the final move
![Image](https://tejidosjulieth.com/First-delivery-AAAAIAA.png)

*Second delivery*: black arrow is the initial position, blue arrows are the movements and orange arrow the final move
![Image](https://tejidosjulieth.com/Second-delivery-DDDAIAD.png)

*Third delivery*: black arrow is the initial position, blue arrows are the movements and orange arrow the final move
![Image](https://tejidosjulieth.com/Third-delivery-AAIADAD.png)

## Tools

For this console project were used: 
* Java 8 
* Maven 
* Intellij
* GitFlow 
* JUnit 4 
* Cucumber 6. 

## Implementation and Usage 

In order to make the Drones deliver lunches CompletableFuture was used for asynchronous programming. Asynchronous programming is a means of writing non-blocking code by running a task on a separate thread than the main application thread and notifying the main thread about its progress, completion or failure. That means the Drones are able to make shipping without the risk of blocking each others work.

For reading the routes that every Drone had to follow the WatchService interface was implemented to monitor the filesystem, so all we need is to drop the text files into a directory to be consumed.

The domain objects were programmed as immutable objects, so we are sure that the results returned by functions and methods are consistence with the information given to them through the objects. Besides, the Grid class from domain is using functional programming for componing calculations related to the destinations the Drone has to arrive according to the routes.

Regarding the usage, this is a console plain Java project, and the main function accept four parameters. The first one is number of lunches that each Drone is able to carry, the default value is three, the second parameter is the size of the grid as number of blocks, the default parameter is ten, the third parameter is the number of Drones that we have, and the fourth parameter is number of milliseconds the main function is going to be hanging waiting for input files, this las parameters is useful for running unit test over the main function so it can return in short period of time.

