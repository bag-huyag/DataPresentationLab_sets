package setManyToMany;

public class Test {
    public static void main(String[] args) {
        ManyToMany m = new ManyToMany();
        m.putStudentOnCourse("Bob",2);
        m.putStudentOnCourse("Bob",1);
        m.putStudentOnCourse("Alice",1);
        /*m.removeStudentFromCourse(1, "Alice");
        m.removeStudentFromCourse(1,"Bob");
        m.removeStudentFromCourse(2,"Bob");

        System.out.print("Bob's list of courses: ");
        m.listOfCourses("Bob");
        System.out.println();
        System.out.print("Alice's list of courses: ");
        m.listOfCourses("Alice");
        System.out.println();
        System.out.print("All students from 1 course: ");
        m.listOfStudents(1);
        System.out.println();
        System.out.print("All students from 2 course: ");
        m.listOfStudents(2);
        System.out.println();*/
        System.out.println("removed bob everywhere");
        m.removeStudentEverywhere("Alice");
        m.removeStudentEverywhere("Bob");
        System.out.print("Bob's list of courses: ");
        m.listOfCourses("Alice");
        System.out.println();
        System.out.print("All students from 1 course: ");
        m.listOfStudents(1);
        System.out.print("All students from 2 course: ");
        m.listOfStudents(2);
        System.out.println();
    }
}
