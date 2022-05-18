package setManyToMany;

public class StudentHashElement extends Pointer {
    private RegistrationRecord next;
    private String studName;

    public StudentHashElement(String s, RegistrationRecord n) {
        studName = s;
        next = n;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String s) {
        studName = s;
    }

    public void setNext(RegistrationRecord n) {
        next = n;
    }

    public RegistrationRecord getNext() {
        return next;
    }
}
