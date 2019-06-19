import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Integer> stream = new ArrayList<>();

    public static void init() {
        stream.add(20);
        stream.add(10);
        stream.add(21);
        stream.add(11);
        stream.add(22);
        stream.add(30);
        stream.add(40);
    }

    public static void main(String[] args) {
        init();
        LRU lru = new LRU(stream, 3);
        LFU lfu = new LFU(stream, 3);
        // lru.calHitRate();
        lfu.calHitRate();
    }
}