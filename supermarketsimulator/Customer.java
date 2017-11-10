package supermarketsimulator;

public class Customer {
    // the total number of items the customer has
    private int totalItems;
    // a static value which will be used for "naming the customers"
    private static int customerNum = 1;
    // number of items that will change after items are processed
    private int unprocessedItems;
    // "name" of the customer
    private String customerName;
    
    // constructor that creates a new Customer with a random number of items no more than maxItems
    public Customer(int maxItems){
        //creates a name for the customer
        customerName = "Customer " + customerNum;
        // increases counter so that the next customer is +1
        customerNum++;
        // sets a random item number in the customer ranging between 1-maxItems parameter
        totalItems = (int) (Math.random() * (maxItems) + 1); // +1 since a custommer with 0 items will not join the queue
        // sets unprocessed items equal to total customer items
        unprocessedItems = totalItems;
    }// end of constructor #1


    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setUnprocessedItems(int unprocessedItems) {
        this.unprocessedItems = unprocessedItems;
    }

    public int getUnprocessedItems() {
        return unprocessedItems;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public String displayNewCustomer() {
        return this.customerName + " created carrying " + this.totalItems + " items!"; 
    }// to be displayed upon creating a new customer
    
    public String customerInfo() {
        return this.customerName + " has " + this.unprocessedItems + " out of " + this.totalItems + " items to be processed.";
    }// to be displayed upon request by the queue

}// end of class