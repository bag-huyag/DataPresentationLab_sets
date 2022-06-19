package dictionaryOpenHashing;

public class test {
    public static void main(String[] args) {
        dictionary d = new dictionary(50);
        d.insert("Masha");
        d.insert("Mahsa");
        d.insert("Mhasa");
        d.insert("Masha1");
        d.insert("Masha6");
        d.print();
        System.out.println();

        d.delete("Mhasa");
        d.print();
    }
}
