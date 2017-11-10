package supermarketsimulator;

public class SuperMarketQueue extends LinkedQueue<Customer> {
    
    //will store queue's "name"
    private String queueName;
    // will store total items in the queue
    private int totalLineItems;
    
    public SuperMarketQueue(int lineNumber) {
        super();
        //names the Queue using a number
        queueName = "Line" + " " + lineNumber;
        //totalLineItems for an empty queueu
        totalLineItems = 0;
        
    }//end of constructor - creates an empty queue


    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setTotalLineItems(int totalLineItems) {
        this.totalLineItems = totalLineItems;
    }

    public int getTotalLineItems() {
        return totalLineItems;
    }

    // overides LinkedQueue.eqnqueue - updates totalLineItems upon enqueueing a customer
    public void enqueue(Customer newCustomer){
        totalLineItems += newCustomer.getTotalItems();
        super.enqueue(newCustomer);
    }
    
    //method that removes a number of items from the queue and removes customers if necessary
    public void processItems(int processedItems){
        //if this Line is not empty
        if(!this.isEmpty()){
            //case where the first customer has more items that the ones being processed
            if((this.first().getUnprocessedItems() - processedItems)>0){
                // updating first()'s unprocessedItems
                this.first().setUnprocessedItems(this.first().getUnprocessedItems() - processedItems);
                // updating totalLineItems
                this.totalLineItems = this.totalLineItems - processedItems; 
            }else{ 
                // case where the first customer has less or equal items to the ones being processed
                //updating items left to be processed
                processedItems = processedItems - this.first().getUnprocessedItems();
                //updating totalLineItems
                this.totalLineItems = this.totalLineItems - this.first().getUnprocessedItems();
                // displaying message the first Customer has left the queue
                System.out.println(this.first().getCustomerName() + " has been fully processed and left " + queueName);
                //removing Customer
                this.dequeue();
                //calling the same method recursively for the remainder of processedItems
                processItems(processedItems);
            }
        }
    }
    
    //method that removes one item from the queue, and updates it
    public void removeOneItem(){
        if(!this.isEmpty()){
            // removes 1 item from first customer
            this.first().setUnprocessedItems(this.first().getUnprocessedItems() - 1);
            // removes first Customer if his/her items have reached 0
            if(this.first().getUnprocessedItems()==0){
                // displaying message the first Customer has left the queue
                System.out.println(this.first().getCustomerName() + " has been fully processed and left " + queueName);
                //removing Customer
                this.dequeue();
            }
            //updates totalLineItems
            this.totalLineItems--;
        }
    }
    
    // method that removes an ammount of items from the queue using removeOneItem()
    // method does the same thing as processItems() less efficienty (more operations needed)
    public void processItems2(int processedItems){
        while(processedItems > 0 && !this.isEmpty()){
            removeOneItem();
            processedItems--;
        }
    }
    
    // method that displays information about the line(short)
    public void displayQueueShort(){
        if(this.isEmpty()){
            System.out.println(this.queueName + " is empty!");
        }else{
            System.out.println(this.queueName + " has " + this.size() + " customers with a total of " + this.totalLineItems + " items.");
        }
    }
    
    // method that displays all information of a line and its Customers
    public void displayQueue(){
        if(this.isEmpty()){
            System.out.println("\n" + this.queueName + " is empty!");
        }else{
            System.out.println("\n" + this.queueName + " has " + this.size() + " customers with a total of " + this.totalLineItems + " items.");
            System.out.println("------------------------------------------------");
            // method to display all customers in the queue starting from the first one
            returnAllCustomerInfo(this.getHead());
        }
    }
    
    // method to display all customers in the queue
    public void returnAllCustomerInfo(SinglyLinkedList.Node<Customer> currentNode){
        // displays the information of the Customer in the current Node
        System.out.println(currentNode.getElement().customerInfo());
        //method is called recursively on the next Node if there is one
        if(currentNode.getNext() != null){
            returnAllCustomerInfo(currentNode.getNext());
        }
    }
    

}// end of class
