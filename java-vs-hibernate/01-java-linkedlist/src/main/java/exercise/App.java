package exercise;

public class App {

    public static void main(String[] args) {
        System.out.printf("\n\nStart program:\n");

        System.out.printf("\nInitialize list:\n");

        Node one = new Node("one");
        Node two = new Node("two");
        Node three = new Node("three");
        Node four = new Node("four");

        one.setNext(two);
        two.setNext(three);
        three.setNext(four);

        System.out.printf("\nPrint list:\n");

        Node.printList(one);

        System.out.printf("\nGood luck.\n");
    }

}

