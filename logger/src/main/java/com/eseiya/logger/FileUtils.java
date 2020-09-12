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

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * 文件工具.
 *
 * @author AndyZheng
 * @since 2019/5/4
 */
class FileUtils {
    private FileUtils() {
    }

    public static void write(File file, String data) {
        if (data == null || data.length() == 0) {
            return;
        }
        FileOutputStream out = null;
        try {
            out = openOutputStream(file, true);
            write(data, out);
            out.close();
        } catch (IOException e) {
            Log.w(Constants.TAG, e);
        } finally {
            closeQuietly(out);
        }
    }

    public static void write(File file, char[] data) {
        write(file, data, 0, data.length);
    }

    public static void write(File file, char[] data, int off, int len) {
        FileWriter writer = null;
        try {  //NOSONAR
            writer = new FileWriter(file, true);
            writer.write(data, off, len);
        } catch (IOException e) {
            Log.w(Constants.TAG, e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    Log.w(Constants.TAG, e);
                }
            }
        }
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }

            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        }

        return new FileOutputStream(file, append);
    }

    public static void write(String data, OutputStream output) throws IOException {
        if (data != null) {
            output.write(data.getBytes(Charset.defaultCharset()));
        }
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException var2) {
            //Do nothing
        }
    }

    public static String getFileSuffix(File file) {
        String name = file.getName();
        int pointIndex = name.lastIndexOf('.');
        if (pointIndex <= 0 || pointIndex == name.length() - 1) {//No suffix
            return "";
        }
        return name.substring(pointIndex);
    }
}
