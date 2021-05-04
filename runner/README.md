# PatrIoT Data Generator Runner
Simple Java application that is used for running Data Generator Devices inside docker container.


## How to create docker image
- Compile source code to get runnable jar file with the following command:
```shell
mvn clean compile assembly:single 
```
- Build docker image with the following command:
```shell
docker build . -t <YOUR_NAME>/patriot-data-generator-runner:latest
```

## How to run PatrIoT Data Generator Runner

The configuration for the data generator device is passed via file.
In order to run the docker image you need to mount the configuration file to the container.

```shell
docker run \
  --env PATRIOT_DATA_GENERATOR_DEVICE_FILE=/var/<FILE_NAME> \
  --mount type=bind,source="<PATH_TO_THE_FILE_DIRECTORY>,target=/var" \
   -p 5683:5683/udp \
   <YOUR_NAME>/patriot-data-generator-runner:latest
```

### Running Device

In order to run specific Device in the container you need to set environment variable `PATRIOT_DATA_GENERATOR_DEVICE_FILE` 
which represents path to the configuration file.

### Running ActiveDevice
The setup is similiar as for the Device with the change of the environment variable name to `PATRIOT_DATA_GENERATOR_ACTIVE_FILE`.


## How to get configuration file
Create device you want to deploy, for example:
```java
DataFeed dataFeed = new NormalDistVariateDataFeed(18, 2);
Device device = new Thermometer("simpleThermometer", dataFeed);
```
Serialize device
```java
JSONSerializer.serialize(device, new File("<PATH_TO_YOUR_FILE>"));
```
Use the file as configuration for data generator runner.