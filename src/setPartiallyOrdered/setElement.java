package setPartiallyOrdered;

public class setElement extends Trail {
    private int key;
    private int count;

    public setElement(setElement i, Trail n, int k, int c) {
        super(i, n);
        key = k;
        count = c;
    }

    public void increment(){
        count++;
    }

    public void decrement(){
        count--;
    }

    public int getCount() {
        return count;
    }

    public int getKey() {
        return key;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
