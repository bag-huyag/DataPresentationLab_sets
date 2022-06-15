package setManyToMany;

public class StudentHashElement extends Pointer {
    //имя - массив чаров
    private RegistrationRecord next;
    private char[] studName;

    public StudentHashElement(char[] s, RegistrationRecord n) {
        studName = s;
        next = n;
    }

    public char[] getStudName() {
        return studName;
    }

    public void setNext(RegistrationRecord n) {
        next = n;
    }

    public RegistrationRecord getNext() {
        return next;
    }

    public void printName(){
        char[] name = this.getStudName();
        int counter = 0;
        for (int i = 0; i < name.length; i++){
            if (name[i] != '\u0000'){
                System.out.print(name[i]);
            }
            else counter ++;
        }
        if (counter != 10) System.out.print(" ");
    }
}
