package com.wdy.bizz.file;

import cn.hutool.core.io.FileUtil;
import com.jfinal.aop.Aop;
import com.wdy.biz.file.rmb.ReadRmbService;
import com.wdy.bizz.TestBeforeWdyConfig;
import com.wdy.generator.postgreSQL.model.A01Temp;
import com.wdy.generator.postgreSQL.model.A36Temp;
import com.wdy.generator.postgreSQL.model.A57Temp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wgch
 * @Description lrm lrmx
 * @date 2019/12/10
 */
public class TestReadRmb extends TestBeforeWdyConfig {
    private ReadRmbService rmbService = Aop.get(ReadRmbService.class);

    @Test
    public void testRmb() throws Exception {
        String lrm = "D:\\wdy\\wdy_jfinal_demo\\download\\rmb\\岑邦青.lrm";
        String pic = "D:\\wdy\\wdy_jfinal_demo\\download\\rmb\\岑邦青.pic";
        String lrmx = "D:\\wdy\\wdy_jfinal_demo\\download\\rmb\\陈卫星.lrmx";
        List<A01Temp> a01TempList = new ArrayList<>();
        List<A36Temp> a36TempList = new ArrayList<>();
        List<A57Temp> a57TempList = new ArrayList<>();

        rmbService.readTxtLrm(FileUtil.file(lrm), "123456", a01TempList, a36TempList);

        rmbService.readPic(FileUtil.file(pic), "123456", a57TempList);

        rmbService.readXmlLrmx(FileUtil.file(lrmx), "123", a01TempList, a36TempList, a57TempList);

        System.out.println(a01TempList);
        System.out.println(a36TempList);
        System.out.println(a57TempList);
    }


    @Test
    public void testRegex() {
        boolean matches = "大专#".matches(".+#");
        System.out.println(matches);

    }

}
