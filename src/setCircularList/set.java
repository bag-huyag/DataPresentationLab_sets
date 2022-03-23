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

    Node tail;

    public set(){
        tail = null;
    }

    public void insert(int a){
        if (tail == null){
            tail = new Node(a, null);
            tail.next = tail;
        }
    }
}
