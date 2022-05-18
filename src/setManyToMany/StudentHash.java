package setManyToMany;

public class StudentHash {
    StudentHashElement[] array;

    public StudentHash(int a){
        array = new StudentHashElement[a];
    }

    public void insert(String name) {
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

    public void delete(String name) {
        int temp = search(name);
        if (temp != -1){
            array[temp].setStudName("\u0000");
        }
    }

    public StudentHashElement member(String name) {
        int t = search(name);
        return t == -1 ? null : array[t];
    }

    public void print() {
        for (int i = 0; i < array.length; i++){
            System.out.println(array[i].getStudName());;
        }
    }

    private int hashFunc(String name, int q) {
        int sum = q;
        for (int i = 0; i < name.length(); i++){
            sum += name.charAt(i);
        }
        return sum % array.length;
    }

    private boolean isDeleted(String a){
        return a.startsWith("\u0000");
    }

    private int search(String name) {
        int place = hashFunc(name, 0);
        int start = place;
        int counter = 0;
        place = hashFunc(name, ++counter);

        while (array[place] != null || place != start){
            if ((array[place].getStudName().equals(name))) {
                return place;
            }
            place = hashFunc(name, ++counter);
        }
        return -1;
    }

}
