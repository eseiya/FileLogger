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

package com.eseiya.logger.sample;

import com.eseiya.logger.Config;
import com.eseiya.logger.LogUtil;

/**
 * 封装打印工具.
 *
 * @author AndyZheng
 * @since 2020/5/16
 */
public class MyLogger {

    private MyLogger() {
    }

    public static void init(Config config) {
        LogUtil.init(config);
    }

    public static int v(String msg) {
        return LogUtil.v(msg);
    }

    public static int v(String msg, Throwable tr) {
        return LogUtil.v(msg, tr);
    }

    public static int va(String msg, Object... args) {
        return LogUtil.va(msg, args);
    }

    public static int d(String msg) {
        return LogUtil.d(msg);
    }

    public static int d(String msg, Throwable tr) {
        return LogUtil.d(msg, tr);
    }

    public static int da(String msg, Object... args) {
        return LogUtil.da(msg, args);
    }

    public static int i(String msg, Throwable tr) {
        return LogUtil.i(msg, tr);
    }

    public static int i(String msg) {
        return LogUtil.i(msg);
    }

    public static int ia(String msg, Object... args) {
        return LogUtil.ia(msg, args);
    }

    public static int w(String msg) {
        return LogUtil.w(msg);
    }

    public static int w(String msg, Throwable tr) {
        return LogUtil.w(msg, tr);
    }

    public static int wa(String msg, Object... args) {
        return LogUtil.wa(msg, args);
    }

    public static int e(String msg) {
        return LogUtil.e(msg);
    }

    public static int e(String msg, Throwable tr) {
        return LogUtil.e(msg, tr);
    }

    public static int ea(String msg, Object... args) {
        return LogUtil.ea(msg, args);
    }

    public static void flushLog() {
        LogUtil.flushLog();
    }
}
