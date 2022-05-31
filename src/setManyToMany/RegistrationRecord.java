package setManyToMany;

public class RegistrationRecord extends Pointer {
    private Pointer cNext;
    private Pointer sNext;

    public RegistrationRecord(Pointer l1, Pointer l2) {
        cNext = l1;
        sNext = l2;
    }

    public Pointer getCNext() {
        return cNext;
    }

    public Pointer getSNext() {
        return sNext;
    }

    public void setCNext(Pointer l1) {
        cNext = l1;
    }

    public void setSNext(Pointer l2) {
        sNext = l2;
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
