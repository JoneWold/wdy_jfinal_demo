package com.wdy.bizz.hello;

import com.jfinal.plugin.activerecord.Record;
import com.wdy.bizz.TestBeforeWdyConfig;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wgch
 * @description java技术测试
 * @date 2019/6/6 11:02
 */
public class TestJavaSkill extends TestBeforeWdyConfig {


    /**
     * java8 list分组
     */
    @Test
    public void testGroupBy() {
        List<Record> testRecord = new ArrayList<>();
        Record record = new Record();
        record.set("A0000", "1");
        record.set("A0200", "12313213");
        record.set("A03333", "123123");
        Record record2 = new Record();
        record2.set("A0000", "1");
        record2.set("A0200", "1231312312213");
        record2.set("A03333", "123131231231323");
        Record record3 = new Record();
        record3.set("A0000", "2");
        record3.set("A0200", "12313213");
        record3.set("A03333", "123123");
        Record record4 = new Record();
        record4.set("A0000", "2");
        record4.set("A0200", "1231312312213");
        record4.set("A03333", "123131231231323");
        testRecord.add(record);
        testRecord.add(record2);
        testRecord.add(record3);
        testRecord.add(record4);

        Map<String, List<Record>> map = testRecord.stream().collect(Collectors.groupingBy(d -> {
            return d.getStr("A0000");
        }));
        // 遍历map集合
        for (Map.Entry<String, List<Record>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<Record> values = entry.getValue();
            System.out.println(key + ">>>" + values);
        }
        System.out.println(map);
    }

    @Test
    public void testForEach() {
        Arrays.asList("a", "b", "c").forEach(e -> System.out.println(e));
    }

}
