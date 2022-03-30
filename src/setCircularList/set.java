package setCircularList;

public class set {
    private static class Node{
        int number;
        Node next;

        public Node(int a, Node n){
            number = a;
            next = n;
        }
    }

    private Node tail;

    public set(){
        tail = null;
    }

    public void insert(int a){
        if (tail == null){
            tail = new Node(a, null);
            tail.next = tail;
            return;
        }

        if (searchPrev(tail, a) == null)
            tail.next = new Node(a, tail.next);
    }

    public void delete(int a){
        if (tail == null) return;
        if (a == tail.number) {
            if (tail != tail.next){
                tail = tail.next;

                if (tail.next.number == a)
                    tail.next = null;
                return;
            }

            tail = null;
            return;
        }

        Node temp = searchPrev(tail, a);

        if (temp != null)
            temp.next = temp.next.next;
    }

    public void assign(set a){
        if (a.tail == null) {
            tail = null;
            return;
        }

        tail = new Node(a.tail.number, null);
        Node tempA = tail;

        Node temp = a.tail.next;

        while (temp != a.tail){
            tempA.next = new Node(temp.number, null);
            tempA = tempA.next;
            temp = temp.next;
        }

        tempA.next = tail;
    }

    public int min(){
        int min = Integer.MAX_VALUE;
        if (tail == null) throw new myException("set is empty");

        Node temp = tail.next;
        while (temp != tail){
            if (temp.number < min){
                min = temp.number;
            }
            temp = temp.next;
        }

        return Math.min(min, tail.number);
    }

    public int max(){
        int max = Integer.MIN_VALUE;
        if (tail == null) throw new myException("set is empty");

        Node temp = tail.next;
        while (temp != tail){
            if (temp.number > max){
                max = temp.number;
            }
            temp = temp.next;
        }

        return Math.max(max, tail.number);
    }

    public void print(){
        if (tail == null) return;
        Node q = tail.next;
        while (q != tail && q != null){
            System.out.print(q.number + " ");
            q = q.next;
        }

        System.out.print(tail.number + " ");
        System.out.println();
    }

    private Node searchPrev(Node pos, int n){
        if (pos.number == n) return pos;
        Node temp = pos;
        Node temp2 = pos.next;
        while (temp2 != pos){
            if (temp2.number == n)
                return temp;

            temp = temp2;
            temp2 = temp2.next;
        }

        return null;
    }


}
