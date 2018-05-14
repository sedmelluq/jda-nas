# JDA-NAS - JDA Native Audio System

JDA-NAS is an alternative to JDA's built-in audio packet sending system. It keeps a buffer of audio packets in native code and also sends them from there. This way it is unaffected by GC pauses shorter than the duration of the buffer (400ms by default) and gets rid of stuttering caused by those.

#### Maven package

Replace `x.y.z` with the latest version number: [![Bintray](https://api.bintray.com/packages/sedmelluq/com.sedmelluq/jda-nas/images/download.svg)](https://bintray.com/sedmelluq/com.sedmelluq/jda-nas)

* Repository: jcenter
* Artifact: **com.sedmelluq:jda-nas:x.y.z**

Using in Gradle:
```groovy
repositories {
  jcenter()
}

dependencies {
  compile 'com.sedmelluq:jda-nas:x.y.z'
}
```

Using in Maven:
```xml
<repositories>
  <repository>
    <id>central</id>
    <name>bintray</name>
    <url>http://jcenter.bintray.com</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.sedmelluq</groupId>
    <artifactId>jda-nas</artifactId>
    <version>x.y.z</version>
  </dependency>
</dependencies>
```


## Usage

Using it is as simple as just calling calling this on a JDABuilder:
```java
.setAudioSendFactory(new NativeAudioSendFactory())
```

For example:
```java
new JDABuilder(AccountType.BOT)
    .setToken(System.getProperty("botToken"))
    .setAudioSendFactory(new NativeAudioSendFactory())
    .buildBlocking()
```

## Supported platforms

As it includes a native library, it is only supported on a specific set of platforms currently:

* Windows (x86 and x64)
* Linux (x86 and x64, glibc >= 2.15)
