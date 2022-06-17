package setManyToMany;

public class ManyToMany {
    //удаление должно удалять регистрационную запись из двух списков
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

        if (onCourse(n,name)){
            System.out.println(name + " is already enrolled");
            return;
        }

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
        if (temp == null) return;

        while (temp.hasNext()){
            System.out.print(findCourse((RegistrationRecord) temp).getCourse() + " ");
            temp = ((RegistrationRecord) temp).getSNext();
        }

        System.out.println();
    }

    public void listOfStudents(int course){
        CourseHashElement n = cHash.member(course);
        if(n == null) return;

        Pointer temp = n.getNext();
        if (temp == null) return;

        while (temp.hasNext()){
            findStudent((RegistrationRecord) temp).printName();
            temp = ((RegistrationRecord) temp).getCNext();
        }

        System.out.println();
    }

    public void removeStudentFromCourse(int course, String name){
        StudentHashElement c = studHash.member(name);
        if(c == null) return;
        CourseHashElement n = cHash.member(course);
        if(n == null) return;

        //предыдущая запись, перед интересующей нас записью со стороны курса
        //если нужная нам запись ялвяется первой на данном курсе
        Pointer result = searchPrevStudent(n, name);
        if (result == null) return;

        if (result.hasNext()){
            RegistrationRecord r1 = (RegistrationRecord) result;
            //если нужная нам запись последняя в кольце курса, то ссылаемся на сам курс
            if (!r1.getCNext().hasNext()) r1.setCNext(n);
            else r1.setCNext(((RegistrationRecord) r1.getCNext()).getCNext());
        }
        else {
            if (n.getNext().hasNext()){
                RegistrationRecord temp2 = n.getNext();
                if (temp2.getCNext().hasNext()) n.setNext((RegistrationRecord) temp2.getCNext());
                else n.setNext(null);
            }
        }

        //предыдущая запись, перед интересующей нас записью со стороны студента
        //если нужная нам запись ялвяется первой у данного студента
        result = searchPrevCourse(c, course);
        if (result == null) return;

        if (result.hasNext()){
            RegistrationRecord r2 = (RegistrationRecord) result;
            //если нужная нам запись последняя в кольце курса, то ссылаемся на сам курс
            if (!r2.getSNext().hasNext()) r2.setSNext(c);
            else r2.setSNext(((RegistrationRecord) r2.getSNext()).getSNext());
        }
        else {
            RegistrationRecord temp2 = c.getNext();
            if (temp2.getSNext().hasNext()) c.setNext((RegistrationRecord) temp2.getSNext());
            else c.setNext(null);
        }
    }

    public void removeStudentEverywhere(String name){
        StudentHashElement c = studHash.member(name);
        if(c == null) return;

        //если студеент никуда не записан
        if (c.getNext() == null) return;

        Pointer temp = c.getNext();

        while (temp.hasNext()){
            //найти курс, на который записан студент
            CourseHashElement course = ((RegistrationRecord) temp).getCNext().hasNext() ?
                    findCourse((RegistrationRecord) ((RegistrationRecord) temp).getCNext()) :
                    (CourseHashElement) ((RegistrationRecord) temp).getCNext();

            Pointer result = searchPrevStudent(course, name);
            if (result == null) continue;

            //if the previous is not Course, but record
            if (result.hasNext()){
                RegistrationRecord temp2 = (RegistrationRecord) result;
                temp2.setCNext(((RegistrationRecord)temp2.getCNext()).getCNext());
            }
            else {
                //if there are other registrations on this course
                if (course.getNext().getCNext().hasNext()) course.setNext((RegistrationRecord) course.getNext().getCNext());

                //only one reg and it should be deleted
                else course.setNext(null);
            }
            temp = ((RegistrationRecord) temp).getSNext();
        }
        c.setNext(null);
    }


    private CourseHashElement findCourse(RegistrationRecord r){
        Pointer p = r.getCNext();

        while (p.hasNext())
            p = ((RegistrationRecord) p).getCNext();

        return ((CourseHashElement) p);
    }

    private StudentHashElement findStudent(RegistrationRecord r){
        Pointer p = r.getSNext();

        while (p.hasNext())
            p = ((RegistrationRecord) p).getSNext();

        return (((StudentHashElement) p));
    }

    private Pointer searchPrevStudent(CourseHashElement s, String name){
        Pointer p = s.getNext();
        Pointer p2 = s;

        while (p.hasNext()) {
            if (StudentHash.compareCharArrays(findStudent((RegistrationRecord) p).getStudName(), StudentHash.convertStringToCharArray(name)))
                return p2;
            p2 = p;
            p = ((RegistrationRecord) p).getCNext();
        }
        return null;
    }

    private boolean onCourse(CourseHashElement s, String name){
        Pointer p = s.getNext();
        if(p == null) return false;

        while (p.hasNext()) {
            if (StudentHash.compareCharArrays(findStudent((RegistrationRecord) p).getStudName(), StudentHash.convertStringToCharArray(name)))
                return true;
            p = ((RegistrationRecord) p).getCNext();
        }
        return false;
    }

    private Pointer searchPrevCourse(StudentHashElement s, int course){
        Pointer p = s.getNext();
        Pointer p2 = s;

        while (p.hasNext()) {
            if (findCourse((RegistrationRecord) p).getCourse() == course)
                return p2;
            p2 = p;
            p = ((RegistrationRecord) p).getSNext();
        }
        return null;
    }

}