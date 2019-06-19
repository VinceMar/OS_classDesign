import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FIFO {
    private Queue<Integer> que = new LinkedList<>();
    private Double C = 0.0;
    private int Msize;
    private List<Integer> stream;

    public FIFO(List<Integer> stream, int Msize) {
        this.Msize = Msize;
        this.stream = stream;
    }

    public void process(){
        for (int i = 0; i < stream.size(); i++) {
            int zhiLing = stream.get(i);
            int yeMian = zhiLing/10;
            if (que.contains(yeMian)) {
                System.out.println("指令" + zhiLing + "已在内存，对应页面：" + yeMian);
            } else {
                C++;
                if (que.size() == Msize) {
                    int pop = que.poll(); // 将最先进入的弹出
                    System.out.println("页面" + pop + "弹出");
                }
                que.offer(yeMian);
                System.out.println("页面" + yeMian + "调入");
            }
            System.out.println("当前队列："+que);
        }
    }

    public void calHitRate(){
        System.out.println("----------FIFO-START-----------");
        this.process();
        C -= Msize;
        Double c = 1 - (double) (C / 320);
        System.out.println("FIFO : " + String.format("%.6f", c));
    }
}