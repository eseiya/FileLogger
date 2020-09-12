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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.eseiya.logger.Config;
import com.eseiya.logger.LogLevel;
import com.eseiya.logger.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        LogUtil.i("logFile " + logFile);
        LogUtil.i("onCreate");
        LogUtil.flushLog();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInThread();
                logInThread();
                logInThread();
                logInThread();
            }
        });
        findViewById(R.id.test_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.v("v test_single");
                LogUtil.d("d test_single");
                LogUtil.i("i test_single");
                LogUtil.w("w test_single");
                LogUtil.e("e test_single ", new RuntimeException("test_single"));
                LogUtil.i("ZHA", "tag i test_single");
                String formatStr = "format %s test";
                LogUtil.va(formatStr, "2");
                LogUtil.da(formatStr, "2");
                LogUtil.ia("format test", "3");
                LogUtil.wa(formatStr, "4", "5");
                LogUtil.ea("format %s and %s test", "2", 3);
                LogUtil.flushLog();
            }
        });
    }

    private void logInThread() {
        new Thread() {
            @Override
            public void run() {
                log();
            }
        }.start();
    }

    private void log() {
        for (int i = 0; i < 100; i++) {
            LogUtil.ia("log %s", i);
        }
        LogUtil.w(Thread.currentThread().getName() + " logFile over");
        LogUtil.flushLog();
    }

}
