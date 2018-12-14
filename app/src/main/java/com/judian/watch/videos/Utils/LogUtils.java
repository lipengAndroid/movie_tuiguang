package com.judian.watch.videos.Utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

/**
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * <p/>
 * Author: wyouflf
 * Date: 13-7-24
 * Time: 下午12:23
 */
public class LogUtils {

    private static String customTagPrefix = "";

    private LogUtils() {
    }

    /**
     * 是否打印log
     *
     * @param enable
     */
    public static void enableLog(boolean enable) {
        allowE = enable;
    }

    private static boolean allowE = true;

    @SuppressLint("DefaultLocale")
    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    private static CustomLogger customLogger;

    public interface CustomLogger {

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);
    }


    public static void e(String content) {
        if (!allowE) return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (TextUtils.isEmpty(content)) {
            return;
        }
        if (customLogger != null) {
            if (content.length() > 4000) {
                for (int i = 0; i < content.length(); i += 4000) {
                    if (i + 4000 < content.length()) {
                        customLogger.e(tag + i, content.substring(i, i + 4000));
                    } else {
                        customLogger.e(tag + i, content.substring(i, content.length()));
                    }
                }
            }
            customLogger.e(tag, content);
        } else {
            if (content.length() > 4000) {
                for (int i = 0; i < content.length(); i += 4000) {
                    if (i + 4000 < content.length()) {
                        Log.e(tag + i, content.substring(i, i + 4000));
                    } else {
                        Log.e(tag + i, content.substring(i, content.length()));
                    }
                }
            }
            Log.e(tag, content);
        }
    }


    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}