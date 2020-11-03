package synch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A bank with a number of bank accounts that uses locks for serializing access.
 * Created by shucheng on 2020/10/29 23:03
 */
public class MyBank {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;

    /**
     * Constructs the bank
     * @param n the number of accounts
     * @param initialBalance the initial balance for each account
     */
    public MyBank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    // transfer方法里每次只会进入一个线程，所以这里List和Map没有使用Collections.synchronized方法来做同步
    // 余额不足时被阻塞的线程
    private List<Thread> list = new ArrayList<>();
    // 余额不足的账户与转账操作信息的对应关系
    private Map<Integer, TransferOperation> blockFromAmountMap = new HashMap<>();

    static class TransferOperation {
        private Thread thread;
        private int to;
        private double amount;

        public TransferOperation(Thread thread, int to, double amount) {
            this.thread = thread;
            this.to = to;
            this.amount = amount;
        }
    }

    /**
     * Transfer money from one account to another.
     * @param from the account to transfer from
     * @param to the account to transfer to
     * @param amount the amount to transfer
     */
    public void transfer(int from, int to, double amount) throws InterruptedException {
        bankLock.lock();
        try {
            // if (accounts[from] < amount) return;
            while (accounts[from] < amount) {
                logger.info("账户【{}】余额（{}）不足，当前线程被阻塞 to->{},amount->{}",
                        from, String.format("%.2f", accounts[from]), to, String.format("%.2f", amount));
                list.add(Thread.currentThread());
                blockFromAmountMap.put(from, new TransferOperation(Thread.currentThread(), to, amount));
                sufficientFunds.await();
            }
            if (list.remove(Thread.currentThread())) {
                logger.info("线程{}被唤醒，账户【{}】余额充足", Thread.currentThread(), from);
                blockFromAmountMap.remove(from);
            }
            accounts[from] -= amount;
            accounts[to] += amount;

            logger.info(String.format("%10.2f from %d to %d Total Balance: %10.2f%n", amount, from, to, getTotalBalance()));
            TransferOperation transferOperation = blockFromAmountMap.get(to);
            if (transferOperation != null) {
                logger.info("账户【{}】正在向账户【{}】（阻塞线程 {}）补充资金", from, to, transferOperation.thread);
                if (accounts[to] >= transferOperation.amount) {
                    logger.info("账户【{}】资金已到位，线程{}即将被唤醒", to, transferOperation.thread);
                }
            }
            sufficientFunds.signalAll();
        } finally {
            bankLock.unlock();
        }
    }

    /**
     * 下面其实可以不用加锁，因为线程中没有直接去调用getTotalBalance方法，
     * 都是通过transfer方法间接调用getTotalBalance方法的，而transfer方法已经加锁了
     */
    /**
     * Gets the sum of all account balances.
     * @return
     */
    public double getTotalBalance() {
        bankLock.lock();
        try {
            double sum = 0;
            for (double a : accounts) {
                sum += a;
            }
            return sum;
        } finally {
            bankLock.unlock();
        }
        /*double sum = 0;
        for (double a : accounts) {
            sum += a;
        }
        return sum;*/
    }

    /**
     * Gets the number of accounts in the bank.
     * @return the number of accounts
     */
    public int size() {
        return accounts.length;
    }
}
