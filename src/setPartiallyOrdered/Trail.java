package setPartiallyOrdered;

public class Trail {
    private setElement id;
    private Trail next;

    public Trail(setElement i, Trail n){
        id = i;
        next = n;
    }

    public Trail getNext() {
        return next;
    }

    public setElement getId() {
        return id;
    }

    public void setId(setElement id) {
        this.id = id;
    }

    public void setNext(Trail next) {
        this.next = next;
    }

}
