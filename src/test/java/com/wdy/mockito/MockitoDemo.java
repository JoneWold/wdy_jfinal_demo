package com.wdy.mockito;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by wgch on 2019/3/20.
 */
public class MockitoDemo {
    @Test
    public void mockitoMapDemo1() {
        Map mockedMap = Mockito.mock(Map.class);
        when(mockedMap.get("city")).thenReturn("广州");
        Object cityValue = mockedMap.get("city");
        assertThat(cityValue.toString(), is("广州"));
        verify(mockedMap).get(eq("city"));
        verify(mockedMap, times(2));
    }

    @Test
    public void mockitoMapDemo2() {
        Map mockedMap = Mockito.mock(Map.class);
//        when(mockedMap.put(anyInt(), anyString())).thenReturn("world");
        mockedMap.put(1, "hello");
        verify(mockedMap).put(anyInt(), eq("hello"));
    }

    @Test
    public void mockitoListDemo() {
        List mockedList = Mockito.mock(List.class);
        mockedList.add("one");
        mockedList.add("two");
        verify(mockedList).add("one");
        verify(mockedList, times(2)).add(anyString());
    }
}
