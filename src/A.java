import java.util.*;


public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("����ָ������");
        produce_addstream();	//��ʼ��ָ�
        int k = 0;	//��ʾ���п��Ʊ���
        for(int a:stream) {
            System.out.print((k+1)+":"+a+"\t");
            k++;
            if(k%10 == 0)
            	System.out.println();
        }
        
        System.out.println("\r\n��ѡ��ҳ���û��㷨 \r\n1�� ����û�ҳ���û��㷨��OPT��\r\n" + 
                "2�� ������δʹ��ҳ���û��㷨��LRU��\r\n" + 
                "3�� First in first out algorithm\r\n" + 
                "4�� ����ʹ��ҳ���û��㷨��LFU��\r\n");
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
     * @return ����ָ������stream
     */
    public static void produce_addstream(){
        stream=new ArrayList<>();
        while(stream.size()!=320) {//�ظ��� ~ �ݣ�ֱ��ִ��320��ָ�
            int m = (int)((Math.random()*319));//���	:��[0, 319]��ָ���ַ֮�����ѡ��һ���M��
            stream.add(m+1);//˳��ִ��һ��ָ���ִ�е�ַΪM+1��ָ�
            int m2 = (int)((Math.random()*(m+1)));	//��ǰ��ַ[0, M+1]�����ѡȡһ��ָ�ִ�У���ָ��ĵ�ַΪM'��
            stream.add(m2);//ִ��m2
            stream.add(m2+1);//˳��ִ��һ��ָ����ַΪM'+1��
            int m3 = (m2+2)+(int)(+Math.random()*(319-m2-2));//�ں��ַ[M'+2, 319]�����ѡȡһ��ָ�ִ�У�
            stream.add(m3);//ִ��[m2+2,319]
        }	
    }
    /**
     * ����ָ�����ҵ�ҳ��
     * @param zhiLing ����ָ��
     * @return ָ������ҳ��
     */
    public static int search(int zhiLing) {
        return zhiLing/10;
    }
    /**
     * �Ƚ��ȳ�
     * @param Msize �������
     */
    public static void FIFO(int Msize) {
        Queue<Integer> que = new LinkedList<>();
        Double C = 0.0;//δ���д���
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
                //�������
            	System.out.println("ָ��"+zhiLing+"��Ӧҳ��"+yeMian+"�����ڴ�");
            }else {
                C++;
                System.out.println("C:"+C);
                if(que.size()==Msize) {
                    que.poll();	//�����Ƚ���ĵ���
                    System.out.println("ҳ��"+yeMian+"��������ָ��Ϊ"+zhiLing);
                }
                que.offer(yeMian);	//ȱҳ����ҳ������ڴ�
                System.out.println();
            }
        }
        C-=Msize;
        Double c = 1-(double) (C/320);
        System.out.println("FIFO: "+String.format("%.6f",c)+"   Msize : "+Msize);
    }
    /**
     * ������δʹ���㷨
     * @param Msize �����
     */
    public static void LRU(int Msize) {
        Stack<Integer> stack = new Stack<>();
        Double C = 0.0;//δ���д���
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
     * ����û��㷨
     * @param Msize �����
     */
    public static void OPT(int Msize) {
        Double C = 0.0;//δ���д���
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<stream.size();i++) {
            int zhiLing = stream.get(i);
            int yeMian = search(zhiLing);
            if(set.contains(yeMian)) {
                
            }else {
                C++;
                if(set.size()==Msize) {
                    int max = -1;//�ʱ�䲻��ʹ�õ�ָ���ҳ��
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
                        set.remove(max);//�Ƴ���ҳ��
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
     * ����ʹ���û��㷨
     * @param Msize
     */
    public static void LFU(int Msize) {
        Double C = 0.0;//δ���д���
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
                    set.remove(Min);//�Ƴ���ҳ��
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