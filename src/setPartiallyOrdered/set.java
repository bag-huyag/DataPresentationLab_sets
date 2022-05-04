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

    public void sort(){
        set newSet = new set();
        setElement q = head;
        setElement q2 = null;
        setElement lastInNewSet = null;
        while (q != null){

            if (q.getCount() == 0){
                Trail tempTrail = q.getNext();
                while (tempTrail != null){
                    tempTrail.getId().decrement();
                    tempTrail = tempTrail.getNext();
                }
                q.setNext(null);

                setElement temporary = q;
                if (q == head){
                    head = head.getId();
                }
                else {
                    q2.setId(q.getId());
                }

                if (newSet.head != null){
                    setElement temp = lastInNewSet.getId();
                    lastInNewSet.setId(temporary);
                    lastInNewSet.getId().setId(temp);
                    lastInNewSet = lastInNewSet.getId();
                }
                else {
                    newSet.head = temporary;
                    newSet.head.setId(null);
                    lastInNewSet = newSet.head;
                }
                q = head;
                q2 = null;
                continue;
            }
            q2 = q;
            q = q.getId();
        }
        print();
        System.out.println();
        head = newSet.head;
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
