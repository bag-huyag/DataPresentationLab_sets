package setIntArray;

public class test {
    public static void main(String[] args) {
        set s1 = new set(0, 32);
        s1.insert(1);
        set s2 = new set(-32,0);
        s2.insert(0);

        set s3 = s1.merge(s2);
        s3.print();
        set s4 = s1.difference(s2);

        System.out.println(s4.equal(s1));

    }
}
