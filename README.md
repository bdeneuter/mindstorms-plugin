# mindstorms-plugin

## How to use it

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

