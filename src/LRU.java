import java.util.List;
import java.util.Stack;

public class LRU {
    private Stack<Integer> stack = new Stack<>();
    private Double C = 0.0;; // 未命中次数
    private int Msize;
    private List<Integer> stream;

    public LRU(List<Integer> stream, int Msize) {
        this.Msize = Msize;
        this.stream = stream;
    }

    public void process() {
        for (int i = 0; i < stream.size(); i++) {
            int zhiLing = stream.get(i);
            int yeMian = zhiLing/10;
            if (stack.contains(yeMian)) {
                System.out.println("指令" + zhiLing + "已在内存,页面为：" + yeMian);
                stack.removeElement(yeMian); // 先移除再push进栈，提高其优先级
                stack.push(yeMian);
            } else {
                C++;
                if (stack.size() == Msize) {
                    System.out.println("页面" + stack.firstElement() + "调出");
                    stack.remove(0); // 调出第一个最久未使用的页面
                }
                stack.push(yeMian);
                System.out.println("页面" + yeMian + "调入");
            }
            System.out.println("当前栈：" + stack);
        }
    }

    public void calHitRate() {
        System.out.println("----------LRU-START-----------");
        this.process();
        C -= Msize;
        Double c = 1 - (double) (C / 320);
        System.out.println("LRU : " + String.format("%.6f", c));
    }

}