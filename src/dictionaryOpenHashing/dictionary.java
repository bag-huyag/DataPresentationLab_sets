package dictionaryOpenHashing;

public class dictionary {
    private static class element{
        char[] name;
        element next;

        public element(){
            name = new char[10];
            next = null;
        }

        public element(char[] n, element nxt){
            next = nxt;
            name = new char[10];
            copyArrays(n, name);
        }

        public void setName(char[] n){
            if (n == null){
                name = null;
                return;
            }
            copyArrays(n, name);
        }

        public static void copyArrays(char[] from, char[] to){
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

        public boolean isEmpty(){
            for (int i = 0; i < name.length; i++){
                if (name[i] != '\u0000') return false;
            }
            return true;
        }
    }

    private element array[];
    private final int size = 10;

    public dictionary(int a){
        array = new element[a/size];
        for (int i = 0; i < array.length; i++){
            array[i] = new element();
        }
    }

    public void insert(char[] name) {
        int place = hashFunc(name);

        if (array[place].isEmpty()) {
            array[place].setName(name);
        }

        element q = array[place];
        element q2 = null;
        while (q != null){
            if (compareCharArrays(q.name, name))
                return;
            q2 = q;
            q = q.next;
        }

        q2.next = new element(name, null);
    }

    public void insert(String str){
        if (str.length() > size) return;
        insert(convertStringToCharArray(str));
    }

    public void delete(char[] name) {
        int place = hashFunc(name);

        if (array[place].isEmpty()) return;

        if (compareCharArrays(array[place].name, name))
            array[place].setName(new char[size]);

        element q = array[place];
        element q2 = null;
        while (q != null){
            if (compareCharArrays(q.name, name)) {
                q2.next = q.next;
                return;
            }
            q2 = q;
            q = q.next;
        }
    }

    public void delete(String str){
        if (str.length() > size) return;
        delete(convertStringToCharArray(str));
    }

    public boolean member(char[] name) {
        int place = hashFunc(name);

        if (compareCharArrays(array[place].name, name))
            return true;

        element q = array[place];
        while (q != null){
            if (compareCharArrays(q.name, name)) {
                return true;
            }
            q = q.next;
        }

        return false;
    }

    public boolean member(String str){
        if (str.length() > size) return false;
        return member(convertStringToCharArray(str));
    }

    public void makenull(){
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
        for (int i = 0; i < size; i++){
            if (a[i] != b[i])
                return false;
        }
        return true;
    }

    private char[] convertStringToCharArray(String str){
        char[] name = new char[size];
        element.copyArrays(str.toCharArray(), name);
        return name;
    }
}