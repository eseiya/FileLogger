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

import com.eseiya.logger.FormatParam;
import com.eseiya.logger.LogFormatter;

/**
 * 默认Logcat格式化工具.
 *
 * @author AndyZheng
 * @since 2020/4/26
 */
public class DefaultLogcatFormatter implements LogFormatter {
    private static final String PATTERN = "%s %s";

    @Override
    public String format(FormatParam param) {
        String classInfo = FormatUtils.getClassInfo(param);
        if (param.getTag().isEmpty()) {
            param.setLogcatTag(classInfo);//logcat tag不能为null和空字符，否则logcat不输出日志
            return param.getMsg();
        } else {
            return String.format(PATTERN, classInfo, param.getMsg());
        }
    }
}
