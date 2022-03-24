package dictionaryClosedHashing;

public class dictionary {
    private char[][] array;
    private final static int SIZE = 10;

    public dictionary(int a){
        array = new char[a/ SIZE][];
    }

    public void insert(char[] name) {
        int place = hashFunc(name, 0);
        int number = place;
        int counter = 0;
        int deleted = -1;

        place = hashFunc(name, ++counter);

        while (place != number){

            if (array[place] == null) {
                array[place] = new char[SIZE];
                copyCharArrays(name, array[place]);
                return;
            }

            if (deleted == -1 && isDeleted(array[place])){
                deleted = place;
            }

            else place = hashFunc(name,counter++);
        }

        if (deleted != -1){
            copyCharArrays(name, array[deleted]);
        }
    }

    public void insert(String str) {
        if (str.length() > SIZE) return;
        insert(convertStringToCharArray(str));
    }

    public void delete(char[] name) {
        int temp = search(name);
        if (temp != -1){
            array[temp][0] = '\u0000';
        }
    }

    public void delete(String str) {
        if (str.length() > SIZE) return;
        delete(convertStringToCharArray(str));
    }

    public boolean member(char[] name) {
        return search(name) != -1;
    }

    public boolean member(String str) {
        if (str.length() > SIZE) return false;
        return member(convertStringToCharArray(str));
    }

    public void makeNull() {
        for (int i = 0; i < array.length; i++){
            array[i] = null;
        }
    }

    public void print() {
        for (int i = 0; i < array.length; i++){
            printName(array[i]);
        }
    }

    private int hashFunc(char[] name, int q) {
        int sum = q;
        for (int i = 0; i < name.length; i++){
            sum += name[i];
        }
        int temp = sum % array.length;
        return temp;
    }

    private char[] convertStringToCharArray(String str){
        char[] name = new char[SIZE];
        copyCharArrays(str.toCharArray(), name);
        return name;
    }

    private void copyCharArrays(char[] from, char[] to){
            for (int i = 0; i < from.length; i++){
                to[i] = from[i];
            }
    }

    public boolean isDeleted(char[] a){
        return a[0] == '\u0000';
    }

    private boolean compareCharArrays(char[] a, char[] b){
        for (int i = 0; i < SIZE; i++){
            if (a[i] != b[i])
                return false;
        }
        return true;
    }

    private void printName(char[] name){
            if (name == null) return;
            int counter = 0;
            for (int i = 0; i < name.length; i++){
                if (name[i] != '\u0000'){
                    System.out.print(name[i]);
                }
                else counter ++;
            }
            if (counter != 10) System.out.println();
    }

    private int search(char []name) {
        int place = hashFunc(name, 0);
        int start = place;
        int counter = 0;
        place = hashFunc(name, ++counter);

        while (array[place] != null || place != start){
            if (compareCharArrays(array[place], name)) {
                return place;
            }
            place = hashFunc(name, ++counter);
        }

        return -1;
    }
}
