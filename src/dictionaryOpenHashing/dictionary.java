package dictionaryOpenHashing;

public class dictionary {
    private static class element{
        char[] name;
        element next;

        public element(){
            name = null;
            next = null;
        }

        public element(char[] n, element nxt){
            next = nxt;
            name = new char[10];
            copyCharArrays(n, name);
        }

        public void setName(char[] n){
            if (n == null){
                name = null;
                return;
            }
            copyCharArrays(n, name);
        }

        public static void copyCharArrays(char[] from, char[] to){
            for (int i = 0; i < from.length; i++){
                to[i] = from[i];
            }
        }

        public void printName(){
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

    private element array[];
    private final int SIZE = 10;

    public dictionary(int a){
        array = new element[a/ SIZE];
        for (int i = 0; i < array.length; i++){
            array[i] = new element();
        }
    }

    public void insert(char[] name) {
        int place = hashFunc(name);

        if (array[place].name == null) {
            array[place].name = new char[SIZE];
            array[place].setName(name);
            return;
        }

        if (searchPrev(name, place) == null) {
            array[place].next = new element(name, array[place].next);
        }
    }

    public void insert(String str){
        if (str.length() > SIZE) return;
        insert(convertStringToCharArray(str));
    }

    public void delete(char[] name) {
        int place = hashFunc(name);

        if (array[place].name == null) return;

        if (compareCharArrays(array[place].name, name)){
            if (array[place].name == null){
                array[place].name = null;
                array[place].next = null;
            }
            else {
                array[place].setName(array[place].next.name);
                array[place].next = array[place].next.next;
            }

            return;
        }

        element temp = searchPrev(name, place);
        if (temp != null){
            temp.next = temp.next.next;
        }

    }

    public void delete(String str){
        if (str.length() > SIZE) return;
        delete(convertStringToCharArray(str));
    }

    public boolean member(char[] name) {
        int place = hashFunc(name);

        if (compareCharArrays(array[place].name, name))
            return true;

        return (searchPrev(name, place) != null);
    }

    public boolean member(String str){
        if (str.length() > SIZE) return false;
        return member(convertStringToCharArray(str));
    }

    public void makeNull(){
        for (int i = 0; i < array.length; i++){
            array[i].setName(null);
            array[i].next = null;
        }
    }

    public void print(){
        for (int i = 0; i < array.length; i++){
            element q = array[i];
            while (q != null){
                q.printName();
                q = q.next;
            }
        }
    }

    private int hashFunc(char[] name){
        int sum = 0;
        for (int i = 0; i < name.length; i++){
            sum += name[i];
        }
        return sum % array.length;
    }

    private boolean compareCharArrays(char[] a, char[] b){
        for (int i = 0; i < SIZE; i++){
            if (a[i] != b[i])
                return false;
        }
        return true;
    }

    private char[] convertStringToCharArray(String str){
        char[] name = new char[SIZE];
        element.copyCharArrays(str.toCharArray(), name);
        return name;
    }

    private element searchPrev(char[] name, int place){
        element q = array[place];
        element q2 = null;
        while (q != null){
            if (compareCharArrays(q.name, name)) {
                return q2;
            }
            q2 = q;
            q = q.next;
        }

        return null;
    }
}