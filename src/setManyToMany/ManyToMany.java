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
        RegistrationRecord temp;

        //предыдущая запись, перед интересующей нас записью со стороны курса
        //если нужная нам запись ялвяется первой на данном курсе
        if (StudentHash.compareCharArrays(findStudent(n.getNext()).getStudName(), StudentHash.convertStringToCharArray(name))){
            if (n.getNext().hasNext()){
                RegistrationRecord temp2 = n.getNext();
                if (temp2.getCNext().hasNext()){
                    n.setNext((RegistrationRecord) temp2.getCNext());
                }
                else n.setNext(null);
            }
            else n.setNext(null);
        }
        else {
            RegistrationRecord r1 = searchPrevStudent(n,name);
            if (r1 == null) return;
            //если нужная нам запись последняя в кольце курса, то ссылаемся на сам курс
            if (!r1.getCNext().hasNext()){
                r1.setCNext(n);
            }
            else{
                temp = (RegistrationRecord) r1.getCNext();
                r1.setCNext(temp.getCNext());
            }
        }

        //предыдущая запись, перед интересующей нас записью со стороны студента
        //если нужная нам запись ялвяется первой у данного студента
        if (findCourse(c.getNext()).getCourse() == course){
            if (c.getNext().hasNext()){
                RegistrationRecord temp2 = c.getNext();
                if (temp2.getSNext().hasNext()){
                    c.setNext((RegistrationRecord) temp2.getSNext());
                }
                else c.setNext(null);
            }
            else c.setNext(null);
        }
        else{
            RegistrationRecord r2 = searchPrevCourse(c,course);
            if (r2 == null) return;
            //если нужная нам запись последняя в кольце курса, то ссылаемся на сам курс
            if (!r2.getSNext().hasNext()){
                r2.setSNext(c);
            }
            else{
                temp = (RegistrationRecord) r2.getCNext();
                r2.setCNext(temp.getSNext());
            }
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
            CourseHashElement course = ((RegistrationRecord) temp).getCNext().hasNext() ? findCourse((RegistrationRecord) ((RegistrationRecord) temp).getCNext()) : (CourseHashElement) ((RegistrationRecord) temp).getCNext();

            //искомая запись первая на курсе
            char[] fst = findStudent(course.getNext()).getStudName();
            char[] snd = StudentHash.convertStringToCharArray(name);
            if ((StudentHash.compareCharArrays(fst, snd))){
                if (course.getNext().getCNext().hasNext()){
                    course.setNext((RegistrationRecord) course.getNext().getCNext());
                }
                else course.setNext(null);
                temp = ((RegistrationRecord) temp).getSNext();
                continue;
            }


            RegistrationRecord temp2 = searchPrevStudent(course,name);
            if (temp2 == null) {
                course.setNext((RegistrationRecord) course.getNext().getCNext());
                temp = ((RegistrationRecord) temp).getCNext();
                continue;
            }

            if (!temp2.getCNext().hasNext())
                temp2.setCNext(c);
            else {
                temp2.setCNext(((RegistrationRecord)temp2.getCNext()).getCNext());
            }
            temp = ((RegistrationRecord) temp).getSNext();
        }
        c.setNext(null);
    }


    private CourseHashElement findCourse(RegistrationRecord r){
        Pointer p = r.getCNext();

        while (p.hasNext()) {
            p = ((RegistrationRecord) p).getCNext();
        }

        return ((CourseHashElement) p);
    }

    private StudentHashElement findStudent(RegistrationRecord r){
        Pointer p = r.getSNext();

        while (p.hasNext()) {
            p = ((RegistrationRecord) p).getSNext();
        }

        return (((StudentHashElement) p));
    }

    private RegistrationRecord searchPrevStudent(CourseHashElement s, String name){
        Pointer p = s.getNext();
        Pointer p2 = null;

        while (p.hasNext()) {
            if (StudentHash.compareCharArrays(findStudent((RegistrationRecord) p).getStudName(), StudentHash.convertStringToCharArray(name))){
                return (RegistrationRecord)p2;
            }
            p2 = p;
            p = ((RegistrationRecord) p).getCNext();
        }
        return null;
    }

    private RegistrationRecord searchPrevCourse(StudentHashElement s, int course){
        Pointer p = s.getNext();
        Pointer p2 = null;

        while (p.hasNext()) {
            if (findCourse((RegistrationRecord) p).getCourse() == course){
                return (RegistrationRecord)p2;
            }
            p2 = p;
            p = ((RegistrationRecord) p).getSNext();
        }
        return null;
    }

}