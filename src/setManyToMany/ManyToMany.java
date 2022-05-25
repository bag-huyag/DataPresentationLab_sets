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
        System.out.println();
        cHash.print();
    }

    public void putStudentOnCourse(String name, int course){
        StudentHashElement n = studHash.member(name);
        if(n == null) return;
        CourseHashElement c = cHash.member(course);
        if(c == null) return;

        RegistrationRecord temp = new RegistrationRecord(
                n.getNext() == null ? n: n.getNext(),
                c.getNext() == null ? c : c.getNext());

        n.setNext(temp);
        c.setNext(temp);
    }

    public void listOfCourses(String name){
        StudentHashElement n = studHash.member(name);
        if(n == null) return;

        RegistrationRecord temp = n.getNext();
        Pointer p = temp.getCNext();

        while (p != null && p.hasNext()){
            System.out.print(findCourse(temp));
            temp = (RegistrationRecord) temp.getSNext();
            p = temp;
        }
    }

    private int findCourse(RegistrationRecord r){
        Pointer p = r.getCNext();
        while (p.hasNext()) {
            p = r.getCNext();
        }
        
        CourseHashElement temp = (CourseHashElement) p;
        return temp.getCourse();
    }



}