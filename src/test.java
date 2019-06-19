import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Stack;

public class test {

    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet<>();
        set.add("2");
        set.add("1");
        set.add("3");
        System.out.println(set);
        System.out.println(set.iterator().next());
        set.remove(set.iterator().next());
        System.out.println(set);
        System.out.println(set.iterator().next());
    }
}