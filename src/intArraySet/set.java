package intArraySet;


public class set {
    private class position{
        int index;
        int pos;

        public position(int i, int p){
            index = i;
            pos = p;
        }
    }

    private int[] array;
    private int shift = 0; //shift in INT
    private int zeroPosition = 0; //array index of zero
    private int start, end;

    public set(int from, int to) {
        if ((from == 0 && to == 0) || (from > to)) return;
        start = from;
        end = to;

        shift = 0;
        zeroPosition = 0;

        if (start == 0){
            array = new int[end/32 + 1];
        }

        if (start < 0){
            array = new int[end/32 - start/32 + 2];
            zeroPosition = Math.abs(start)/32;
        }

        if (start > 0) {
            array = new int[end/32 - start/32 +  1];
            shift = start % 32;
        }

    }

    public set(){
        new set(-100,100);
    }

    public void printData(){
        System.out.println("Shift: " + shift);
        System.out.println("Zero at: " + zeroPosition);
        System.out.println("Start: " + start + " | End: " + end);
        for (int i = 0; i < array.length; i ++)
            System.out.print(array[i] + " ");
        System.out.println();

    }

    public void insert(int q){
        if (q < start || q > end) return;
        position p = findInArray(q);

        array[p.index] |= 0b10000000000000000000000000000000 >>> p.pos;
    }

    public void delete(int q){
        if (q < start || q > end) return;
        position p = findInArray(q);

        array[p.index] &= ~(0b10000000000000000000000000000000 >>> p.pos);
    }

    public void assign(set a){
        new set(a.start, a.end);
        shift = a.shift;
        zeroPosition = a.zeroPosition;

        for (int i = 0; i < a.array.length; i ++){
            array[i] = a.array[i];
        }
    }

    public int min(){
        for (int i = 0; i < array.length; i++){
            if (array[i] != 0){
                int mask;
                for (int j = 0; j < 32; j++){
                    mask = 0b10000000000000000000000000000000 >> j;
                    if ((array[i] & mask) != 0){
                        return (32 * i - shift + j );
                    }
                }

            }
        }
        throw new myException("The set is empty");
    }

    public int max(){
        for (int i = array.length - 1; i >= 0; i--){
            if (array[i] != 0){
                int mask;
                int maskCounter = 0;
                for (int j = 31; j >= 0; j--){
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
        if (end < a.start || a.end < start)
            return false;

        //equal sets
        if (start == a.start && end == a.end){
            for (int i = 0; i < array.length; i++)
                if (array[i] != a.array[i])
                    return false;

            return true;
        }

        //one set - other's subset
        if ((start > a.start && end < a.end) || (a.start > start && a.end < end)){
            set biggerSet, smallerSet;
            if (a.start > start){
                biggerSet = this;
                smallerSet = a;
            }
            else {
                biggerSet = a;
                smallerSet = this;
            }

            int intersectionStart = findInArray(smallerSet.start).index;
            int intersectionEnd = findInArray(smallerSet.end).index;

            for (int i = 0; i < intersectionStart; i++){
                if (biggerSet.array[i] != 0)
                    return false;
            }

            int counter = 0;
            for (int i = intersectionStart; i < intersectionEnd; i++){
                if (biggerSet.array[i] != smallerSet.array[counter])
                    return false;

                counter++;
            }

            for (int i = intersectionEnd; i < biggerSet.array.length; i++){
                if (biggerSet.array[i] != 0)
                    return false;
            }

            return true;
        }

        //more variants
        return false;
    }

    public void makeNull(){
        for (int i = 0; i < array.length; i++){
            array[i] = 0;
        }
    }

    public boolean member(int q){
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

        }

        return true;
    }

    private boolean isTaken(position q){
        int mask = 0b10000000000000000000000000000000 >>> q.pos;
        return !((array[q.index] & mask) == 0);
    }

    private position findInArray(int q){
        if (start == 0)
            return new position(q / 32, q % 32);

        if (start < 0){
            if (q < 0)
                return new position(q * (-1) / 32, q * (-1) % 32);

            return new position(Math.abs(q + start) / 32, Math.abs(q + start) % 32);
        }

        return new position((q - start) / 32, (q - start) % 32);
    }
}
