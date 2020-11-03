package checkedException;

/**
 * Created by shucheng on 2020/10/15 11:36
 */
public abstract class Block {

    public abstract void body() throws Exception;

    public Thread toThread() {
        return new Thread() {
            @Override
            public void run() {
                try {
                    body();
                } catch (Throwable t) {
                    // 在这里抛了一个异常（使用泛型把检查异常变成RuntimeException了）
                    Block.<RuntimeException>throwAs(t);
                }
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwAs(Throwable e) throws T {
        throw (T) e;
    }
}