package setManyToMany;

public class Test {
    public static void main(String[] args) {
        ManyToMany m = new ManyToMany();
        m.putStudentOnCourse("Bob",2);
        m.putStudentOnCourse("Bob",1);
        m.putStudentOnCourse("Alice",1);
        m.listOfCourses("Bob");
        m.listOfStudents(1);
    }
}
