import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LFU {
    private Set<Integer> set = new LinkedHashSet<>();
    private Double C = 0.0;
    private int Msize;
    private List<Integer> stream;

    public LFU(List<Integer> stream, int Msize) {
        this.Msize = Msize;
        this.stream = stream;
    }

    public void process(){
        for (int i = 0; i < stream.size(); i++) {
            int zhiLing = stream.get(i);
            int yeMian = zhiLing/10;
            int[] temp = new int[32];
            if (set.contains(yeMian)) {
                System.out.println("指令" + zhiLing + "已在内存,对应页面：" + yeMian);
            } else {
                C++;
                if (set.size() == Msize) {
                    int Min = -1;
                    for (int a : set) {
                        for (int j = 0; j < i; j++) { // 遍历yeMian前的全部页面，若与当前set集合中的a相等，则对应temp[a]++(统计出现次数)
                            int zhiLing1 = stream.get(j);
                            int yeMian1 = zhiLing1/10;
                            if (yeMian1 == a) {
                                temp[a]++;
                            }
                        }
                    }
                    for (int a : set) {
                        if (Min == -1) {
                            Min = a;
                            continue; // 跳出此次循环，直接执行下次循环
                        }
                        if (temp[a] < temp[Min]) { // 找出出现频率最低的页面
                            Min = a;
                        }
                    }
                    set.remove(Min);// 移除该页面（出现频率最低）
                    System.out.println("页面" + Min + "调出");
                }
                set.add(yeMian);
                System.out.println("页面" + yeMian + "调入");
            }
            System.out.println("当前set:" + set);
        }
    }

    public void calHitRate(){
        System.out.println("----------LFU-START-----------");
        this.process();
        C -= Msize;
        Double c = 1 - (double) (C / 320);
        System.out.println("LFU : " + String.format("%.6f", c));
    }
}