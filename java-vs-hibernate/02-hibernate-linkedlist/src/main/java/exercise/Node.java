package exercise;

import jakarta.persistence.*;

@Entity
@lombok.Data
@lombok.EqualsAndHashCode(of="id")
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String data;

    @OneToOne
    private Node next;

    public Node() {
    }

    public Node(String data) {
        this.data = data;
    }

    /*
    String getData() {
        return data;
    }

    void setData(String data) {
        this.data = data;
    }

    Node getNext() {
        return next;
    }

    void setNext(Node next) {
        this.next = next;
    }
    */

    public String toString() {
        if (next == null)
            return String.format("Node { id = %d, data = %s, next = null }", id, data);
        else
            return String.format("Node { id = %d, data = %s, next.id = %d }", id, data, next.getId());
    }

    public static void append(Node head, String data) {
        Node new_node = new Node(data);

        // Traverse untill the last node and insert the new_node there
        Node last = head;
        while (last.next != null) {
            last = last.next;
        }

        // Append the new_node at last node
        last.next = new_node;
    }

    public static void printList(Node head) {
        System.out.print("\t[ ");

        // Traverse through the List
        Node curr = head;
        while (curr != null) {
            // Print the data at current node
            System.out.print(curr.data + ", ");

            // Go to next node
            curr = curr.next;
        }
        System.out.print("]\n");
    }
}

