package com.wdy.bizz.js;

import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.util.Date;

/**
 * @author wgch
 * @Description
 * @date 2019/10/22 9:06
 */
public class TestJS {

    @Test
    public void testNashorn() throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        FileReader reader = new FileReader("D:\\wdy\\wdy_jfinal_demo\\src\\test\\java\\com\\wdy\\bizz\\js\\testNashorn.js");
        engine.eval(reader);
//        Bindings bindings = engine.createBindings();
//        bindings.put("name", "Nashorn");
//        engine.eval("print('Hello ' + name);");
        Invocable invocable = (Invocable) engine;
        Object o = invocable.invokeFunction("fun1", "啊啊啊啊啊啊啊");
        System.out.println(o);
        Object fun2 = invocable.invokeFunction("fun2", new Date());
        System.out.println(fun2);

        Object fun3 = invocable.invokeFunction("fun3");
        Object fun4 = invocable.invokeFunction("fun4List");
        invocable.invokeFunction("fun5try");
        invocable.invokeFunction("fun6stream");
        invocable.invokeFunction("fun7Thread");
        invocable.invokeFunction("fun8Method");
        invocable.invokeFunction("fun9Bind");
        invocable.invokeFunction("fun10");
    }


    public static String say(String name) {
        System.out.println("--->>>" + name);
        return "结束";
    }

}
