package supermarketsimulator;

public class SinglyLinkedList<E> {
    
    // turned protected in order to be able to access it from the rest of the package classes
    // in order to be able to display all Queue Customers in
    // alternatively i would have to make it a seperate class
    protected static class Node<E>{
        private E element;
        private Node<E> next;
        public Node(E e, Node<E> n){
            element = e;
            next = n;
        }// end of Node constructor
        public E getElement(){
            return element;
        }
        public Node<E> getNext(){
            return next;
        }
        public void setNext(Node<E> n){
            next = n;
        }
    }// end of Node nested class
    
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;
    
    public SinglyLinkedList(){
        
    }// end of constructor
    
    public int size(){
        return this.size;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
    
    public E first(){
        if(isEmpty()){
            return null;
        }else{
            return head.getElement();
        }
    }
    
    public E last(){
        if(isEmpty()){
            return null;
        }else{
            return tail.getElement();
        }
    }
    
    public void addFirst(E e){
        head = new Node<E>(e, head);
        if(isEmpty()){
            tail = head;
        }
        size++;
        
    }
    
    public void addLast(E e){
        Node<E> newNode = new Node<E>(e, null);
        if(isEmpty()){
            head = newNode;
        }else{
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }
    
    public E removeFirst(){
        if(isEmpty()){
            return null;
        }else{
            E element = head.getElement();
            head = head.getNext();
            size--;
            if(isEmpty()){
                tail = null;
            }
            return element;
        }
    }

    // method that returns the head Node
    public SinglyLinkedList.Node<E> getHead() {
        return head;
    }


}// end of class
