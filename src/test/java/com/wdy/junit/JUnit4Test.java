package com.wdy.junit;

import org.junit.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by wgch on 2019/3/14.
 *
 * JUnit4使用Java5中的注解（annotation），以下是JUnit4常用的几个annotation：
 * @Before：初始化方法，对于每一个测试方法都要执行一次（注意与BeforeClass区别，后者是对于所有方法执行一次）
 * @After：释放资源，对于每一个测试方法都要执行一次（注意与AfterClass区别，后者是对于所有方法执行一次）
 * @Test：测试方法，在这里可以测试期望异常和超时时间
 * @Test(expected=ArithmeticException.class)检查被测方法是否抛出ArithmeticException异常
 * @Ignore：忽略的测试方法
 * @BeforeClass：针对所有测试，只执行一次，且必须为static void
 * @AfterClass：针对所有测试，只执行一次，且必须为static void
 * 一个JUnit4的单元测试用例执行顺序为：@BeforeClass -> @Before -> @Test -> @After -> @AfterClass;
 * 每一个测试方法的调用顺序为：@Before -> @Test -> @After;
 *
 */
public class JUnit4Test {
    @Before
    public void before() {
        System.out.println("@Before");
    }

    @Test
    /**
     *Mark your test cases with @Test annotations.
     *You don’t need to prefix your test cases with “test”.
     *tested class does not need to extend from “TestCase” class.
     */
    public void test() {
        System.out.println("@Test");
        assertEquals(5 + 5, 10);
        //断言：测试变量值等于指定值
        assertThat(100, is(100));
    }

    @Ignore
    @Test
    public void testIgnore() {
        System.out.println("@Ignore");
    }

    @Test(timeout = 50)
    public void testTimeout() {
        System.out.println("@Test(timeout = 50)");
        assertEquals(5 + 5, 10);
    }

    @Test(expected = ArithmeticException.class)
    public void testExpected() {
        System.out.println("@Test(expected = Exception.class)");
        throw new ArithmeticException();
    }

    @After
    public void after() {
        System.out.println("@After");
    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("@BeforeClass");
    }


    @AfterClass
    public static void afterClass() {
        System.out.println("@AfterClass");
    }

}
