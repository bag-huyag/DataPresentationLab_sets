package setManyToMany;

public class CourseHashElement extends Pointer{
    private RegistrationRecord next;
    private int course;

    public CourseHashElement(int c, RegistrationRecord n){
        course = c;
        next = n;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int c) {
        course = c;
    }

    public void setNext(RegistrationRecord n) {
        next = n;
    }

    public RegistrationRecord getNext() {
        return next;
    }
}
