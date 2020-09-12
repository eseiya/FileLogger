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

import java.util.HashMap;
import java.util.Map;

/**
 * Format参数.
 *
 * @author AndyZheng
 * @since 2020/4/26
 */
public class FormatParam {
    private int priority;
    private String tag;
    private String msg;
    private String logcatTag;//用于替换logcat的tag
    private Map<String, Object> extraMap;//存储额外数据
    private Config config;

    public FormatParam(int priority, String tag, String msg, Config config) {
        this.priority = priority;
        this.tag = tag;
        this.msg = msg;
        this.config = config;
        this.logcatTag = tag;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLogcatTag() {
        return logcatTag;
    }

    public void setLogcatTag(String logcatTag) {
        this.logcatTag = logcatTag;
    }

    public void putExtra(String key, Object value) {
        if (extraMap == null) {
            extraMap = new HashMap<>(2);
        }
        extraMap.put(key, value);
    }

    public <T> T getExtra(String key) {
        if (extraMap != null) {
            return (T) extraMap.get(key);
        }
        return null;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
