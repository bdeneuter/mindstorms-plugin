# mindstorms-plugin

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
