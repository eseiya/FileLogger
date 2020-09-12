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

package com.eseiya.logger.fomatter;

import android.util.Log;

import com.eseiya.logger.Config;
import com.eseiya.logger.FormatParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式化工具.
 *
 * @author AndyZheng
 * @since 2020/4/26
 */
public class FormatUtils {
    private static final Pattern ANONYMOUS_CLASS = Pattern.compile("\\$\\d+$");
    private static ThreadLocal<SimpleDateFormat> localFormat = new ThreadLocal<>();
    private static ThreadLocal<Date> localData = new ThreadLocal<>();
    private static final String KEY_CLASS_INFO = "class_info";
    private static int sStackTraceIndex;//调用类在栈中的索引

    private FormatUtils() {
    }

    public static String getTime(String timePattern) {
        SimpleDateFormat sdf = localFormat.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat(timePattern, Locale.getDefault());
            localFormat.set(sdf);
        }
        Date date = localData.get();
        if (date == null) {
            date = new Date();
            localData.set(date);
        }
        date.setTime(System.currentTimeMillis());
        return sdf.format(date);
    }

    /**
     * 优先级信息.
     */
    public static String getPriorityInfo(int priority) {
        switch (priority) {
            case Log.VERBOSE:
                return "V";
            case Log.DEBUG:
                return "D";
            case Log.INFO:
                return "I";
            case Log.WARN:
                return "W";
            case Log.ERROR:
                return "E";
            case Log.ASSERT:
                return "A";
            default:
                return "";
        }
    }

    /**
     * 获取类信息.
     */
    public static String getClassInfo(FormatParam param) {
        String classInfo = param.getExtra(KEY_CLASS_INFO);
        if (classInfo == null) {
            classInfo = getClassInfo(param.getConfig());
            param.putExtra(KEY_CLASS_INFO, classInfo);
        }
        return classInfo;
    }

    /**
     * 获取类信息.
     */
    public static String getClassInfo(Config config) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int stackTraceIndex = getStackTraceIndex(stackTrace, config);
        StackTraceElement element = stackTrace[stackTraceIndex];
        String className = element.getClassName();
        Matcher m = ANONYMOUS_CLASS.matcher(className);
        if (m.find()) {
            className = m.replaceAll("");
        }
        return className.substring(className.lastIndexOf('.') + 1) + "[" + element.getLineNumber() + "]";
    }

    private static int getStackTraceIndex(StackTraceElement[] stackTrace, Config config) {
        if (sStackTraceIndex > 0) {
            return sStackTraceIndex;
        }
        int stackTraceIndex = 0;
        for (int i = 0, stackTraceLength = stackTrace.length; i < stackTraceLength - 1; i++) {
            if (stackTrace[i].getClassName().equals(config.getLoggerClass().getName()) &&
                    !stackTrace[i + 1].getClassName().equals(config.getLoggerClass().getName())) {
                stackTraceIndex = i + 1;
                break;
            }
        }
        sStackTraceIndex = stackTraceIndex;
        return stackTraceIndex;
    }

    /**
     * 线程信息.
     */
    public static String getThreadInfo() {
        return Thread.currentThread().getName();
    }

    public void destroy() {
        localFormat.remove();
        localData.remove();
    }
}
