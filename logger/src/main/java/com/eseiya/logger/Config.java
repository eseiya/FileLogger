/*
 * Copyright (C) 2020 AndyZheng.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eseiya.logger;

import com.eseiya.logger.fomatter.DefaultFileLogFormatter;
import com.eseiya.logger.fomatter.DefaultLogcatFormatter;

import java.io.File;

/**
 * 日志配置.
 *
 * @author AndyZheng
 * @since 2019/5/5
 */
public class Config {
    private final File logFile;//日志文件
    private final int bufferSize;//内存缓存大小
    private final long maxFileSize;//日志文件大小
    private LogLevel fileLogLevel;//写入文件的最低级别
    private LogLevel logcatLevel;//logcat的最低级别
    private String timePattern;//时间格式
    private LogFormatter fileLogFormatter;//文件日志模板
    private LogFormatter logcatFormatter;//logcat日志模板
    private Class<?> loggerClass;//打印类的class,用于查找调用类的信息;在封装LogUtil后需要设置封装类的class,否则日志中调用类的信息会不对

    private Config(File logFile, int bufferSize, long maxFileSize, LogLevel fileLogLevel, LogLevel logcatLevel,  //NOSONAR
                   String timePattern, LogFormatter fileLogFormatter, LogFormatter logcatFormatter, Class<?> loggerClass) {
        this.logFile = logFile;
        this.bufferSize = bufferSize;
        this.maxFileSize = maxFileSize;
        this.timePattern = timePattern;
        this.fileLogLevel = fileLogLevel;
        this.fileLogFormatter = fileLogFormatter;
        this.logcatLevel = logcatLevel;
        this.logcatFormatter = logcatFormatter;
        this.loggerClass = loggerClass;
        init();
    }

    private void init() {
        if (fileLogLevel == null) {
            fileLogLevel = LogLevel.VERBOSE;
        }
        if (logcatLevel == null) {
            logcatLevel = LogLevel.VERBOSE;
        }
        if (timePattern == null) {
            timePattern = "MM-dd HH:mm:ss";
        }
        if (fileLogFormatter == null) {
            fileLogFormatter = new DefaultFileLogFormatter();
        }
        if (logcatFormatter == null) {
            logcatFormatter = new DefaultLogcatFormatter();
        }
        if (loggerClass == null) {
            loggerClass = LogUtil.class;
        }
    }

    public File getLogFile() {
        return logFile;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public LogLevel getFileLogLevel() {
        return fileLogLevel;
    }

    public LogLevel getLogcatLevel() {
        return logcatLevel;
    }

    public String getTimePattern() {
        return timePattern;
    }

    public LogFormatter getFileLogFormatter() {
        return fileLogFormatter;
    }

    public LogFormatter getLogcatFormatter() {
        return logcatFormatter;
    }

    public Class<?> getLoggerClass() {
        return loggerClass;
    }

    public static class Builder {
        private File logFile;
        private int bufferSize;
        private long maxFileSize;
        private LogLevel fileLogLevel;
        private LogLevel logcatLevel;
        private String timePattern;
        private LogFormatter fileLogFormatter;
        private LogFormatter logcatFormatter;
        private Class<?> loggerClass;

        public Builder setLogFile(File logFile) {
            this.logFile = logFile;
            return this;
        }

        public Builder setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public Builder setMaxFileSize(long maxFileSize) {
            this.maxFileSize = maxFileSize;
            return this;
        }

        /**
         * 打印日志的最低级别.
         *
         * @see android.util.Log#VERBOSE
         */
        public Builder setFileLogLevel(LogLevel fileLogLevel) {
            this.fileLogLevel = fileLogLevel;
            return this;
        }

        public Builder setLogcatLevel(LogLevel logcatLevel) {
            this.logcatLevel = logcatLevel;
            return this;
        }

        public Builder setTimePattern(String timePattern) {
            this.timePattern = timePattern;
            return this;
        }

        public Builder setFileLogFormatter(LogFormatter fileLogFormatter) {
            this.fileLogFormatter = fileLogFormatter;
            return this;
        }

        public Builder setLogcatFormatter(LogFormatter logcatFormatter) {
            this.logcatFormatter = logcatFormatter;
            return this;
        }

        public Builder setLoggerClass(Class<?> loggerClass) {
            this.loggerClass = loggerClass;
            return this;
        }

        public Config build() {
            return new Config(logFile, bufferSize, maxFileSize, fileLogLevel, logcatLevel, timePattern, fileLogFormatter, logcatFormatter, loggerClass);
        }
    }
}
