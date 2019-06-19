import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Integer> stream, stream2;

    /**
     * 初始化指令集
     */
    public static void init() {
        stream = new ArrayList<>(); // 测试用，便于看效果
        stream2 = new ArrayList<>(); // 题目要求
        stream.add(20);
        stream.add(10);
        stream.add(21);
        stream.add(11);
        stream.add(22);
        stream.add(30);
        stream.add(40);
        while (stream2.size() != 320) {// 重复① ~ ⑤，直到执行320次指令。
            int m = (int) ((Math.random() * 319));// 起点 :在[0, 319]的指令地址之间随机选区一起点M；
            stream2.add(m + 1);// 顺序执行一条指令，即执行地址为M+1的指令；
            int m2 = (int) ((Math.random() * (m + 1))); // 在前地址[0, M+1]中随机选取一条指令并执行，该指令的地址为M'；
            stream2.add(m2);// 执行m2
            stream2.add(m2 + 1);// 顺序执行一条指令，其地址为M'+1；
            int m3 = (m2 + 2) + (int) (+Math.random() * (319 - m2 - 2));// 在后地址[M'+2, 319]中随机选取一条指令并执行；
            stream2.add(m3);// 执行[m2+2,319]
        }
    }

    /**
     * 打印指令集和对应页面
     */
    public static void show(List<Integer> stream) {
        System.out.print("指令\t");
        System.out.println();
        for (int i = 0; i < stream.size(); i++) {
            int zhiLing = stream.get(i);
            System.out.print(zhiLing + "\t");
            if ((i + 1) % 10 == 0 && i != 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.print("页面\t");
        System.out.println();
        for (int i = 0; i < stream.size(); i++) {
            int zhiLing = stream.get(i);
            int yeMian = zhiLing / 10;
            System.out.print(yeMian + "\t");
            if ((i + 1) % 10 == 0 && i != 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        init();
        List<Integer> s = stream;
        // show(s);
        LRU lru = new LRU(s, 3);
        LFU lfu = new LFU(s, 3);
        FIFO fifo = new FIFO(s, 3);
        OPT opt = new OPT(s, 3);
        CLOCK clock = new CLOCK(s, 3);
        // lru.calHitRate();
        // lfu.calHitRate();
        // fifo.calHitRate();
        // opt.calHitRate();
        clock.process();
    }
}