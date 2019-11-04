package com.wdy.biz.wdy;

/**
 * @author wgch
 * @Description 泛型
 * @date 2019/6/26
 */
public class FanX<T> {
    private int code;
    private T data;

    private FanX(T data, int code) {
        this.data = data;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FanX{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }

    public static void main(String[] args) {
        FanX<String> fanX = new FanX<>("字符串类型", 1);
        System.out.println(fanX);

        FanX<Integer> integerFanX = new FanX<>(123, 2);
        System.out.println(integerFanX);

        FanX<Boolean> booleanFanX = new FanX<>(false, 3);
        System.out.println(booleanFanX);

        Class<? extends String> aClass = fanX.getData().getClass();
        System.out.println(aClass);
        Class<? extends Integer> aClass1 = integerFanX.getData().getClass();
        System.out.println(aClass1);
    }


}
