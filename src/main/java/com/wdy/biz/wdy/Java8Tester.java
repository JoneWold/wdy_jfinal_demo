package com.wdy.biz.wdy;

/**
 * @author wgch
 * @Description
 * @date 2019/6/6 16:59
 */
public class Java8Tester {
    public static void main(String args[]) {
        Java8Tester j8 = new Java8Tester();
        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;
        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;
        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };
        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;
        System.out.println("10 + 5 = " + j8.operate(10, 5, addition));
        System.out.println("10 - 5 = " + j8.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + j8.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + j8.operate(10, 5, division));
        // 不用括号
        GreetingService gs1 = message -> System.out.println("Hello " + message);
        gs1.sayMessage("Runoob");
        // 用括号
        GreetingService gs2 = (message) ->
                System.out.println("Hello " + message);
        gs2.sayMessage("Google");

    }


    /**
     * 定义函数式接口：
     * 该接口只能有一个抽象函数。当该接口中抽象函数个数不是1时就会报错提示。
     * 不过，默认方法和静态方法 不会破坏函数式接口的定义
     */
    @FunctionalInterface
    interface MathOperation {
        int operation(int a, int b);

        static void p() {
            System.out.println("静态方法");
        }
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }


}
