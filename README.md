

# FileLogger

Android平台日志工具，可将日志写入文件。

###### 初始化

使用前需要设置相应参数

```
String logFileName = new SimpleDateFormat("yyyyMMdd_HH-mm-ss.SSS", Locale.getDefault()).format(new Date());
File logFile = new File(getExternalCacheDir(), logFileName + ".txt");
Config config = new Config.Builder()
        .setLogFile(logFile)
        .setBufferSize(1024)
        .setMaxFileSize(1024L * 1024L)
        .setFileLogLevel(LogLevel.INFO)
        .setLogcatLevel(LogLevel.VERBOSE)
        .setTimePattern("dd_HH:mm:ss.SSS")
        .setLogcatFormatter(new ThreadLogcatFormatter())
        .setFileLogFormatter(new ThreadFileLogFormatter())
        .build();
LogUtil.init(config);
```

参数说明

- `logFile`，日志文件
- `bufferSize`，内存缓存大小
- `maxFileSize`，日志文件大小
- `fileLogLevel`，写入文件的最低级别
- `logcatLevel`，`logcat`的最低级别
- `timePattern`，时间格式
- `fileLogFormatter`，文件日志模板
- `logcatFormatter`，`logcat`日志模板
- `loggerClass`，打印类的`class`，用于查找调用类的信息；在封装`LogUtil`后需要设置封装类的`class`，否则日志中调用类的信息会不对

可通过设置`fileLogFormatter`和`logcatFormatter`定制需要打印的信息。

###### 使用方法

不带Tag日志

```java
LogUtil.v("v test_single");
```

带Tag日志

```java
LogUtil.i("ZHA", "tag i test_single");
```

带参数日志

```java
LogUtil.va("format %s test", "2");
```

###### 引入组件

```groovy
implementation "com.eseiya.filelogger:logger:1.0.0"
```
