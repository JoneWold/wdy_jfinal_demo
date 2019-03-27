package com.wdy.common.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hdy on 2018/4/17.
 */
public class JUUID {
    private static NumberFormat nf = NumberFormat.getInstance();
    private static AtomicInteger ai = new AtomicInteger();

    // 32位
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replaceAll("-", "");

        return uuidStr;
    }

    public static String getShortUUID() {
        //设置最大整数位数
        nf.setMaximumIntegerDigits(3);
        //设置最小整数位数
        nf.setMinimumIntegerDigits(3);
        if (ai.intValue() >= 999) {
            ai.set(0);
        }
        return System.currentTimeMillis() + nf.format(ai.incrementAndGet());
    }

    // 16位
    public static long generateUUID() {
        if (ai.intValue() >= 999) {
            ai.set(0);
        }
        return System.currentTimeMillis() * 1000 + ai.incrementAndGet();
    }

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();   // 排序前取得当前时间

        for (int i = 0; i < 1000000; i++) {
            generateUUID();
        }
        long t2 = System.currentTimeMillis();   // 排序后取得当前时间

        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(t2 - t1);

        System.out.println("generateUUID-耗时: " + c1.get(Calendar.MINUTE) + "分 "
                + c1.get(Calendar.SECOND) + "秒 " + c1.get(Calendar.MILLISECOND) + " 毫秒");
    }


    private static int sequence = 0;

    private static int length = 6;

    /**
     * YYYYMMDDHHMMSS+6位自增长码(20位)
     *
     * @return
     * @author shijing
     * 2015年6月29日下午1:25:23
     */
    public static synchronized String getLocalTrmSeqNum() {
        sequence = sequence >= 999999 ? 1 : sequence + 1;
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String s = Integer.toString(sequence);
        return datetime + addLeftZero(s, length);
    }

    /**
     * 左填0
     *
     * @param s
     * @param length
     * @return
     * @author shijing
     * 2015年6月29日下午1:24:32
     */
    public static String addLeftZero(String s, int length) {
        // StringBuilder sb=new StringBuilder();
        int old = s.length();
        if (length > old) {
            char[] c = new char[length];
            char[] x = s.toCharArray();
            if (x.length > length) {
                throw new IllegalArgumentException(
                        "Numeric value is larger than intended length: " + s
                                + " LEN " + length);
            }
            int lim = c.length - x.length;
            for (int i = 0; i < lim; i++) {
                c[i] = '0';
            }
            System.arraycopy(x, 0, c, lim, x.length);
            return new String(c);
        }
        return s.substring(0, length);

    }
}
