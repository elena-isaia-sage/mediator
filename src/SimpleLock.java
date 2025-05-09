import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleLock {
    private static Set<Integer> usedKeys= ConcurrentHashMap.newKeySet();

    public synchronized boolean tryLock(Integer key) {
        return usedKeys.add(key);
    }

    public synchronized void unlock(Integer key) {
        usedKeys.remove(key);
    }
}
