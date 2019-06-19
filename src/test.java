import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

public class test {

    public static void main(String[] args) {
        CK[] c = new CK[5];
        ArrayList<CK> array = new ArrayList<CK>();

        for (int i = 0; i < 5; i++) {
            c[i] = new CK(2,0);
            array.add(c[i]);
        }
        System.out.println(array.size());
        for (int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i).state);
            System.out.println("页面："+array.get(i).ym);
        }
        System.out.println();
    }
}