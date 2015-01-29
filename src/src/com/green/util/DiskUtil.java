package com.green.util;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-9
 * Time: ÏÂÎç12:45
 * To change this template use File | Settings | File Templates.
 */
public class DiskUtil {
    public static String formatDouble(double d)
    {
        DecimalFormat df=new DecimalFormat("0.00");
        return df.format(d);
    }
    public static String getExtension(String fileName)
    {
        int i=fileName.lastIndexOf(".");
        return fileName.substring(i+1,fileName.length());
    }
    public static String getNameWithoutExtension(String fileName)
    {
        int i=fileName.lastIndexOf(".");
        return fileName.substring(0,i);
    }
}
