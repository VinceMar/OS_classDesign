import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CLOCK {
    /**
     * Clock：时钟替换算法（Clock）,给每个页帧关联一个使用位。 当该页第一次装入内存或者被重新访问到时，将使用位置为1。
     * 每次需要替换时，查找使用位被置为0的第一个帧进行替换。 在扫描过程中，如果碰到使用位为1的帧，将使用位置为0，在继续扫描。
     * 如果所谓帧的使用位都为0，则替换第一个帧。 总结：
     * 
     * 1.内存中不存在页面n，而内存中有空闲位置时，直接加入页面n(1)，p加1
     * 
     * 2.内存中不存在页面n，且内存中没有空闲位置时，发生替换n(1), p加1
     * 
     * 3.内存中存在页面n，,p不变，将页面n重置为n(1)（不管页面n之前使用位为1或0）
     */
    private Set<Integer> set = new LinkedHashSet<>();
    private Double C = 0.0;
    private int Msize;
    private List<Integer> stream;
    private CK[] c;
    private ArrayList<CK> array;    //ArrayList的CK对象数组
    private boolean exist;   //指令是否在内存中的标志

    public CLOCK(List<Integer> stream, int Msize) {
        this.Msize = Msize;
        this.stream = stream;
        c = new CK[stream.size()];
        array = new ArrayList<CK>();
    }

    public void process() {
        for (int i = 0; i < stream.size(); i++) {
            exist = true;
            int zhiLing = stream.get(i);
            int yeMian = zhiLing / 10;
            c[i] = new CK(yeMian,0);
            for (int j = 0; j < array.size(); j++) {
                if (array.get(j).ym == yeMian) {
                    System.out.println("指令" + zhiLing + "已在内存,对应页面：" + yeMian);
                    exist = false;  //命中，改变状态(跳过未命中的代码)
                }
            }
            if(exist){  //未命中
                C++;
                if(array.size()==Msize){
                    //执行调出操作
                    for (int j = 0; j < array.size(); j++) {
                        array.get(j).state = 1;
                    }
                    System.out.println("页面"+array.get(0).ym+"调出");
                    array.remove(0);
                }
                array.add(c[i]);//否则未满Msize的个数，执行添加
                System.out.println("页面"+c[i].ym+"调入");
            }
            
        }
    }

    public void test(){
        process();
        for (int i = 0; i < array.size(); i++) {
            System.out.print(array.get(i).ym+"\t");
            System.out.print(array.get(i).state);
            System.out.println();
        }
    }

    public void calHitRate() {
        System.out.println("----------CLOCK-START-----------");
        this.process();
        C -= Msize;
        Double c = 1 - (double) (C / 320);
        System.out.println("CLOCK : " + String.format("%.6f", c));
    }
}

class CK {
    public int ym;
    public int state;

    public CK(int ym,int state) {
        this.state = state;
        this.ym = ym;
    }
}