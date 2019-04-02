package com.wdy;

import cn.hutool.crypto.digest.DigestUtil;

import java.text.DecimalFormat;

/**
 * Created by wgch on 2019/3/14.
 */
public class WdyMain {

    public static void main(String[] args) {
        System.out.println(Fribonacci(9));
        WdyString();
        md5();

    }

    public static int Fribonacci(int n) {
        if (n <= 2) {
            return 1;
        } else {
            return Fribonacci(n - 1) + Fribonacci(n - 2);
        }
    }

    public static void WdyString() {
        String orgCode = "001.001.002.046.007";
        // 截取字符串后三位
        String sub = orgCode.substring(orgCode.length() - 3);
        System.out.println(sub);
        // 007加1 --->>> 008
        String newOrgCode = "001.001.002.046." + new DecimalFormat("000").format(Integer.parseInt(sub) + 1);
        System.out.println(newOrgCode);
        String other = newOrgCode.substring(0, newOrgCode.length() - 3);
        System.out.println(other);

        String s = "001.001.002.046." + new DecimalFormat("000").format(1);
        System.out.println("--->>> " + s);
        System.out.println("少时诵诗书所所".substring(0,1));
    }

    public static void md5() {
        String md5Hex = DigestUtil.md5Hex("123456");
        System.out.println(md5Hex);
    }
}
