import java.util.Random;

public class DLinkedList<T> {
    class Node {
        public T data;
        public Node previous;
        public Node next;
        public Node(T t) { previous = next = null; data = t; }
    }
    Node tail;
    Node head;
    public DLinkedList() { tail = head = null; }  // default constructor
    // appends to tail of list
    void Append(T data) {
        Node pdata = new Node(data);
        if (tail == null) {
            head = tail = pdata;
        } else {
            pdata.previous = tail;
            tail.next = pdata;
            tail = pdata;
        }
    }
    // prepends to head of list
    void Prepend(T data) {
        Node pdata = new Node(data);
        if (head == null) {
            head = tail = pdata;
        } else {
            head.previous = pdata;
            pdata.next = head;
            head = pdata;
        }
    }
    // inserts data after found data
    // if `find` not exist, don't insert.
    void InsertAfter(T find, T data) {
        Node temp = head;
        while (temp != null && !temp.data.equals(find)) {
            temp = temp.next;
        }
        if (temp != null) {
            Node pdata = new Node(data);
            pdata.previous = temp;
            pdata.next = temp.next;
            if (temp.next != null) {
                temp.next.previous = pdata;
            } else {
                tail = pdata;
            }
            temp.next = pdata;
        }
    }
    // inserts data before found data
    void InsertBefore(T find, T data) {
        Node temp = tail;
        while(temp != null && !temp.data.equals(find)) {
            temp = temp.previous;
        }
        if (temp != null) {
            Node pdata = new Node(data);
            pdata.previous = temp.previous;
            pdata.next = temp;
            if (temp.previous != null) {
                temp.previous.next = pdata;
            } else {
                head = pdata;
            }
            temp.previous = pdata;
        }
    }
    // finds data node and returns it
    Node Search(T data) {
        Node temp = tail;
        while (temp != null && !temp.data.equals(data)) {
            temp = temp.previous;
        }
        return temp;
    }
    // deletes a node from the list
    void Delete(T data) {
        Node temp = head;
        while (temp != null && !temp.data.equals(data)) {
            temp = temp.next;
        }
        if (temp != null) {
            if (temp.previous == null && temp.next == null) {
                head = tail = null;
            } else if (temp.previous == null) {
                head = temp.next;
                temp.next.previous = null;
                temp.next = null;
            } else if (temp.next == null) {
                tail = temp.previous;
                temp.previous.next = null;
                temp.previous = null;
            } else {
                temp.previous.next = temp.next;
                temp.next.previous = temp.previous;
                temp.previous = temp.next = null;
            }
        }
    }
    // returns number of nodes in list
    int Count() {
        int c = 0;
        Node temp = tail;
        while (temp != null) {
            ++c;
            temp = temp.previous;
        }
        return c;
    }
    // returns true if list is empty
    boolean IsEmpty()
    {
        return tail == null;
    }
    // prints list from tail of list
    void Output()
    {
        Node rover = tail;
        while (rover != null)
        {
            System.out.print( rover.data+"\t" );
            rover = rover.previous;
        }
        System.out.println();
    }

    T get(int i) {
        Node tmp = head;
        while(i-- > 0) {
            tmp = tmp.next;
        }
        if (tmp != null) {
            return tmp.data;
        }
        return null;
    }

    public static void main(String args[]) {
        Random rand = new Random();
        int count = 10;
        DLinkedList<Integer> list = new DLinkedList<Integer>();
        for (int x = 0; x < count; x++)
        {
            int rnumber = rand.nextInt(100) + 1;
            list.Append(rnumber);
            System.out.print(rnumber+"\t");
        }
        System.out.println();

        System.out.println("\t1) Output list from tail to head is:");
        list.Output();

        int a = rand.nextInt(100) + 1;
        System.out.println("\t2) Prepend element: " + a);
        list.Prepend(a);
        System.out.println("\t3) After prepend, the list is (from tail to head): ");
        list.Output();

        int b = rand.nextInt(100) + 1;
        System.out.println("\t4) Insert " + b + " after " + a);
        list.InsertAfter(a, b);
        System.out.println("\t5) After insert " + b + " after " + a + ", the list is (from tail to head): ");
        list.Output();

        int c = rand.nextInt(100) + 1;
        System.out.println("\t6) Insert " + c + " before " + b);
        list.InsertBefore(b, c);
        System.out.println("\t7) After insert " + c + " before " + b + ", the list is (from tail to head): ");
        list.Output();

        System.out.println("\t8) list size is: " + list.Count());

        DLinkedList.Node t = list.Search(c);
        System.out.println("\t9) Search " + c + " is successful?" + ( t!= null));

        System.out.println("\t10) Delete " + c);
        list.Delete(c);
        System.out.println("\t11) After delete " + c + ", list size is: " + list.Count());

        System.out.println("\t12) Output list from tail to head is:");
        list.Output();

        t = list.Search(c);
        System.out.println("\t13) After delete " + c + ", Search " + c + " is successful?" + ( t!= null));

        System.out.println("\t14) Delete " + b);
        list.Delete(b);
        System.out.println("\t15) After delete " + b + ", list size is: " + list.Count());

        System.out.println("\t16) Output list from tail to head is:");
        list.Output();

        System.out.println("\t17) list is Empty now?" + list.IsEmpty());
    }
}
