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

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.File;
import java.nio.CharBuffer;

/**
 * 缓存文件写入工具.
 *
 * @author AndyZheng
 * @since 2019/5/4
 */
public class BufferFileWriter {
    private static final int MSG_WHAT_WRITE = 1;
    private static final int MSG_WHAT_FLUSH = 2;
    /**
     * 最小缓存大小.
     */
    private static final int MIN_BUFFER_SIZE = 512;
    /**
     * 最大缓存大小.
     */
    private static final int MAX_BUFFER_SIZE = 1024 * 64;
    /**
     * 默认缓存大小.
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;
    /**
     * 默认文件大小.
     */
    private static final long DEFAULT_FILE_SIZE = 1024 * 512L;
    private long maxFileSize = 1024 * 512L;//Default is 512K.

    private File file;
    private File fileDir;
    private String fileName;
    private String fileSuffix;
    private int fileCount = 1;
    private CharBuffer buffer;
    private Handler fileHandler;

    public BufferFileWriter(File file, int bufferSize, long maxFileSize) {
        this.file = file;
        initMaxFileSze(maxFileSize);
        bufferSize = initBufferSize(bufferSize);
        buffer = CharBuffer.allocate(bufferSize);
        initFileParam();
        initFileHandler();
    }

    private void initMaxFileSze(long maxFileSize) {
        if (maxFileSize > 0) {
            this.maxFileSize = maxFileSize;
        } else {
            this.maxFileSize = DEFAULT_FILE_SIZE;
        }
    }

    private int initBufferSize(int bufferSize) {
        if (bufferSize > 0) {
            if (bufferSize < MIN_BUFFER_SIZE) {
                bufferSize = MIN_BUFFER_SIZE;
            } else if (bufferSize > MAX_BUFFER_SIZE) {
                bufferSize = MAX_BUFFER_SIZE;
            }
        } else {
            bufferSize = DEFAULT_BUFFER_SIZE;
        }
        return bufferSize;
    }

    private void initFileParam() {
        fileDir = file.getParentFile();
        fileSuffix = FileUtils.getFileSuffix(file);
        String name = file.getName();
        if (fileSuffix.length() > 0) {
            fileName = name.substring(0, name.indexOf(fileSuffix));
        } else {
            fileName = name;
        }
    }

    private void initFileHandler() {
        HandlerThread thread = new HandlerThread(Constants.NAME, android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        fileHandler = new Handler(thread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MSG_WHAT_WRITE) {
                    writeInternal((String) msg.obj);
                } else if (msg.what == MSG_WHAT_FLUSH) {
                    writeToFile();
                }
                return true;
            }
        });
    }

    private void writeInternal(String data) {
        if (data.length() < buffer.remaining()) {
            buffer.put(data);
        } else {// buffer剩余空间不足
            int start = 0;
            while (start < data.length()) {//循环检查的目的是防止data过大，分多次才能写完
                int length = Math.min(data.length() - start, buffer.remaining());
                buffer.put(data, start, start + length);
                start = start + length;
                if (buffer.remaining() == 0) { //buffer full
                    writeToFile();
                }
            }
        }
    }

    private void writeToFile() {
        if (buffer.position() == 0) {//有数据时才写文件
            return;
        }
        if (file.length() >= maxFileSize) {//文件超过最大限制时创建新文件
            if (renameOldFile()) { //NOSONAR
                createNewFile();
            }
        }
        FileUtils.write(file, buffer.array(), 0, buffer.position());
        buffer.clear();
    }

    private boolean renameOldFile() {
        File newFile = getNewFile();
        return file.renameTo(newFile);
    }

    private void createNewFile() {
        fileCount++;
        file = getNewFile();
    }

    private File getNewFile() {
        return new File(fileDir, getNewFileName());
    }

    private String getNewFileName() {
        return fileName + "." + fileCount + fileSuffix;
    }

    public void write(String data) {
        Message.obtain(fileHandler, MSG_WHAT_WRITE, data).sendToTarget();
    }

    public void flush() {
        Message.obtain(fileHandler, MSG_WHAT_FLUSH).sendToTarget();
    }
}
