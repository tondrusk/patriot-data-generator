# Data Generator library  
The goal of this project provide data generator tool used for emulating common IoT devices 
(sensors, actuators, e.t.c.) in virtualized IoT testing environments. Library offers features like:
simulation of various types of IoT devices, administration of such devices with use of CoAP, basic
serialization and more.

## Getting Started
To include this library to existing project, user has two options:

1. Clone this project and manually compile, package,and install Data Generation library
    ```
    git clone --branch v1.0-thesis https://github.com/jsmolar/patriot-data-generator.git
    cd patriot-data-generator
    mvn clean install
    ``` 
    
    Subsequently, library can be include in the project by adding dependency:
    ```
    <dependency>
        <groupId>io.patriot-framework</groupId>
        <artifactId>generator</artifactId>
        <version>1.0-THESIS</version>
    </dependency>
    ```

2. Include library from Official Maven repository:
    ```
    <dependency>
        <groupId>io.patriot-framework</groupId>
        <artifactId>generator</artifactId>
        <version>2.0.0</version>
    </dependency>
    ```

### Build With

* [Maven](https://maven.apache.org/) - Dependency Management