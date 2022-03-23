package dictionaryClosedHashing;

public class dictionary {
    private char[][] array;
    private final static int size = 10;

    public dictionary(int a){
        array = new char[a/size][size];
    }

    public void insert(char[] name) {
        int place = hashFunc(name, 0);
        int number = place;
        int counter = 0;
        int deleted = -1;
        boolean foundDeleted = false;


        place = hashFunc(name, ++counter);

        while (place != number){

            if (array[place] == null) {
                array[place] = new char[10];
                copyArrays(name, array[place]);
                return;
            }

            if (!foundDeleted & isDeleted(array[place])){
                foundDeleted = true;
                deleted = place;
            }

            else place = hashFunc(name,counter++);
        }

        if (deleted != -1){
            copyArrays(name, array[deleted]);
        }
    }

    public void insert(String str) {
        if (str.length() > size) return;
        insert(convertStringToCharArray(str));
    }

    public void delete(char[] name) {
        int place = hashFunc(name, 0);
        int start = place;
        int counter = 0;
        place = hashFunc(name, ++counter);

        while (array[place] != null || place != start) {
            if (compareCharArrays(array[place], name)) {
                array[place] = new char[10];
                return;
            }
            place = hashFunc(name,++counter);
        }
    }

    public void delete(String str) {
        if (str.length() > size) return;
        delete(convertStringToCharArray(str));
    }

    public boolean member(char[] name) {
        int place = hashFunc(name, 0);
        int start = place;
        int counter = 0;
        place = hashFunc(name, ++counter);

        while (array[place] != null || place != start){
            if (compareCharArrays(array[place], name)) {
                return true;
            }
            place = hashFunc(name, ++counter);
        }

        return false;
    }

    public boolean member(String str) {
        if (str.length() > size) return false;
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
        char[] name = new char[size];
        copyArrays(str.toCharArray(), name);
        return name;
    }

    private void copyArrays(char[] from, char[] to){
            for (int i = 0; i < from.length; i++){
                to[i] = from[i];
            }
    }

    public boolean isDeleted(char[] a){
        for (int i = 0; i < a.length; i++) {
            if (a[i] != '\u0000') return false;
        }
        return true;
    }

    private boolean compareCharArrays(char[] a, char[] b){
        for (int i = 0; i < size; i++){
            if (a[i] != b[i])
                return false;
        }
        return true;
    }

    public void printName(char[] name){
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
}
