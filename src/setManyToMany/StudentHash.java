package setManyToMany;

public class StudentHash {
    private StudentHashElement[] array;
    private final static int SIZE = 10;

    public StudentHash(int a){
        array = new StudentHashElement[a];
    }

    public void insert(char[] name) {
        int place = hashFunc(name, 0);
        int number = place;
        int counter = 0;
        int deleted = -1;

        place = hashFunc(name, ++counter);

        while (place != number){

            if (array[place] == null) {
                array[place] = new StudentHashElement(name,null);
                return;
            }

            if (deleted == -1 && isDeleted(array[place].getStudName())) {
                deleted = place;
            }

            else place = hashFunc(name,counter++);
        }

        if (deleted != -1){
            array[deleted] = new StudentHashElement(name,null);;
        }
    }

    public void insert(String str) {
        if (str.length() > SIZE) return;
        insert(convertStringToCharArray(str));
    }

    public void delete(char[] name) {
        int temp = search(name);
        if (temp != -1){
            array[temp].getStudName()[0] = '\u0000';
        }
    }

    public void delete(String str) {
        if (str.length() > SIZE) return;
        delete(convertStringToCharArray(str));
    }

    public StudentHashElement member(char[] name) {
        int t = search(name);
        return t == -1 ? null : array[t];
    }

    public StudentHashElement member(String str) {
        if (str.length() > SIZE) return null;
        return member(convertStringToCharArray(str));
    }


    public void print() {
        for (int i = 0; i < array.length; i++){
            array[i].printName();
        }
        System.out.println();
    }

    private int hashFunc(char[] name, int q) {
        int sum = q;
        for (int i = 0; i < name.length; i++){
            sum += name[i];
        }
        return sum % array.length;
    }


    public boolean isDeleted(char[] a){
        return a[0] == '\u0000';
    }

    public static boolean compareCharArrays(char[] a, char[] b){
        for (int i = 0; i < SIZE; i++){
            if (a[i] != b[i])
                return false;
        }
        return true;
    }


    private int search(char []name) {
        int place = hashFunc(name, 0);
        int start = place;
        int counter = 0;
        place = hashFunc(name, ++counter);

        while (array[place] != null || place != start){
            if (compareCharArrays(array[place].getStudName(), name)) {
                return place;
            }
            place = hashFunc(name, ++counter);
        }

        return -1;
    }

    public static char[] convertStringToCharArray(String str){
        char[] name = new char[SIZE];
        copyCharArrays(str.toCharArray(), name);
        return name;
    }

    private static void copyCharArrays(char[] from, char[] to){
        for (int i = 0; i < from.length; i++){
            to[i] = from[i];
        }
    }
}