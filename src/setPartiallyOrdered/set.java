package setPartiallyOrdered;

public class set {
    private setElement head;

    public set(){
        head = null;
    }

    public boolean init(int[][] array){
        if (array.length == 0 || array[0][0] == array[0][1])
            return false;

        head = new setElement(null,null, array[0][0], 0);
        head.setId(new setElement(null, null, array[0][1], 0));
        head.setNext(new Trail(head.getId(),null));
        head.getId().increment();

        for (int i = 1; i < array.length; i++) {
            if (array[i][0] == array[i][1]){
                return false;
            }

            setElement temp1 = search(array[i][0]);
            setElement temp2 = search(array[i][1]);

            if (temp1.getKey() != array[i][0]) {
                temp1.setId(new setElement(null, null, array[i][0], 0));
                temp1 = temp1.getId();
            }

            if (temp2.getKey() != array[i][1]){
                temp2.setId(new setElement(null, null, array[i][1], 0));
                temp2 = temp2.getId();
            }

            temp2.increment();

            Trail tempTrail = temp1.getNext();
            temp1.setNext(new Trail(temp2, tempTrail));
        }

        return true;
    }

    public void print(){
        setElement temp = head;
        while (temp != null){
            System.out.print("key: " + temp.getKey() + " | Count : " + temp.getCount() + " | Trail : ");
            Trail tempT = temp.getNext();
            while (tempT != null){
                System.out.print(tempT.getId().getKey() + " ");
                tempT = tempT.getNext();
            }
            System.out.println();
            temp = temp.getId();
        }
    }

    private setElement search(int p){
        setElement q = head;
        setElement q2 = null;

        while (q != null){
            if (p == q.getKey()) {
                return q;
            }
            q2 = q;
            q = q.getId();
        }
        return q2;
    }
}
