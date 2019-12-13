package com.wdy.constant;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.File;

/**
 * @author TMW
 * @version 1.0
 * @Description: 通用常量类
 * @date 2019/3/20 17:54
 */
public class CommonConstant {
    /***
     * DSL 上下文
     * */
    public static final DSLContext DSL_CONTEXT = DSL.using(SQLDialect.POSTGRES);
    /**
     * /
     */
    public static final String SEPARATOR = File.separator;
    /**
     * 零
     */
    public static final String ZERO = "0";
    /**
     * 一
     */
    public static final String ONE = "1";
    /**
     * 二
     */
    public static final String TWO = "2";
    /**
     * 三
     */
    public static final String THREE = "3";
    public static final String FOUR = "4";
    /**
     * 七
     */
    public static final String SEVEN = "7";
    /**
     * -1
     */
    public static final String FONE = "-1";
    /**
     * 0
     */
    public static final int ZERO_INT = 0;
    /**
     * 1
     */
    public static final int ONE_INT = 1;
    /**
     * 2
     */
    public static final int TWO_INT = 2;
    /**
     * 3
     */
    public static final int THREE_INT = 3;
    /**
     * 01
     */
    public static final String ZERO_ONE = "01";
    /**
     * 02
     */
    public static final String ZERO_TWO = "02";
    /**
     * 001
     */
    public static final String ZERO_ZERO_ONE = "001";
    /**
     * 002
     */
    public static final String ZERO_ZERO_TWO = "002";
    /**
     * 003
     */
    public static final String ZERO_ZERO_THREE = "003";
    /**
     * get方法
     */
    public static final String GET_STR = "get";
    /**
     * find方法
     */
    public static final String FIND_STR = "find";
    /**
     * save方法
     */
    public static final String SAVE_STR = "save";
    /**
     * add方法
     */
    public static final String ADD_STR = "add";
    /**
     * update方法
     */
    public static final String UPDATE_STR = "update";
    /**
     * del方法
     */
    public static final String DEL_STR = "del";
    /**
     * today
     */
    public static final String TODAY = "today";
    /**
     * ;
     */
    public static final String MH = "；";
    /**
     * .
     */
    public static final String POINT = ".";

    public static final String POINT_LRM = ".lrm";
    public static final String POINT_PIC = ".pic";
    public static final String POINT_LRMX = ".lrmx";
    public static final String POINT_ZIP = ".zip";

    /**
     * π
     */
    public static final String PAI = "3.14159 26535 　89793 23846 　26433 83279 　50288 41971 　69399 37510 　58209 74944 　59230 78164 \n" +
            "　06286 20899 　86280 34825 　34211 70679 　82148 08651 　32823 06647 　09384 46095 　50582 23172 \n" +
            "　53594 08128 　48111 74502 　84102 70193 　85211 05559 　64462 29489 　54930 38196 　44288 10975 \n" +
            "　66593 34461 　28475 64823 　37867 83165 　27120 19091 　45648 56692 　34603 48610 　45432 66482 \n" +
            "　13393 60726 　02491 41273 　72458 70066 　06315 58817 　48815 20920 　96282 92540 　91715 36436 \n" +
            "　78925 90360 　01133 05305 　48820 46652 　13841 46951 　94151 16094 　33057 27036 　57595 91953 \n" +
            "　09218 61173 　81932 61179 　31051 18548 　07446 23799 　62749 56735 　18857 52724 　89122 79381 \n" +
            "　83011 94912 　98336 73362 　44065 66430 　86021 39494 　63952 24737 　19070 21798 　60943 70277 \n" +
            "　05392 17176 　29317 67523 　84674 81846 　76694 05132 　00056 81271 　45263 56082 　77857 71342 \n" +
            "　75778 96091 　73637 17872 　14684 40901 　22495 34301 　46549 58537 　10507 92279 　68925 89235 \n" +
            "　42019 95611 　21290 21960 　86403 44181 　59813 62977 　47713 09960 　51870 72113 　49999 99837 \n" +
            "　29780 49951 　05973 17328 　16096 31859 　50244 59455 　34690 83026 　42522 30825 　33446 85035 \n" +
            "　26193 11881 　71010 00313 　78387 52886 　58753 32083 　81420 61717 　76691 47303 　59825 34904 \n" +
            "　28755 46873 　11595 62863 　88235 37875 　93751 95778 　18577 80532 　17122 68066 　13001 92787 \n" +
            "　66111 95909 　21642 01989 ";


}
