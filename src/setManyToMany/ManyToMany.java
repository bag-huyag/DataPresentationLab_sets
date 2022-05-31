package setManyToMany;

public class ManyToMany {
    private StudentHash studHash;
    private CourseHash cHash;

    ManyToMany(){
        studHash = new StudentHash(4);
        studHash.insert("Bob");
        studHash.insert("Alice");
        studHash.insert("Mary");
        studHash.insert("John");

        cHash = new CourseHash(3);
        cHash.insert(1);
        cHash.insert(2);
        cHash.insert(3);

        studHash.print();
        cHash.print();
        System.out.println();
    }

    public void putStudentOnCourse(String name, int course){
        StudentHashElement c = studHash.member(name);
        if(c == null) return;
        CourseHashElement n = cHash.member(course);
        if(n == null) return;

        RegistrationRecord temp = new RegistrationRecord(
                n.getNext() == null ? n : n.getNext(),
                c.getNext() == null ? c : c.getNext());

        n.setNext(temp);
        c.setNext(temp);
    }

    public void listOfCourses(String name){
        StudentHashElement n = studHash.member(name);
        if(n == null) return;

        Pointer temp = n.getNext();

        while (temp.hasNext()){
            System.out.print(findCourse((RegistrationRecord) temp) + " ");
            temp = ((RegistrationRecord) temp).getSNext();
        }

        System.out.println();
    }

    public void listOfStudents(int course){
        CourseHashElement n = cHash.member(course);
        if(n == null) return;

        Pointer temp = n.getNext();

        while (temp.hasNext()){
            System.out.print(findStudent((RegistrationRecord) temp) + " ");
            temp = ((RegistrationRecord) temp).getCNext();
        }

        System.out.println();
    }

    private int findCourse(RegistrationRecord r){
        Pointer p = r.getCNext();

        while (p.hasNext()) {
            p = ((RegistrationRecord) p).getCNext();
        }

        return (((CourseHashElement) p)).getCourse();
    }

    private String findStudent(RegistrationRecord r){
        Pointer p = r.getSNext();

        while (p.hasNext()) {
            p = ((RegistrationRecord) p).getSNext();
        }

        return (((StudentHashElement) p)).getStudName();
    }



}