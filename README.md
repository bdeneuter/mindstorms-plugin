# mindstorms-plugin

This project contains the source code for a gradle plugin for the Lego Mindstorms EV3.
For an example how to use it and to learn how to install java 8 on your EV3 brick, have a look to my [Mindstorms repo](https://github.com/bdeneuter/mindstorms).

## How to use it

Add to your build.gradle file:

```groovy

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.github.bdeneuter:mindstorms-plugin:1.0.0-beta'
    }
}

apply plugin: 'com.github.bdeneuter.mindstorms.ev3'

mindstorms {
    // Your Java main class (the class with the method: public static void main(String ... args))
    main = 'com.github.bdeneuter.mindstorms.samples.HelloWorld'
}

```

The plugin will apply the java plugin and add the [LeJOS EV3 Java API](http://www.lejos.org/ev3/docs/) to your compile classpath. So you can put your source code in the src/main/java folder like a normal java project.

## Adding extra dependencies

If you want to add extra dependencies:

```groovy

dependencies {
    // example
    compile 'com.google.guava:guava:19.0'
}

```

## Configuration

The following properties are expected. You can put them in the gradle.properties file.
The property brick_host is the IP address from your EV3 on your WiFi network. It should be displayed on the screen of the EV3 brick. It is the second IP address.

```properties

brick_user = root
brick_host = 192.168.0.104
brick_home = /home/lejos/programs

```

## Building

Just build your project as a normal Java project in gradle

```
./gradlew build
```

## Deploy

To deploy your application to the EV3 brick.

```
./gradlew copyToRobot
```

## Run

To run your application, select your application on the EV3 brick by using the menu on the brick and launch it.
