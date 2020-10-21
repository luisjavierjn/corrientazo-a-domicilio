# Su Corrientazo a Domicilio

Corrientazo is a Java project for dealing with a situation where a group of Drones are sent out to deliver lunches. It doesn't use any framework.

## Hexagonal Architecture

This project has been defined using Hexagonal Architecture to separate responsabilities, considering the concepts of Ports/Adapters. In this regard, the project is built using several layers to represent the different connections that can be made for being scalable.

![Image](https://tejidosjulieth.com/Arquitectura-Hexagonal.png)

The packages that make up the application and their relationship with Hexagonal Architecture are shown below:

**domain**: this module contains all the domain objects which are related to the problem that the project is dealing with. Here we can find the following: Drone which is the device that make the shipping, Route which represent the path to follow to get to the address destination and Grid which represents an square geological map for making the Drone to be aware about what it is its location.

**core**: this module contains all the Ports, that mean, the java interfaces that the application use to connect to for using some functionality. So we can find in here ports for the layers Inbound, Outbound, Services and App, where each of them encapsulate an specific behaviour through the Adapters. Some implementations suggest to put all these layers within a parent module called Infrastruture. Nevertheless, it is also useful having these layers at the same level into the project for visual accomodation and for not complicating the files structure.

**inbound**: this module deal with reading text files, but we can create another package inside the Inbound module so we can get the same information from a database. The main reason of this module is dealing with different inputs.

**outbound**: this module deal with writing text files, but we can create another package inside the Outbound module so we can generate the same information to a database. The main reason of this module is dealing with different outputs.

**services**: this module deal with business logic, in this particular case Shippings, but different packages can be created to deal with some others processes.

**app**: this module deal with the methods to run an application, which imply that can be hardwired to an specific platform or framework. In this project for example it is not involved any particular framework like Spring, so for running the application plain Java is used and a factory pattern is used to connect to the ports in the core module and get an implementation from the Adapters.
