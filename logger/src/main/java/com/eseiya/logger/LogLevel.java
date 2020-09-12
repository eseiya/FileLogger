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
 * 日志级别.
 *
 * @author AndyZheng
 * @since 2019/5/5
 */
public class LogLevel {

    /**
     * @serial The non-localized name of the level.
     */
    private final String name;

    /**
     * @serial The integer value of the level.
     */
    private final int value;

    private LogLevel(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Priority constant for the println method; Off log.
     */
    public static final LogLevel OFF = new LogLevel("OFF", Integer.MAX_VALUE);

    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final LogLevel VERBOSE = new LogLevel("VERBOSE", Log.VERBOSE);

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final LogLevel DEBUG = new LogLevel("DEBUG", Log.DEBUG);

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final LogLevel INFO = new LogLevel("INFO", Log.INFO);

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final LogLevel WARN = new LogLevel("WARN", Log.WARN);

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final LogLevel ERROR = new LogLevel("ERROR", Log.ERROR);

    /**
     * Priority constant for the println method.
     */
    public static final LogLevel ASSERT = new LogLevel("ASSERT", Log.ASSERT);

    /**
     * Returns a string representation of this LogLevel.
     *
     * @return the non-localized name of the LogLevel, for example "INFO".
     */
    @Override
    public final String toString() {
        return name;
    }

    /**
     * Get the integer value for this level.  This integer value
     * can be used for efficient ordering comparisons between
     * LogLevel objects.
     *
     * @return the integer value for this level.
     */
    public final int intValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
