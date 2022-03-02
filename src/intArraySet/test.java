package intArraySet;

public class test {
    public static void main(String[] args) {
        set s1 = new set(0, 32);
        s1.insert(0);
        set s2 = new set(-32,0);
        s2.insert(0);

        set s3 = s1.merge(s2);
        s3.printData();
        System.out.println(s1.equal(s2));

    }
}
