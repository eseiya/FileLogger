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
 * 日志工具.
 *
 * @author AndyZheng
 * @since 2019/5/4
 */
public class LogUtil {
    private static FileLogger mFileLogger;

    private LogUtil() {
    }

    public static void init(Config config) {
        if (mFileLogger == null) {
            mFileLogger = new FileLogger(config);
        }
    }

    public static int v(String msg) {
        return v(null, msg, null);
    }

    public static int v(String msg, Throwable tr) {
        return v(null, msg, tr);
    }

    public static int v(String tag, String msg) {
        return v(tag, msg, null);
    }

    public static int v(String tag, String msg, Throwable tr) {
        return println(Log.VERBOSE, tag, msg, tr);
    }

    public static int va(String msg, Object... args) {
        return v(null, formatMsg(msg, args), null);
    }

    public static int d(String msg) {
        return d(null, msg, null);
    }

    public static int d(String msg, Throwable tr) {
        return d(null, msg, tr);
    }

    public static int d(String tag, String msg) {
        return d(tag, msg, null);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return println(Log.DEBUG, tag, msg, tr);
    }

    public static int da(String msg, Object... args) {
        return d(null, formatMsg(msg, args), null);
    }

    public static int i(String msg, Throwable tr) {
        return i(null, msg, tr);
    }

    public static int i(String msg) {
        return i(null, msg, null);
    }

    public static int i(String tag, String msg) {
        return i(tag, msg, null);
    }

    public static int i(String tag, String msg, Throwable tr) {
        return println(Log.INFO, tag, msg, tr);
    }

    public static int ia(String msg, Object... args) {
        return i(null, formatMsg(msg, args), null);
    }

    public static int w(String msg) {
        return w(null, msg, null);
    }

    public static int w(String msg, Throwable tr) {
        return w(null, msg, tr);
    }

    public static int w(String tag, String msg) {
        return w(tag, msg, null);
    }

    public static int w(String tag, String msg, Throwable tr) {
        return println(Log.WARN, tag, msg, tr);
    }

    public static int wa(String msg, Object... args) {
        return w(null, formatMsg(msg, args), null);
    }

    public static int e(String msg) {
        return e(null, msg, null);
    }

    public static int e(String msg, Throwable tr) {
        return e(null, msg, tr);
    }

    public static int e(String tag, String msg) {
        return e(tag, msg, null);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return println(Log.ERROR, tag, msg, tr);
    }

    public static int ea(String msg, Object... args) {
        return e(null, formatMsg(msg, args), null);
    }

    private static String formatMsg(String msg, Object... args) {
        if (msg == null || args == null || args.length == 0) {
            return msg;
        }
        return String.format(msg, args);
    }

    private static int println(int priority, String tag, String msg, Throwable tr) {
        if (mFileLogger == null) {
            return -1;
        }
        return mFileLogger.log(priority, tag, msg, tr);
    }

    /**
     * 刷日志到文件.
     */
    public static void flushLog() {
        if (mFileLogger == null) {
            return;
        }
        mFileLogger.flush();
    }
}
