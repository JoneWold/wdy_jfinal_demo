package com.wdy.utils;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

/**
 * @author wgch
 * @Description 获取出生日期性别等
 * @date 2019/10/12
 */
public class IdCards {

    public static void main(String[] args) {
        AgeSexBirthday birAgeSex = getBirAgeSex("513525196702131991");
        System.out.println(birAgeSex.getAge());
        System.out.println(birAgeSex.getSexName());
        System.out.println(birAgeSex.getBirthday());
    }

    /***
     * 通过身份证号码获取出生日期、性别、年龄
     * @param idCards
     * @return 返回的出生日期格式：1990-01-01   性别格式：F-女，M-男
     */
    public static AgeSexBirthday getBirAgeSex(String idCards) {
        String age = "";
        String sexCode = "";
        String birthday = "";

        int year = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = idCards.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) return new AgeSexBirthday();
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) return new AgeSexBirthday();
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && idCards.length() == 15) {
            birthday = "19" + idCards.substring(6, 8) + "-"
                    + idCards.substring(8, 10) + "-"
                    + idCards.substring(10, 12);
            sexCode = Integer.parseInt(idCards.substring(idCards.length() - 3)) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt("19" + idCards.substring(6, 8))) + "";
        } else if (flag && idCards.length() == 18) {
            birthday = idCards.substring(6, 10) + "-"
                    + idCards.substring(10, 12) + "-"
                    + idCards.substring(12, 14);
            sexCode = Integer.parseInt(idCards.substring(idCards.length() - 4, idCards.length() - 1)) % 2 == 0 ? "F" : "M";
            try {
                age = String.valueOf(DateUtil.betweenYear(DateUtil.parse(birthday), new Date(), false));
            } catch (Exception e) {
                age = (year - Integer.parseInt(idCards.substring(6, 10))) + "";
            }
        }
        AgeSexBirthday asb = new AgeSexBirthday();
        asb.setAge(age);
        asb.setSexName(sexCode);
        asb.setBirthday(birthday);
        return asb;
    }


    @Data
    private static class AgeSexBirthday {
        private String age;
        private String sexName;
        private String birthday;
    }
}
