package com.judian.watch.videos.Utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static HashMap getUrl(String str) {
        List<String> URLS = new ArrayList<>();
        String[] aa = str.split("\r");
        URLS.addAll(Arrays.asList(aa));
        HashMap map = new HashMap();


        for (int i = 0; i < URLS.size(); i++) {
            String[] types = URLS.get(i).split("\\$");
//            if (types.length > 1) {
//                title = types[0];
//            } else {
//                map.put("", "");
//            }
            if (types.length == 2) {
                map.put(types[0], types[1]);
            } else if (types.length == 1 && types[0].contains("http")) {
                map.put("1", types[0]);
            }


        }

        return map;
    }

    public static List<String> sp(String str, String sp) {
        List<String> A = new ArrayList<>();
        if (TextUtils.isEmpty(str)) {
            A.add("");
        } else if (str.contains(sp)) {
            A.addAll(Arrays.asList(str.split(sp)));
        } else {
            A.add(str);
        }
        return A;
    }

    public static List<List<String>> getUrls(String str) {
        int a = 1;
        List<String> URLS = new ArrayList<>();
        String[] aa = str.split("\r");
        URLS.addAll(Arrays.asList(aa));
        List<String> title = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        List<List<String>> lists = new ArrayList<>();

        for (int i = 0; i < URLS.size(); i++) {
            if (URLS.get(i).contains("$")) {
                String[] types = URLS.get(i).split("\\$");
                types[0] = types[0].replace("\r", "");
                types[0] = types[0].replace("\n", "");
                title.add(types[0]);
                urls.add(types[1]);

            } else {
                title.add(a++ + "");
                urls.add(URLS.get(i));
            }
        }
        lists.add(title);
        lists.add(urls);
        return lists;
    }

    public static String initTitleData(String title) {
        switch (title) {
            case "iqiyi":
                title = "爱奇艺";
                break;
            case "sohu":
                title = "搜狐";
                break;
            case "letv":
                title = "乐视网";
                break;
            case "qq":
                title = "腾讯视频";
                break;
            case "ykyun":
                title = "优酷云";
                break;
            case "mgtv":
                title = "芒果TV";
                break;
            case "tudou":
                title = "土豆网";
                break;
            case "pptv":
                title = "PPTV";
                break;
            case "youku":
                title = "优酷网";
                break;
            case "cntv":
                title = "cntv";
                break;
            case "cctv":
                title = "cctv";
                break;
            case "pps":
                title = "pps";
                break;
            case "135m3u8":
                title = "云资源1";
                break;
            case "33uuck":
                title = "云资源2";
                break;
            default:
                title = "云资源3";
                break;
        }
        return title;
    }

    /**
     * 自动转译String 中的特殊字符
     *
     * @param str
     * @return 转译String 中的特殊字符
     */
    public static String HandlingSpecialCharacters(String str) {
        StringBuilder newString = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            char[] a = str.toCharArray();
            for (char anA : a) {
                String b = String.valueOf(anA);
                if (!isConSpeCharacters(b)) {
                    b = "\\" + b;
                }
                newString.append(b);
            }

            return newString.toString();
        } else {
            return str;
        }
    }

    /**
     * true 不是  flase 是
     *
     * @param string
     * @return 是否是特殊字符
     */
    private static boolean isConSpeCharacters(String string) {
        // TODO Auto-generated method stub
        return string.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0;
    }

    public static String unicodeToString(String stringOrUnicode) {
        StringBuilder sb = new StringBuilder(stringOrUnicode.length());
        char[] chars = stringOrUnicode.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '\\' && chars[i + 1] == 'u') {
                char cc = 0;
                for (int j = 0; j < 4; j++) {
                    char ch = Character.toLowerCase(chars[i + 2 + j]);
                    if ('0' <= ch && ch <= '9' || 'a' <= ch && ch <= 'f') {
                        cc |= (Character.digit(ch, 16) << (3 - j) * 4);
                    } else {
                        cc = 0;
                        break;
                    }
                }
                if (cc > 0) {
                    i += 5;
                    sb.append(cc);
                    continue;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * json格式化输出
     *
     * @param string
     * @return JSON_STRING
     */
    public static String stringFormatting(String string) {

        StringBuilder json = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            String chars = string.charAt(i) + "";


            if (chars.equals("\\") && String.valueOf(string.charAt(i + 1)).equals("/")) {
                continue;
            }
            json.append(string.charAt(i));
            if ((chars.equals(",") && String.valueOf(string.charAt(i + 1)).equals("\""))
                    || chars.equals("{") || chars.equals("}")) {
                json.append("\n");
            }
        }
        return json.toString();
    }
}
