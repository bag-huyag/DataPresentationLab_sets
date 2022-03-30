package setCircularList;

public class test {
    public static void main(String[] args) {
        set s = new set();
        s.insert(-1);
        s.insert(-123);
        s.insert(123213);

        set s1 = new set();
        s1.assign(s);
        s1.print();
        s.print();
        System.out.println(s1.min());
        System.out.println(s1.max());
    }
}
