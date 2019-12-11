package com.wdy.bizz.file;

import com.jfinal.aop.Aop;
import com.wdy.biz.file.controller.ReadRmbService;
import com.wdy.bizz.TestBeforeWdyConfig;
import org.junit.Test;

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
        String lrmx = "D:\\wdy\\wdy_jfinal_demo\\download\\rmb\\陈卫星.lrmx";
        rmbService.readTxtLrm(lrm);


        List<Object> xmlLrmx = rmbService.readXmlLrmx(lrmx, "123");
        System.out.println(xmlLrmx);
    }


}
