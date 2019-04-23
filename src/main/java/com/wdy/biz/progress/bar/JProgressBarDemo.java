package com.wdy.biz.progress.bar;

import javax.swing.*;
import java.awt.*;

/**
 * @author wgch
 * @Description Java进度条
 * @date 2019/4/23 15:47
 */
public class JProgressBarDemo extends JFrame implements Runnable {

    private JProgressBar progress; // 进度条

    public JProgressBarDemo(String str) {
        super(str);
        progress = new JProgressBar(1, 100); // 实例化进度条

        progress.setStringPainted(true);      // 描绘文字


        progress.setBackground(Color.PINK); // 设置背景色

        this.add(progress);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(200, 300, 250, 50);
        this.setVisible(true);
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 100; i++) {
                try {
                    progress.setValue(progress.getValue() + 1); // 随着线程进行，增加进度条值

                    progress.setString(progress.getValue() + "%");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            progress.setValue(0);
            progress.setString(0 + "%");
        }
    }

    public static void main(String[] args) {
        JProgressBarDemo pb = new JProgressBarDemo("Test JProcessBar");
        Thread t = new Thread(pb);
        t.start();
    }


}
