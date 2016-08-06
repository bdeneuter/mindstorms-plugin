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
        classpath 'com.github.bdeneuter:mindstorms-plugin:1.1.0'
    }
}

apply plugin: 'com.github.bdeneuter.mindstorms.ev3'

mindstorms {
    // Your Java main class (the class with the method: public static void main(String ... args))
    main = 'com.github.bdeneuter.mindstorms.samples.HelloWorld'
    
    // Optional: The IP address from the EV3 brick. Default value is 10.0.1.1
    ip = '10.0.1.1'
    
    // Optional: the user for the EV3 brick. Default value is root
    user = 'root'
    
    // Optional: the password for the user of the EV3 brick (Don't check in paswords in repositories! If you use a password, put it in the gradle.properties in your GRADLE_HOME and assign the variable to the password field. Default value is none
    password = ''
    
    // Optional: the directory on the EV3 brick where the applications will be stored. Default is /home/lejos/programs
    home = '/home/lejos/programs'
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
Or run your application from gradle with this plugin:

```
./gradlew launch
```
