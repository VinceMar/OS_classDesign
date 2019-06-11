import java.util.*;


public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("产生指令序列");
        produce_addstream();	//初始化指令集
        int k = 0;	//显示换行控制变量
        for(int a:stream) {
            System.out.print((k+1)+":"+a+"\t");
            k++;
            if(k%10 == 0)
            	System.out.println();
        }
        
        System.out.println("\r\n请选择页面置换算法 \r\n1、 最佳置换页面置换算法（OPT）\r\n" + 
                "2、 最近最久未使用页面置换算法（LRU）\r\n" + 
                "3、 First in first out algorithm\r\n" + 
                "4、 最少使用页面置换算法（LFU）\r\n");
        int Num = sc.nextInt();
        switch(Num) {
        case 3:
            FIFO(3);
            break;
        case 2:
            LRU(3);
            break;
        case 1:
            OPT(3);
            break;
        case 4:
            LFU(3);
            break;
        default:
            System.out.println("there is not the algorithm in the program");
            break;
        }
        
//        for(int i=2;i<32;i++) {
//            System.out.println("---Msize="+i+"------");
//            FIFO(i);
//            LRU(i);
//            OPT(i);
//            LFU(i);
//        }
    }
    static List<Integer> stream;
    /**
     * @return 产生指令序列stream
     */
    public static void produce_addstream(){
        stream=new ArrayList<>();
        while(stream.size()!=320) {//重复① ~ ⑤，直到执行320次指令。
            int m = (int)((Math.random()*319));//起点	:在[0, 319]的指令地址之间随机选区一起点M；
            stream.add(m+1);//顺序执行一条指令，即执行地址为M+1的指令；
            int m2 = (int)((Math.random()*(m+1)));	//在前地址[0, M+1]中随机选取一条指令并执行，该指令的地址为M'；
            stream.add(m2);//执行m2
            stream.add(m2+1);//顺序执行一条指令，其地址为M'+1；
            int m3 = (m2+2)+(int)(+Math.random()*(319-m2-2));//在后地址[M'+2, 319]中随机选取一条指令并执行；
            stream.add(m3);//执行[m2+2,319]
        }	
    }
    /**
     * 根据指令数找到页面
     * @param zhiLing 所需指令
     * @return 指令所在页面
     */
    public static int search(int zhiLing) {
        return zhiLing/10;
    }
    /**
     * 先进先出
     * @param Msize 物理块数
     */
    public static void FIFO(int Msize) {
        Queue<Integer> que = new LinkedList<>();
        Double C = 0.0;//未命中次数
        for(int i=0;i<stream.size();i++) {
            int zhiLing = stream.get(i);
            int yeMian = search(zhiLing);
            System.out.print(yeMian+"\t");
            if((i+1)%10==0)
            	System.out.println();
        }
        for(int i=0;i<stream.size();i++) {
            int zhiLing = stream.get(i);
            int yeMian = search(zhiLing);
            if(que.contains(yeMian)) {
                //无需操作
            	System.out.println("指令"+zhiLing+"对应页面"+yeMian+"已在内存");
            }else {
                C++;
                System.out.println("C:"+C);
                if(que.size()==Msize) {
                    que.poll();	//将最先进入的弹出
                    System.out.println("页面"+yeMian+"被调出，指令为"+zhiLing);
                }
                que.offer(yeMian);	//缺页，将页面调入内存
                System.out.println();
            }
        }
        C-=Msize;
        Double c = 1-(double) (C/320);
        System.out.println("FIFO: "+String.format("%.6f",c)+"   Msize : "+Msize);
    }
    /**
     * 最近最久未使用算法
     * @param Msize 物理块
     */
    public static void LRU(int Msize) {
        Stack<Integer> stack = new Stack<>();
        Double C = 0.0;//未命中次数
        for(int i=0;i<stream.size();i++) {
            int zhiLing = stream.get(i);
            int yeMian = search(zhiLing);
            if(stack.contains(yeMian)) {
                stack.removeElement(yeMian);
                stack.push(yeMian);
            }else {
                C++;
                if(stack.size()==Msize) {
                    stack.removeElement(stack.firstElement());
                }
                stack.push(yeMian);
            }
        }
        C-=Msize;
        Double c = 1-(double) (C/320);
        System.out.println("LRU : "+String.format("%.6f",c)+"   Msize : "+Msize);
    }
    /**
     * 最佳置换算法
     * @param Msize 物理块
     */
    public static void OPT(int Msize) {
        Double C = 0.0;//未命中次数
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<stream.size();i++) {
            int zhiLing = stream.get(i);
            int yeMian = search(zhiLing);
            if(set.contains(yeMian)) {
                
            }else {
                C++;
                if(set.size()==Msize) {
                    int max = -1;//最长时间不会使用的指令的页面
                    int[] temp = new int[32];
                    for(int a:set) {
                        for(int j=i+1;j<stream.size();j++) {
                            if(search(stream.get(j))==a) {
                                temp[a]=j;
                                break;
                            }
                        }
                    }
                    for(int a:set) {
                        if(max==-1) max=a;
                        if(temp[a]==0) {
                            set.remove(a);
                            break;
                        }
                        if(temp[a]>temp[max]) max=a;
                    }
                    if(set.size()==Msize) {
                        set.remove(max);//移除该页面
                    }
                }
                set.add(yeMian);
            }
        }
        C-=Msize;
        Double c = 1-(double) (C/320);
        System.out.println("OPT : "+String.format("%.6f",c)+"   Msize : "+Msize);
    }
    /**
     * 最少使用置换算法
     * @param Msize
     */
    public static void LFU(int Msize) {
        Double C = 0.0;//未命中次数
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<stream.size();i++) {
            int zhiLing = stream.get(i);
            int yeMian = search(zhiLing);
            int[] temp = new int[32];
            if(set.contains(yeMian)) {
                
            }else {
                C++;
                if(set.size()==Msize) {
                    int Min = -1;
                    for(int a:set) {
                        for(int j=i-1;j>=0;j--) {
                            if(search(stream.get(j))==a) {
                                temp[a]=j;
                                break;
                            }
                        }
                    }
                    for(int a:set) {
                        if(Min==-1) {
                            Min=a;
                            continue;
                        }
                        if(temp[a]<temp[Min]) {
                            Min=a;
                        }
                    }
                    set.remove(Min);//移除该页面
                }
                set.add(yeMian);
                temp[yeMian]++;
            }
        }
        C-=Msize;
        Double c = 1-(double) (C/320);
        System.out.println("LFU : "+String.format("%.6f",c)+"   Msize : "+Msize);
    }
}