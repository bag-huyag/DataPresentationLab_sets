package setIntArray;


public class set {
    private static class position{
        int index;
        int pos;

        public position(int i, int p){
            index = i;
            pos = p;
        }
    }
    private static final int leftBit = 0b10000000000000000000000000000000;
    private int[] array;
    private int zeroPosition = 0; //array index of zero
    private int start, end;

    public set(int from, int to) {
        if ((from == 0 && to == 0) || (from > to)) return;
        start = from;
        end = to;

        zeroPosition = 0;

        if (start < 0){
            int len = end%32 == 0 ? end/32 - start/32 + 1 : end/32 - start/32 + 2;
            array = new int[len];
            zeroPosition = Math.abs(start)/32;
        }

        if (start >= 0) {
            array = new int[end/32 - start/32 +  1];
        }

    }

    public set(){
        new set(-100,100);
    }

    public set(set a){
        start = a.start;
        end = a.end;
        zeroPosition = a.zeroPosition;
        array = new int[a.array.length];
        for (int i = 0; i < array.length; i ++){
            array[i] = a.array[i];
        }

    }

    public void print(){
        System.out.println("Zero at: " + zeroPosition);
        System.out.println("Start: " + start + " | End: " + end);
        for (int i = 0; i < array.length; i ++)
            System.out.print(Integer.toBinaryString(array[i]) + " ");
        System.out.println();

    }

    public void insert(int q){
        if (q < start || q > end) return;
        position p = findInArray(q);

        array[p.index] |= leftBit >>> p.pos;
    }

    public void delete(int q){
        if (q < start || q > end) return;
        position p = findInArray(q);

        array[p.index] &= ~(leftBit >>> p.pos);
    }

    public void assign(set a){
        start = a.start;
        end = a.start;
        zeroPosition = a.zeroPosition;

        array = new int[a.array.length];
        for (int i = 0; i < array.length; i ++){
            array[i] = a.array[i];
        }
    }

    public int min(){
        for (int i = 0; i < array.length; i++){
            if (array[i] != 0){
                int mask;
                for (int j = 0; j < 32; j++){
                    mask = leftBit >> j;
                    if ((array[i] & mask) != 0){
                        return (32 * i - (start > 0 ? 0 : start % 32) + j );
                    }
                }

            }
        }
        throw new myException("The set is empty");
    }

    public int max(){
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] != 0) {
                int mask;
                int maskCounter = 0;
                for (int j = 31; j >= 0; j--) {
                    mask =  1 << maskCounter;
                    if ((array[i] & mask) != 0){
                        return (32 * i + j);
                    }
                    maskCounter++;
                }
            }
        }
        throw new myException("The set is empty");
    }

    public boolean equal(set a){
        if (a == null) throw new myException("");
        if (a == this) return true;

        if (end < a.start || a.end < start)
            return false;

        // checking left part for zeroes
        // we should find the set, that has lower start
        set leftSet = start < a.start? this : a;
        set secondSet = start < a.start? a : this;
        int intersectionStart = leftSet.findInArray(secondSet.start).index;
        for (int i = 0; i < intersectionStart; i++){
            if (leftSet.array[i] != 0)
                return false;
        }

        //going through intersection
        int secondSetIntersectionStart = leftSet.findInArray(intersectionStart).index;
        int intersectionEnd = secondSet.findInArray(Math.min(end, a.end)).index;
        for (int i = intersectionStart; i < intersectionEnd; i++){
            if (leftSet.array[i] != secondSet.array[secondSetIntersectionStart])
                return false;
            secondSetIntersectionStart++;
        }

        //checking right part of array
        secondSet = end < a.end? a : this;
        leftSet = end < a.end? this : a;
        intersectionEnd = secondSet.findInArray(leftSet.end).index;
        for (int i = intersectionEnd + 1; i < secondSet.findInArray(end).index; i++){
            if (secondSet.array[i] != 0)
                return false;
        }

        return true;
    }

    public void makeNull(){
        for (int i = 0; i < array.length; i++){
            array[i] = 0;
        }
    }

    public boolean member(int q){
        if(isEmpty()) return false;
        if (q < start || q > end) return false;
        position p = findInArray(q);
        return isTaken(p);
    }

    public boolean ifIntersect(set a){
        if (a.end < start || a.start > end) return false;

        int intersectionStart = Math.max(a.start, start);
        int intersectionEnd = Math.min(a.end, end);

        int firstSetStart = findInArray(intersectionStart).index;
        int secondSetStart = a.findInArray(intersectionStart).index;


        for (int i = intersectionStart; i <= intersectionEnd; i++){
            if ((array[firstSetStart] & a.array[secondSetStart]) != 0)
                return false;
            firstSetStart++;
            secondSetStart++;
        }

        return true;
    }

    public set find(set a, int x){
        if(isEmpty() || (x < start || x > end)){
            position p = findInArray(x);
            if (isTaken(p)) {
                return this;
            }
        }

        if(a.isEmpty() || (x < a.start || x > a.end)){
            position p = a.findInArray(x);
            if (a.isTaken(p)) {
                return a;
            }
        }

        return null;
    }

    public set merge(set a){
        if (a == this) return new set(a);
        return mergeSets(a);
    }

    public set union(set a){
        if (a == this) return new set(a);
        return mergeSets(a);
    }

    public set intersection(set a){
        if (a == this) return new set(a);
        if (a.end < start || a.start > end) return null;

        int intersectionStart = Math.max(a.start, start);
        int intersectionEnd = Math.min(a.end, end);

        set c = new set(intersectionStart, intersectionEnd);

        int firstSetStart = findInArray(intersectionStart).index;
        int secondSetStart = a.findInArray(intersectionStart).index;


        for (int i = intersectionStart; i <= intersectionEnd; i++){
            c.array[i] = array[firstSetStart] & a.array[secondSetStart];
            firstSetStart++;
            secondSetStart++;
        }

        return c;
    }

    public set difference(set a){
        if (a == this) return new set(start, end);

        set newSet = new set(a);

        //no intersection between two sets
        if(end < a.start || a.end < start){
            return newSet;
        }

        int secondSetStart = a.findInArray(start).index;
        int intersectionEnd;
        if (end <= a.end)
            intersectionEnd = end;
        else
            intersectionEnd = findInArray(a.end).index;

        for (int i = 0; i < intersectionEnd; i++){
            newSet.array[i] = array[i] & ~(a.array[secondSetStart]) ;
            secondSetStart++;
        }

        return newSet;
    }

    private boolean isTaken(position q){
        int mask = leftBit >>> q.pos;
        return !((array[q.index] & mask) == 0);
    }

    private position findInArray(int q){
        if (start == 0)
            return new position(q / 32, q % 32);

        if (start < 0){
            if (q < 0)
                return new position(q * (-1) / 32, q * (-1) % 32);

            return new position((Math.abs(q) + Math.abs(start)) / 32, (Math.abs(q) + Math.abs(start)) % 32);
        }

        return new position((q - start) / 32, (q - start) % 32);
    }

    private boolean isEmpty(){
        for (int i = 0; i < array.length; i++){
            if (array[i] != 0) return false;
        }
        return true;
    }

    private set mergeSets(set a){
        set n = new set(Math.min(a.start, start), Math.max(a.end, end));

        int counter = 0;
        for (int i = n.findInArray(start).index; i <= n.findInArray(end).index; i++){
            n.array[i] |= array[counter];
            counter++;
        }

        counter = 0;
        for (int i = n.findInArray(a.start).index; i <= n.findInArray(a.end).index; i++){
            n.array[i] |= a.array[counter];
            counter++;
        }

        return n;
    }
}
