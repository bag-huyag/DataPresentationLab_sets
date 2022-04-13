package setCircularList;

public class test {
    public static void main(String[] args) {
        set s = new set();
        set s1 = new set();


        s.insert(-1);
        /*s.insert(-123);
        s.insert(123213);*/

        s1.insert(-10);
        s1.insert(-123);
        /*s1.insert(15);*/



        s.print();
        s1.print();
        set s2 = s1.union(s);
        s2.print();
    }
}
