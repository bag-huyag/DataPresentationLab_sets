package setPartiallyOrdered;

public class set {
    private setElement head;

    public set(int[][] array){
        head = new setElement(null,null, array[0][0], 0);
        head.setId(new setElement(null, null, array[0][1], 0));
        head.setNext(new Trail(head.getId(),null));
        setElement cursor = head.getId();
        for (int i = 1; i < array.length; i++){

            setElement temp1 = searchPrev(array[i][0]);
            setElement temp2 = searchPrev(array[i][1]);

            if (temp1 == null && head.getKey() != array[i][0]){
                cursor.setId(new setElement(null, null, array[i][0], 0));
                cursor = cursor.getId();
            }

            else {

                if (head.getKey() == array[i][0])
                    temp1 = head;
                else temp1 = temp1.getId();

                temp1.increment();

                setElement targetElement;

                if (temp2 == null){
                    cursor.setId(new setElement(null, null, array[i][1], 0));
                    cursor = cursor.getId();
                    targetElement = cursor;
                }
                else {
                    targetElement = temp2.getId();
                }

                Trail lastTrail = searchTrail(temp1,targetElement);
                if (lastTrail == null)
                    temp1.setNext(new Trail(targetElement, null));
                else
                    lastTrail.setNext(new Trail(targetElement, null));
            }

        }

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

    private setElement searchPrev(int p){
        setElement q = head;
        setElement q2 = null;

        while (q != null){
            if (p == q.getKey()) {
                return q2;
            }
            q2 = q;
            q = q.getId();
        }
        return null;
    }

    private Trail searchTrail(setElement p, setElement target){
        Trail q = p.getNext();
        Trail q2 = null;

        while (q != null){
            if (q.getId() == target) {
                return q2;
            }
            q2 = q;
            q = q.getNext();
        }
        return q2;
    }


}
