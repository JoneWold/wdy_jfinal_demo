package com.wdy.bizz.file;

import cn.hutool.core.io.FileUtil;
import com.jfinal.aop.Aop;
import com.wdy.biz.file.rmb.dao.ImportRmbDao;
import com.wdy.biz.file.rmb.service.ExportRmbService;
import com.wdy.biz.file.rmb.service.ReadRmbService;
import com.wdy.bizz.TestBeforeWdyConfig;
import com.wdy.dto.RmbOldMemInfoDto;
import com.wdy.generator.postgreSQL.model.A01Temp;
import com.wdy.generator.postgreSQL.model.A36Temp;
import com.wdy.generator.postgreSQL.model.A57Temp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wgch
 * @Description lrm lrmx
 * @date 2019/12/10
 */
public class TestReadRmb extends TestBeforeWdyConfig {
    private ReadRmbService rmbService = Aop.get(ReadRmbService.class);
    private ImportRmbDao importRmbDao = Aop.get(ImportRmbDao.class);
    private ExportRmbService exportRmbService = Aop.get(ExportRmbService.class);

    @Test
    public void testReadRmb() throws Exception {
        String lrm = "D:\\wdy\\wdy_jfinal_demo\\download\\rmb\\岑邦青.lrm";
        String pic = "D:\\wdy\\wdy_jfinal_demo\\download\\rmb\\岑邦青.pic";
        String lrmx = "D:\\wdy\\wdy_jfinal_demo\\download\\rmb\\陈卫星.lrmx";
        List<A01Temp> a01TempList = new ArrayList<>();
        List<A36Temp> a36TempList = new ArrayList<>();
        List<A57Temp> a57TempList = new ArrayList<>();

        rmbService.readTxtLrm(FileUtil.file(lrm), "123456", a01TempList, a36TempList);

        rmbService.readPic(FileUtil.file(pic), "123456", a57TempList);

        rmbService.readXmlLrmx(FileUtil.file(lrmx), "123456", a01TempList, a36TempList, a57TempList);

        System.out.println(a01TempList);
        System.out.println(a36TempList);
        System.out.println(a57TempList);
    }

    @Test
    public void testImpRmbDao() {
        Map<String, List<RmbOldMemInfoDto>> listMap = importRmbDao.findA01ByA0184();
        System.out.println(listMap);
    }

    /**
     * Unicode 汉字内码表 '淘' 28120
     */
    @Test
    public void testTheClock() {
        char c = "淘".charAt(0);
        System.out.println(c);
    }


    @Test
    public void testRegex() {
        boolean matches = "大专#".matches(".+#");
        System.out.println(matches);

        // 中文标点符号编码十进制 unicode码
        String a = "（";
        String b = "，";
        String c = "：";
        String d = "；";
        String e = "、";
        String f = "淘";
        System.out.println(a.codePointAt(0) + " --->>>>  （ ");
        System.out.println(b.codePointAt(0) + " --->>>>  ， ");
        System.out.println(c.codePointAt(0) + " --->>>>  ： ");
        System.out.println(d.codePointAt(0) + " --->>>>  ； ");
        System.out.println(e.codePointAt(0) + " --->>>>  、 ");
        System.out.println(f.codePointAt(0) + " --->>>>  淘 ");
        char charAt = "\n".charAt(0);
        System.out.println(charAt);
    }


    @Test
    public void setA1701Value() {
        String str = "1982.09--1985.07  四川省石柱师范学校中专学习\n" +
                "1985.07--1985.08  待分配\n" +
                "1985.08--1991.08  四川省石柱自治县下路中学教师（其间：\n" +
                "1988.08--1990.07四川省教育学院历史系历史专业大专学习）\n" +
                "1993.10--1996.12  四川省石柱自治县委办公室主办干事（1993.05--1995.12四川省委第二党校经济管理专业大学学习；\n" +
                "1995.12--1996.12挂职任大歇乡党委委员）\n" +
                "1998.01--2001.09  重庆市石柱自治县委办公室副主任，\n" +
                "2000.07正科级\n" +
                "2007.02--2012.02  重庆市石柱自治县委常委、办公室主任\n" +
                "（2004.09--2007.06重庆市委党校政治学理论专业研究生学习）\n" +
                "2016.11--         重庆市忠县县委常委、政法委书记";

        String a1701Value = exportRmbService.setA1701Value(str);
        System.out.println(a1701Value);
    }


}
