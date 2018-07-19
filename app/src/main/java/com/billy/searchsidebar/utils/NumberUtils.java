package com.billy.searchsidebar.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数据类型转换
 */
public class NumberUtils {
    /**
     * 将科学数值转成字符串
     *
     * @param num
     * @return
     */
    public static String transDouble(double num) {

        //转化为指定格式
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String newNum = decimalFormat.format(num);
        String[] newNums = newNum.split("\\.");
        if (2 == newNums.length && (TextUtils.equals("00", newNums[1]) || TextUtils.equals("0", newNums[1]))) {
            return newNums[0];
        }
        if (newNums.length == 2) {
            if (newNums[1].length() > 2) {
                String stars = newNums[1].substring(0, 2);
                newNum = newNums[0] + "." + stars;
            }
        }
        return newNum;

    }
    public static int string2Int(String s){
        try {
            return Integer.parseInt(s);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static boolean isEqual(int num,int... nums){
        boolean flag=true;
        for (int i : nums) {
            if (num!=i){
                flag=false;
                break;
            }
        }
        return flag;
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        if (scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1=new BigDecimal(Double.toString(v1));
        BigDecimal b2=new BigDecimal(Double.toString(v2));

        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
