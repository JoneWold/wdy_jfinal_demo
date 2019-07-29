package com.wdy.biz.wdy;

import cn.hutool.crypto.digest.DigestUtil;
import com.jfinal.plugin.activerecord.Record;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by wgch on 2019/3/14.
 */
public class WdyMain {

    public static void main(String[] args) {
        WdyMain wdy = new WdyMain();
        System.out.println(Fribonacci(5));
        wdy.wdyOrgCode();
        wdy.getNodeCode();
        wdy.getSQL();
        wdy.wdyStringBuffer();
        wdy.progressBar();
    }

    // 回调函数
    private static int Fribonacci(int n) {
        if (n <= 2) {
            return 1;
        } else {
            return Fribonacci(n - 1) + Fribonacci(n - 2);
        }
    }

    // 生成组织层级码
    private void wdyOrgCode() {
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
        System.out.println("少时诵诗书所所".substring(0, 1));
    }

    // 获取层级码
    private void getNodeCode() {
        for (int i = 1; i <= 10; i++) {
            String node = "000" + i;
            node = "003." + node.substring(node.length() - 3);
            System.out.println(node);
        }
    }


    // 分割字符串SQL语句
    private void getSQL() {
        String str = "select a01.\"A0000\",a01.\"A0101\",a01.\"A0192\" as \"A0215A\" ,(select string_agg(a02.\"A0215B\",'、') from \"a02\" where \"A0000\" = a01.\"A0000\" group by \"A0000\") as \"A0215B\",(select string_agg ( to_char ( a02.\"A0243\", 'YYYY.MM' ), '、' ) from \"a02\" where \"A0000\" =a01.\"A0000\" group by \"A0000\") AS \"A0243\",a01.\"A0192C\",a01.\"A0104\",a01.\"A0117\",to_char(a01.\"A0107\",'YYYY.MM') as \"A0107\",a01.\"A0111A\",a01.\"QRZZS\",a01.\"ZZZS\",a01.\"A0196\",to_char(a01.\"A0134\",'YYYY.MM') as \"A0134\",to_char(a01.\"A0144\",'YYYY.MM') as \"A0140\",string_agg(a02.\"mark\",'、') as \"mark\",concat(a01.\"XGR\",to_char(a01.\"XGSJ\",'YYYY.MM')) as \"XGRANDXGSJ\",a01.\"A0198\"\tfrom \"a01\" left join \"a02\" on a01.\"A0000\" = a02.\"A0000\" left join \"b01\" on a02.\"A0201B\" = b01.\"id\" left join \"a08\" on a08.\"A0000\" = a01.\"A0000\" where  b01.\"B0111\" like 'null%' group by a01.\"A0000\"";
        String sql = str.substring(0, str.lastIndexOf("from"));
        System.out.println(sql);
        System.out.println("================");
        String from = str.substring(str.lastIndexOf("from"));
        System.out.println(from);
    }

    // StringBuffer相等判断
    private void wdyStringBuffer() {
        StringBuffer b1 = new StringBuffer();
        StringBuffer b2 = new StringBuffer();
        b1.append("万州学院 军事学");
        b2.append("万州学院 军事学");
        System.out.println(b1.toString().equals(b2.toString()));

        Record record = new Record();
        record.set("name", "ss");
        record.set("id", null);
        if ("1.00".equals(record.getStr("id"))) {
            System.out.println("啦啦啦啦啦啦啦啦");
        }
    }

    /**
     * 数字百分比
     */
    private void progressBar() {
        // 保留小数点
        String format1 = new DecimalFormat("0.00").format((float) 1 / 12);
        System.out.println("format1: " + format1);
        String format2 = String.format("%.2f", (float) 7 / 12);
        System.out.println("format2: " + format2);
        System.out.println("format3: " + String.format("%.2f", (float) 12 / 12));

        // 创建一个数值格式化对象
        NumberFormat num = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        num.setMaximumFractionDigits(2);
        String ss = num.format((float) 1 / (float) 12 * 100);
        System.out.println("百分比：" + ss);
        System.out.println(String.format("百分比：%s", ss));

        // 进度百分比
        double d1 = (2645 / 23);
        double d2 = (float) (2645 / 23) / 2645 * 100;
        System.out.println(d1);
        System.out.println(d2);
    }


}
