package com.wdy.mockito;


import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by wgch on 2019/3/8.
 */
public class UnitTest {

    public void simpleTest() {
        List mockedList = mock(List.class);
        mockedList.add("one");
        mockedList.clear();
        //verification 验证
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    public static void main(String[] args) {

        // 你可以mock具体的类型,不仅只是接口
        LinkedList mockedList = mock(LinkedList.class);

        // 测试桩
        when(mockedList.get(0)).thenReturn("element");
        when(mockedList.get(1)).thenThrow(new RuntimeException());
        // 使用内置的anyInt()参数匹配器
        when(mockedList.get(anyInt())).thenReturn("element");
        // 使用自定义的参数匹配器( 在isValid()函数中返回你自己的匹配器实现 )
//        when(mockedList.contains(argThat(isValid()))).thenReturn("element");

        // 输出“first”
        System.out.println(mockedList.get(0));

        // 抛出异常
        System.out.println(mockedList.get(1));

        // 因为get(999) 没有打桩，因此输出null
        System.out.println(mockedList.get(999));

        // 验证get(0)被调用的次数
        verify(mockedList).get(0);
    }


}
