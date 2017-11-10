package centrality;

public class LinkedQueue<E>{ 
    
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

}// end of class
