import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class OPT {
    private Set<Integer> set = new LinkedHashSet<>(); // 用于存储无序(存入和取出的顺序不一定相同)元素，值不能重复
    private Double C = 0.0;; // 未命中次数
    private int Msize;
    private List<Integer> stream;

    public OPT(List<Integer> stream, int Msize) {
        this.Msize = Msize;
        this.stream = stream;
    }

    public void process() {
        for (int i = 0; i < stream.size(); i++) {
            int zhiLing = stream.get(i);
            int yeMian = zhiLing / 10;
            if (set.contains(yeMian)) {
                System.out.println("指令" + zhiLing + "已在内存,对应页面：" + yeMian);
            } else {
                C++;
                if (set.size() == Msize) {
                    int max = -1; // 最长时间不会使用的指令的页面
                    int[] temp = new int[32]; // 空数组用于保存
                    for (int a : set) {
                        for (int j = i + 1; j < stream.size(); j++) {
                            if ((stream.get(j) / 10) == a) { // 找到与当前页面相等的页面
                                temp[a] = j; // 保存到数组对应位置
                                break;
                            }
                        }
                    }
                    for (int a : set) { // 找出数组中最晚出现的
                        if (max == -1)
                            max = a;
                        if (temp[a] == 0) { // 该页面后续不再出现（数组中未保存该页面的后续页面位置）
                            set.remove(a); // 直接调出
                            System.out.println("页面" + a + "调出");
                            break;
                        }
                        if (temp[a] > temp[max])
                            max = a; // max用于保存数组最靠后的位置
                    }
                    if (set.size() == Msize) {
                        set.remove(max);// 移除该页面
                        System.out.println("页面" + max + "调出");
                    }
                }
                set.add(yeMian);
                System.out.println("页面" + yeMian + "调入");
            }
            System.out.println("当前set:"+set);
        }
    }

    public void calHitRate() {
        System.out.println("----------OPT-START-----------");
        this.process();
        C -= Msize;
        Double c = 1 - (double) (C / 320);
        System.out.println("OPT : " + String.format("%.6f", c));
    }
}