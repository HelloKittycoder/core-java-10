package synch2;

import java.util.Arrays;

/**
 * A bank with a number of bank accounts that uses synchronization primitives.
 * Created by shucheng on 2020/10/29 23:03
 */
public class Bank {

    private final double[] accounts;

    /**
     * Constructs the bank
     * @param n the number of accounts
     * @param initialBalance the initial balance for each account
     */
    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    /**
     * Transfer money from one account to another.
     * @param from the account to transfer from
     * @param to the account to transfer to
     * @param amount the amount to transfer
     */
    public synchronized void transfer(int from, int to, double amount) throws InterruptedException {
        while (accounts[from] < amount)
            wait();
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
        notifyAll();
    }

    /**
     * 下面其实可以不用加锁，因为线程中没有直接去调用getTotalBalance方法，
     * 都是通过transfer方法间接调用getTotalBalance方法的，而transfer方法已经加锁了
     */
    /**
     * Gets the sum of all account balances.
     * @return
     */
    public synchronized double getTotalBalance() {
        double sum = 0;
        for (double a : accounts) {
            sum += a;
        }
        return sum;
    }

    /**
     * Gets the number of accounts in the bank.
     * @return the number of accounts
     */
    public int size() {
        return accounts.length;
    }
}
