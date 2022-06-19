package setCircularList;

public class set {
    //копирующий консутруктор и все методы изменяют входящий объект
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

    public set(set a){
        tail = new Node(a.tail.number, a.tail.next);
        Node tempA = a.tail.next;
        Node tempSelf = tail;
        while (tempA != a.tail){
            tempSelf.next = new Node(tempA.number, tempA.next);
            tempA = tempA.next;
            tempSelf = tempSelf.next;
        }
        tempSelf.next = tail;
    }

    public void insert(int a){
        /*
        Если список пустой
        Если в списке только 1 элемент и этот элемент больше тейла
        Если в списке только 1 элемент и этот элемент меньше тейла
        Если элемент больше хвоста, не меняя хвост вставляем элемент
         */
        if (tail == null){
            tail = new Node(a, null);
            tail.next = tail;
            return;
        }

        if (tail.next == tail){
            if (a == tail.number) return;
            if (a > tail.number){
                tail.next = new Node(tail.number, tail);
                tail.number = a;
                return;
            }

            tail.next = new Node(a, tail);
            return;
        }

        if (a > tail.number){
            Node temp = searchPrev(tail, tail.number);
            if (temp == null) return;
            temp.next = new Node(tail.number, tail);
            tail.number = a;
            return;
        }

        Node temp = searchPlaceForInsert(tail.next, a);

        if (temp == null) {
            tail.next = new Node(a, tail.next);
            return;
        }

        if (temp.next.number == a){
            return;
        }

        Node temporary = temp.next;
        temp.next = new Node(a, temporary);
    }

    public void delete(int a){
        /*
        если пустое множество - ретерн
        если нужно удалить элемент в хвосте и в множестве НЕ один элемент - ссылки перекинуть
        если нужно удалить элемент в хвосте и в множестве один элемент - tail = null
        если элемент не в хвосте = ищем предыдущий и перекижываем ссылки
         */
        if (tail == null) return;
        if (a == tail.number) {
            if (tail != tail.next){
                tail = tail.next;
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

    public set union(set a) {
        //если множества не пересекаюстя
        /*
        создаем указатели на множества и на их слеюудщие элементы после хвостов
        создаем новое множество, хвост которого будет наименьшим из стартов
        переходим на некст в созданынх указателях, если надо
        в цикле идем по двум множествам, до конца одного из них, копируя наименьший элемент в результирующее множество
        остаток копируем в реузльтирующее множество
         */
        set set1 = this;
        set set2 = a;
        Node head1 = set1.tail.next;
        Node head2 = set2.tail.next;
        set resultSet = new set();
        resultSet.tail = new Node(Math.min(head1.number, head2.number), null);
        resultSet.tail.next = resultSet.tail;
        Node temp = resultSet.tail;

        if (resultSet.tail.number == head1.number) head1 = head1.next;
        if (resultSet.tail.number == head2.number) head2 = head2.next;

        while (head1 != set1.tail.next && head2 != set2.tail.next) {
            if (head1.number < head2.number) {
                temp.next = new Node(head1.number, resultSet.tail);
                head1 = head1.next;
            } else {
                temp.next = new Node(head2.number, resultSet.tail);
                head2 = head2.next;
            }
            temp = temp.next;
        }
        System.out.println("first loop");
        resultSet.print();

        if (head1 == set1.tail.next){
            while (head2 != set2.tail.next){
                temp.next = new Node(head2.number, resultSet.tail);
                head2 = head2.next;
                temp = temp.next;
            }
        }
        else {
            while (head1 != set1.tail.next){
                temp.next = new Node(head1.number, resultSet.tail);
                head1 = head1.next;
                temp = temp.next;
            }
        }
        resultSet.tail = temp;
        return resultSet;
    }

    public set Intersection(set a){
        /*
        проверить не нулевые ли множества и пересекаются ли эти множества
        определить наименьшее и наибольшее множество
        найти начало наибольшего в наименьшем start
        найти конец наименьшего в наибольшем end
        создать новое множество и запоминаем его хвост
        пойти циклом от start до end копируя по очереди элементы из множеств
         */
        if (a == null || tail == null) throw new myException("set is empty");
        if (a.tail.next.number > tail.number || tail.next.number > a.tail.number) return null;

        set biggerSet = a.tail.next.number < tail.next.number ? this: a;
        set smallerSet = a.tail.next.number >= tail.next.number ? this: a;
        System.out.println(smallerSet.tail.number);
        System.out.println(biggerSet.tail.number);

        Node biggerSetStart = biggerSet.tail.next;

        Node temp = smallerSet.searchPlaceForInsert(smallerSet.tail, biggerSet.tail.next.number);
        Node smallerSetStart = temp == null ? smallerSet.tail.next : temp.next;

        set resultSet = new set();
        resultSet.tail = new Node(biggerSetStart.number, null);
        temp = resultSet.tail;

        if (smallerSetStart.number == biggerSetStart.number) smallerSetStart = smallerSetStart.next;
        biggerSetStart = biggerSetStart.next;

        while (smallerSetStart != smallerSet.tail && biggerSetStart != biggerSet.tail) {
            if (smallerSetStart.number > biggerSetStart.number) {
                temp.next = new Node(biggerSetStart.number, null);
                biggerSetStart = biggerSetStart.next;
            }
            else {
                temp.next = new Node(smallerSetStart.number, null);
                if (smallerSetStart.number == biggerSetStart.number) biggerSetStart = biggerSetStart.next;
                smallerSetStart = smallerSetStart.next;
            }
            temp = temp.next;
        }

        if (smallerSetStart == smallerSet.tail){
            if (smallerSetStart.number == biggerSetStart.number){
                resultSet.tail = new Node(biggerSetStart.number, resultSet.tail);
            }
        }

        return resultSet;
    }

    public void difference(set a){
        /*
        проверяем на нулл

         */
    }


    private Node searchPrev(Node pos, int n){
        //создаем два указателя - один запаздывающий
        //если не запаздывающий равен искомому элементу - возрващаем запаздывающий
        //в цикле сравниавем элементы
        //если в конце цикла не запаздывающий равен нашей искомой позиции - возвращаем запаздывающий
        Node temp = pos;
        Node temp2 = pos.next;
        if (temp2.number == n) return temp;

        while (temp2 != pos){
            if (temp2.number == n)
                return temp;

            temp = temp2;
            temp2 = temp2.next;
        }

        if (temp2.number == n){
            return temp;
        }
        return null;
    }

    private Node searchPlaceForInsert(Node pos, int n){
        /*
        если искомая позиция уже есть в нашей позиции - возвращаем ее
        если нет - проходим по циклу с запаздывающим указателем
         */
        if (pos.number == n) return pos;
        Node temp = null;
        Node temp2 = pos.next;
        while (temp2 != pos){
            if (temp2.number >= n)
                return temp;

            temp = temp2;
            temp2 = temp2.next;
        }
        return null;
    }

}
