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
        array = new int[end/32 + 1];
        shift = 0;
        zeroPosition = 0;

        if (from < 0){
            array = new int[end/32 - start/32 + 2];
            zeroPosition = Math.abs(from)/32;
        }

        if (from > 0) {
            array = new int[end/32 - start/32 +  1];
            shift = from % 32;
        }

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
        if (isTaken(p)) return;

        int mask = -1 << 31;
        mask >>>= p.pos;
        array[p.index] += mask;
    }

    private boolean isTaken(position q){
        int mask = -1 << 31;
        mask >>>=  q.pos;
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
