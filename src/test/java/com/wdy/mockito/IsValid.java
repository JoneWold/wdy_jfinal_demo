package com.wdy.mockito;

import org.mockito.ArgumentMatcher;

import java.util.List;

/**
 * Created by wgch on 2019/3/8.
 */
public class IsValid implements ArgumentMatcher<List> {
//    @Override
//    public boolean matches(Object o) {
//        return o == 1 || o == 2;
//    }

    @Override
    public boolean matches(List list) {
        return false;
    }
}