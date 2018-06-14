Ambilight API
=

A very lightweight JVM library allowing to access the [JointSPACE](http://jointspace.sourceforge.net/) interface implemented by Philips televisions to
retrieve or manipulate Ambilight data. Written in Kotlin.

For the JointSPACE interface, see http://jointspace.sourceforge.net/projectdata/documentation/jasonApi/1/doc/API.html

This library uses [OkHTTP](http://square.github.io/okhttp/) and [GSON](https://github.com/google/gson) under the hood.

License: [Apache 2.0](LICENSE)

Usage
-

Include the library in your build:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.imeszaros:ambilight-api:v0.1'
}
```

Instantiate and call the API:
```kotlin
fun main(args: Array<String>) {
    val ambilight = Ambilight("192.168.0.10", "6") // Television IP address and API version
    println(ambilight.getTopology())
    println(ambilight.getProcessed())
}
```

For further parameters and methods, see the code docs.
