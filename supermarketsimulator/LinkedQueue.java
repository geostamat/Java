package supermarketsimulator;

public abstract class LinkedQueue<E>{ 
    
    private SinglyLinkedList<E> list = new SinglyLinkedList<>();
    
    public LinkedQueue(){
        
    }// end of constructor
    
    public int size(){
        return list.size();
    }
    
    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void enqueue(E element){
        list.addLast(element);
    }

    public E first() {
        return list.first();
    }
    
    public E dequeue() {
        return list.removeFirst();
    }
    
    //method that returns the first Node of the Queue
    public SinglyLinkedList.Node<E> getHead(){
        return list.getHead();
    }// to be used as a starting point for iterating through the whole Queue
    
}// end of class
