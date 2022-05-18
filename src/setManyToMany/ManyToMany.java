package setManyToMany;

public class ManyToMany {
    private StudentHash studHash;
    private CourseHash cHash;

    ManyToMany(StudentHash h1, CourseHash h2){
        studHash = h1;
        cHash = h2;
    }


}