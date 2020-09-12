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


import android.util.Log;

/**
 * 文件日志.
 *
 * @author AndyZheng
 * @since 2019/5/4
 */
public class FileLogger {

    private Config config;
    private BufferFileWriter logWriter;

    public FileLogger(Config config) {
        this.config = config;
        logWriter = new BufferFileWriter(config.getLogFile(), config.getBufferSize(), config.getMaxFileSize());
    }

    public int log(int priority, String tag, String msg, Throwable tr) {
        int resultCode = -1;
        if (!isFileLog(priority) && !isLogcat(priority)) {
            return resultCode;
        }
        if (tr != null) {
            if ((msg == null || msg.isEmpty())) {
                msg = Log.getStackTraceString(tr);
            } else {
                msg += "\n" + Log.getStackTraceString(tr);
            }
        }
        msg = ensureNotNull(msg);
        tag = ensureNotNull(tag);
        FormatParam formatParam = getFormatParam(priority, tag, msg);
        if (isLogcat(priority)) {
            msg = config.getLogcatFormatter().format(formatParam);
            resultCode = Log.println(priority, formatParam.getLogcatTag(), msg);
        }
        if (isFileLog(priority)) {
            logWriter.write(config.getFileLogFormatter().format(formatParam));
        }
        return resultCode;
    }

    private FormatParam getFormatParam(int priority, String tag, String msg) {
        return new FormatParam(priority, tag, msg, config);
    }

    private String ensureNotNull(String value) {
        if (value == null) {
            value = "";
        }
        return value;
    }

    /**
     * 是否需要写入文件.
     *
     * @param priority a message logging priority
     * @return true if the given message priority is currently being logged.
     */
    private boolean isFileLog(int priority) {
        return priority >= config.getFileLogLevel().intValue();
    }

    private boolean isFileLog() {
        return config.getFileLogLevel() != LogLevel.OFF;
    }

    private boolean isLogcat(int priority) {
        return priority >= config.getLogcatLevel().intValue();
    }

    /**
     * 将缓存的日志写入文件.
     */
    public void flush() {
        if (!isFileLog()) {
            return;
        }
        logWriter.flush();
    }
}
