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

import com.eseiya.logger.FormatParam;
import com.eseiya.logger.LogFormatter;
import com.eseiya.logger.fomatter.FormatUtils;

/**
 * 带线程信息的Logcat模板.
 *
 * @author AndyZheng
 * @since 2020/9/12
 */
public class ThreadLogcatFormatter implements LogFormatter {
    /**
     * 没有Tag时的模板.
     */
    private static final String NO_TAG_PATTERN = "%s %s";
    /**
     * 有Tag时的模板.
     */
    private static final String HAS_TAG_PATTERN = "%s %s %s";

    @Override
    public String format(FormatParam param) {
        String classInfo = FormatUtils.getClassInfo(param);
        if (param.getTag().isEmpty()) {
            param.setLogcatTag(classInfo);//logcat tag不能为null和空字符，否则logcat不输出日志
            return String.format(NO_TAG_PATTERN, FormatUtils.getThreadInfo(), param.getMsg());
        } else {
            return String.format(HAS_TAG_PATTERN, FormatUtils.getThreadInfo(), classInfo, param.getMsg());
        }
    }
}
