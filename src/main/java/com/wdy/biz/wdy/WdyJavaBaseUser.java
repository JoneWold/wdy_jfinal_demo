package com.wdy.biz.wdy;

import cn.hutool.core.util.StrUtil;

import java.util.Scanner;

/**
 * @author wgch
 * @Description java 基础测试
 * @date 2019/12/6
 */
public class WdyJavaBaseUser {
    private String name;
    private String password;

    private WdyJavaBaseUser[] users = new WdyJavaBaseUser[2];
    private int count = 0;

    private Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆");
        System.out.println("****************** 欢迎来到无限注册系统 ******************");
        System.out.println("☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆ ☆☆☆");
        Scanner sc = new Scanner(System.in);
        WdyJavaBaseUser user = new WdyJavaBaseUser();
        while (true) {
            System.out.println("-------------------------------------------------------");
            System.out.println("请选择功能(不分大小写)：A(注册)	B(查看用户)	 C(退出系统)");
            System.out.println("-------------------------------------------------------");
            String st = sc.next();
            if (StrUtil.equalsAny(st, "a", "A")) {
                user.addUser();
            } else if (StrUtil.equalsAny(st, "b", "B")) {
                user.searchUser();
            } else if (StrUtil.equalsAny(st, "c", "C")) {
                System.out.println("哥们,有本事别走刷爆我服务器啊....");
                System.out.println("继续注册请按1,..执意离开请按2");
                st = sc.next();
                if ("1".equals(st)) {
                    user.addUser();
                } else if ("2".equals(st)) {
                    System.exit(0);
                } else {
                    System.out.println("乱按是木有用的...");
                }

            }
        }
    }

    private void addUser() {
        if (count < users.length) {
            WdyJavaBaseUser baseUser = new WdyJavaBaseUser();
            System.out.println("请输入用户名：");
            baseUser.name = scanner.next();
            System.out.println("请输入密码：");
            baseUser.password = scanner.next();
            users[count] = baseUser;
            count++;
        } else {
            this.change();
        }
    }

    public void change() {
        if (users.length <= 2) {
            System.out.println("这软件貌似要火了...");
        } else if (3 < users.length && users.length < 5) {
            System.out.println("用户猛增啊...要发了");
        } else if (5 < users.length && users.length < 10) {
            System.out.println("服务器要爆了...");
        }

        WdyJavaBaseUser[] newUsers = new WdyJavaBaseUser[users.length * 2];
        System.arraycopy(users, 0, newUsers, 0, users.length);
        users = newUsers;
        addUser();

    }

    private void searchUser() {
        System.out.println("当前用户为：");
        for (WdyJavaBaseUser user : users) {
            if (user == null) {
                break;
            }
            System.out.println("用户名：" + user.name + "\t密码：" + user.password);
        }
    }

}
