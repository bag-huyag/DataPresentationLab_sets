package setManyToMany;

public class Test {
    public static void main(String[] args) {
        ManyToMany m = new ManyToMany();
        m.putStudentOnCourse("Bob",2);
        m.listOfCourses("Bob");
    }
}
