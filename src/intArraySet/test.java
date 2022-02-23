package intArraySet;

public class test {
    public static void main(String[] args) {
        set s1 = new set(0, 31);
        s1.printData();
        s1.insert(28);
        s1.printData();
        s1.insert(3);
        s1.printData();


        System.out.println();
        System.out.println(s1.min());
        System.out.println(s1.max());

        System.out.println(s1.member(28));
    }
}
