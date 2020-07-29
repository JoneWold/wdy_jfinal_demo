package com.wdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wgch
 * @Description 银行账户
 * @date 2020/7/29
 */
@Data
@AllArgsConstructor
public class BankAccount {
    private String accountName;
    private double balance;

    /**
     * 存款
     */
    public synchronized double deposit(double amount) {
        balance = balance + amount;
        return balance;
    }

    /**
     * 取款
     */
    public synchronized double withdraw(double amount) {
        balance = balance - amount;
        return balance;
    }
}
