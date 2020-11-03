package synchronizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 14.10.3 障栅
 * 参考链接：https://www.jianshu.com/p/0c8255ede7bc
 * Created by shucheng on 2020/11/3 22:11
 */
public class CyclicBarrierTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(CyclicBarrierTest.class);

    public static void main(String[] args) {
        int playerCount = 4;
        CyclicBarrier barrier = new CyclicBarrier(playerCount);
        for (int i = 0; i < playerCount; i++) {
            new PlayerThread(i, barrier).start();
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 下面说明CyclicBarrier是可以重复使用的
        LOGGER.debug("游戏结束...");
        LOGGER.debug("开始新一轮的游戏");
        for (int i = 0; i < playerCount; i++) {
            new PlayerThread(i, barrier).start();
        }
    }

    static class PlayerThread extends Thread {
        private int playerNo;
        private CyclicBarrier cyclicBarrier;

        public PlayerThread(int playerNo, CyclicBarrier cyclicBarrier) {
            this.playerNo = playerNo;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            LOGGER.info("玩家{}正在连接服务器...", playerNo);
            try {
                // Thread.sleep(1000);
                TimeUnit.SECONDS.sleep(playerNo);
                LOGGER.info("玩家{}准备完毕！", playerNo);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            LOGGER.info("所有玩家准备完毕，游戏正式开始！====玩家{}进入游戏", playerNo);
        }
    }
}
