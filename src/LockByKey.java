import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockByKey {
    private static class LockWrapper {
        private final Lock lock = new ReentrantLock();
        private final AtomicInteger numberOfThreadsInQueue = new AtomicInteger(1);

        private LockWrapper addThreadInQueue() {
            numberOfThreadsInQueue.incrementAndGet();
            return this;
        }

        private int removeThreadFromQueue() {
            return numberOfThreadsInQueue.decrementAndGet();
        }

    }

    private static ConcurrentHashMap<Integer, LockWrapper> locks = new ConcurrentHashMap<>();

    public void lock(Integer key) {
        LockWrapper lockWrapper = locks.compute(key, (k, v) -> v == null ? new LockWrapper() : v.addThreadInQueue());
        lockWrapper.lock.lock();
    }

    public void unlock(Integer key) {
        LockWrapper lockWrapper = locks.get(key);
        lockWrapper.lock.unlock();
        if (lockWrapper.removeThreadFromQueue() == 0) {
            locks.remove(key, lockWrapper);
        }
    }

}
