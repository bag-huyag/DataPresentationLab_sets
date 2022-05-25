package setManyToMany;

public class CourseHash {
    CourseHashElement array[];

    public CourseHash(int a){
        array = new CourseHashElement[a];
    }

    public void insert(int n) {
        int place = hashFunc(n, 0);
        int number = place;
        int counter = 0;
        int deleted = -1;

        place = hashFunc(n, ++counter);

        while (place != number){

            if (array[place] == null) {
                array[place] = new CourseHashElement(n,null);
                return;
            }

            if (deleted == -1 && array[place].getCourse() == 0 ){
                deleted = place;
            }

            else place = hashFunc(n, counter++);
        }

        if (deleted != -1){
            array[deleted].setCourse(n);
        }
    }

    public void delete(int n) {
        int temp = search(n);
        if (temp != -1){
            array[temp].setCourse(0);
        }
    }

    public CourseHashElement member(int n) {
        int temp = search(n);
        return temp >= 0 ? array[temp] : null;
    }

    public void print() {
        for (int i = 0; i < array.length; i++){
            System.out.print(array[i].getCourse() + " ");
        }
        System.out.println();
    }

    private int hashFunc(int n, int q) {
        return (n + q) % array.length;
    }

    private int search(int n) {
        int place = hashFunc(n, 0);
        int start = place;
        int counter = 0;
        place = hashFunc(n, ++counter);

        while (array[place] != null || place != start){
            if (array[place].getCourse() == n) {
                return place;
            }
            place = hashFunc(n, ++counter);
        }

        return -1;
    }
}