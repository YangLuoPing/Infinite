package com.base.common.poi.util;

/**
 * <p>Title: 美林java项目公用代码库－Http参数工具类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Merit</p>
 * @author Shark
 * @version 1.0
 */

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

/**
 * 参数传递工具类
 */

public class ParamUtils {

    /**
     * 构造函数(默认的，最基本的)
     */
    public ParamUtils() {
    }

    /**
     * 转换成encode
     * 
     * @param src
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String encode(String src) {
        if (src == "" || src == null) {
            return "";
        } else {
            return URLEncoder.encode(src);
        }
    }

    /**
     * 转换成decode
     * 
     * @param src
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String decode(String src) {
        if (src == "" || src == null) {
            return "";
        } else {
            return URLDecoder.decode(src);
        }
    }

    /**
     * 编码转化函数
     * 
     * @param s
     * @return
     */
    public static String A2C(String s) {
        return s;
        // System.out.println("a2c="+s);
        /*
         * char[] orig = s.toCharArray(); byte[] dest = new byte[orig.length];
         * for (int i = 0; i < orig.length; i++) { dest[i] = (byte) (orig[i] &
         * 0xFF); } try { ByteToCharConverter toChar =
         * ByteToCharConverter.getConverter("GBK"); return new
         * String(toChar.convertAll(dest)); } catch (Exception e) {
         * System.out.println(e); return s; }
         */
    }

    /**
     * 获取一组字符串参数
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request" .
     * @param paramName
     *            要获取的参数名
     * @return 参数值（数组形式）
     * @return null(没有找到参数,或者参数长度为0)
     */
    public static String[] getParameterValues(HttpServletRequest request,
            String paramName) {
        return getParameterValues(request, paramName, false);
    }

    /**
     * 获取一组字符串参数
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request" .
     * @param paramName
     *            要获取的参数名
     * @param emptyStringsOK
     *            返回参数值是否为空的布尔值
     * @return 参数值（数组形式）如果没有找到参数时返回空字符串(emptyStringsOK=true)
     */
    public static String[] getParameterValues(HttpServletRequest request,
            String paramName, boolean emptyStringsOK) {
        String temp[] = request.getParameterValues(paramName);
        if (temp != null) {
            if (temp.length == 0 && !emptyStringsOK) {
                return null;
            } else {
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = A2C(temp[i]);
                }
                return temp;
            }
        } else if (!emptyStringsOK) {
            return null;
        } else {
            return new String[] {};
        }
    }

    /**
     * 获取一个字符串参数
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request" .
     * @param paramName
     *            要获取的参数名
     * @return 参数值
     * @return null(没有找到参数,或者参数长度为0)
     */
    public static String getParameter(HttpServletRequest request,
            String paramName) {
        return getParameter(request, paramName, false);
    }

    /**
     * 获取参数字符串
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request" .
     * @param paramName
     *            要获取的参数名
     * @param emptyStringsOK
     *            返回参数值是否为空的布尔值
     * @return 参数值 ()如果没有找到参数时返回空字符串(emptyStringsOK=true)
     */
    public static String getParameter(HttpServletRequest request,
            String paramName, boolean emptyStringsOK) {
        String temp = request.getParameter(paramName);
        if (temp != null) {
            if ((temp.equals("") || temp.equalsIgnoreCase("null"))
                    && !emptyStringsOK) {
                return null;
            } else if ((temp.equals("") || temp.equalsIgnoreCase("null"))
                    && emptyStringsOK) {
                return "";
            } else {
                return A2C(temp);
            }
        } else if (!emptyStringsOK) {
            return null;
        } else {
            return "";
        }
    }

    /**
     * 获取一个布尔类型的参数
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request" .
     * @param paramName
     *            要获取的参数名称
     * @return True 参数为真
     * @return alse 参数为假
     */
    public static boolean getBooleanParameter(HttpServletRequest request,
            String paramName) {
        String temp = request.getParameter(paramName);
        if (temp != null && temp.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取一个整型参数
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request"
     * @param paramName
     *            要获取的参数名
     * @return 指定整型参数值
     * @return 参数默认值(如果参数没有找到)
     */
    public static int getIntParameter(HttpServletRequest request,
            String paramName, int defaultNum) {
        String temp = request.getParameter(paramName);
        if (temp != null && !temp.equals("")) {
            int num = defaultNum;
            try {
                num = Integer.parseInt(temp);
            } catch (Exception ignored) {
            }
            return num;
        } else {
            return defaultNum;
        }
    }

    /**
     * 获取一个长整型参数
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request"
     * @param paramName
     *            要获取的参数名
     * @return 指定长整型参数值
     * @return 参数默认值(如果参数没有找到)
     */
    public static long getLongParameter(HttpServletRequest request,
            String paramName, long defaultNum) {
        String temp = request.getParameter(paramName);
        if (temp != null && !temp.equals("")) {
            long num = defaultNum;
            try {
                num = Long.parseLong(temp);
            } catch (Exception ignored) {
            }
            return num;
        } else {
            return defaultNum;
        }
    }

    /**
     * 获取一个checkbox类型参数的布尔值
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request"
     * @param paramName
     *            要获取的参数名
     * @return True checkbox的状态为"on"
     * @return false checkbox的状态不为"on"
     */
    public static boolean getCheckboxParameter(HttpServletRequest request,
            String paramName) {
        String temp = request.getParameter(paramName);
        if (temp != null && temp.equals("on")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取参数字符串
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request"
     * @param attribName
     *            要获取的参数名
     * @return 参数值
     * @return null(没有找到参数,或者参数长度为0)
     */
    public static String getAttribute(HttpServletRequest request,
            String attribName) {
        return getAttribute(request, attribName, false);
    }

    /**
     * 获取参数字符串
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request"
     * @param attribName
     *            参数名
     * @param emptyStringsOK
     *            参数的是否为空布尔值
     * @return 参数值
     * @return null(没有找到参数)
     */
    public static String getAttribute(HttpServletRequest request,
            String attribName, boolean emptyStringsOK) {
        String temp = (String) request.getAttribute(attribName);
        if (temp != null) {
            if (temp.equals("") && !emptyStringsOK) {
                return null;
            } else {
                return A2C(temp);
            }
        } else if (!emptyStringsOK) {
            return null;
        } else {
            return "";
        }
    }

    /**
     * 获取属性的布尔值
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"request"
     * @param attribName
     *            要获取的参数属性
     * @return True 参数值的属性为真
     * @return false 参数值的属性为假
     */
    public static boolean getBooleanAttribute(HttpServletRequest request,
            String attribName) {
        String temp = (String) request.getAttribute(attribName);
        if (temp != null && temp.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取属性的整型值
     * 
     * @param 获取
     *            <code> The HttpServletRequest</code>对象, 正如<code>JSP</code>
     *            页面的"reques
     * @param attribName
     *            要获取的参数值属性
     * @return 属性的整型值
     * @return 属性默认值(没有找到该参数属性)
     */
    public static int getIntAttribute(HttpServletRequest request,
            String attribName, int defaultNum) {
        String temp = (String) request.getAttribute(attribName);
        if (temp != null && !temp.equals("")) {
            int num = defaultNum;
            try {
                num = Integer.parseInt(temp);
            } catch (Exception ignored) {
            }
            return num;
        } else {
            return defaultNum;
        }
    }

    public static void main(String[] args) {
        String paramStr = "~!@#$^&*()_+|`1234567890-=\\中国 ";
        System.out.println(encode(paramStr));
        System.out.println(decode(paramStr));
        System.out.println(decode(encode(paramStr)));
    }
}
