package setIntArray;

public class test {
    public static void main(String[] args) {
        /*set s1 = new set(-32, 0);
        s1.insert(0);
        set s2 = new set(-32,0);
        s2.insert(0);



        set s3 = s1.merge(s2);
        s3.print();
        set s4 = s1.difference(s2);

        System.out.println(s4.equal(s1));*/



        set s = new set(32, 123);
        set.position p = s.findInArray(33);
        System.out.println(p.index + " " + p.pos);

       /* s = new set(-32,0);
        p = s.findInArray(-1);
        System.out.println(p.index + " " + p.pos);

        s = new set(0, 31);
        p = s.findInArray(0);
        System.out.println(p.index + " " + p.pos);

        s = new set(31, 62);
        p = s.findInArray(33);
        System.out.println(p.index + " " + p.pos);*/
    }
}
