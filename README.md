# mockp-interceptor
[![](https://jitpack.io/v/brunogabriel/mockp-interceptor.svg)](https://jitpack.io/#brunogabriel/mockp-interceptor)

Mockp-interceptor is a library to simplify Android development requests that uses Retrofit and OkHttp. This library will mock requests using a json file format in assets folder.

Basically you need to put a simple annotation in Retrofit Service with file path, status code (optional, default is 200) and run delay (optional, default is false).


## Getting started

Mockp-Interceptor is distributed through [jitpack](https://jitpack.io/#brunogabriel/mockp-interceptor). You need to add dependency in `build.gradle` file and pay attention in two libraries variants:

- `library`: used in development mode, will mock requests.
- `library-no-op`: used in release mode, will not mock requests, is just a fake interceptor that repass the requests forward.


```groovy
dependencies {
    releaseImplementation 'com.github.brunogabriel.mockp-interceptor:library:0.0.1'
    debugImplementation 'com.github.brunogabriel.mockp-interceptor:library:0.0.1'
}
```

Do not forget to add jitpack configuration too:

```groovy
allprojects {
    repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Thank you [Chucker](https://github.com/ChuckerTeam/chucker) and [Chuck](https://github.com/jgilfelt/chuck). I learnt this approach to no-op interceptors long time ago by using both libraries.


### Configure

It is easy to configure Mockp, just pass context in Builder pattern and optional value if you need to simulate delay. Default delay is between 0 and 1000 milliseconds.

```kotlin
// Create interceptor
val mockpInterceptor = MockPInterceptor
    .Builder(context)
    .addDelayInMillis(5_000L, 10_000L)
    .build()

// Plug in OkHttpClient
val client = OkHttpClient
    .Builder()
    .addInterceptor(mockpInterceptor)
    .build()
```

### How to use it in development

Create assets folder and pass a json file path, for example, response.json, then:

```kotlin
@MOCK(asset = "response.json", runDelay = true)
@GET("character")
suspend fun getProducts(): List<Product>
```

### Libraries

Mockp-Interceptor uses the following open source libraries:

- [OkHttp](https://github.com/square/okhttp) - Copyright Square, Inc.
- [Gson](https://github.com/square/retrofit) - Copyright Square, Inc.

### License

See [LICENSE](LICENSE)