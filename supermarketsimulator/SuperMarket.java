package supermarketsimulator;

import java.util.Scanner;

public class SuperMarket {
    
    // array that will hold the queues
    private SuperMarketQueue[] lines;
    //number of lines
    private int numOfLines;
    //number will be used for the creation of customers with random number of items
    private int maxNumOfItems;
    //number that indicates how many seconds are needed to processes 1 item
    private int secondsPerItem = 10;
    
    public SuperMarket(){
        // asks user how many lines there are in the SuperMarket
        numOfLines = askLines();
        // creates as many SuperMarketQueues
        createLines(numOfLines);
        //ask the user for the maximum number of items a customer may have
        //number will be used for the creation of customers with random number of items
        maxNumOfItems = askMaxNumOfItems();
        //add a random number of customers when starting the program(0-numberOfLines*3)
        // comment out line below if you want to start with empty queues
        addRandomCustomersStart();
        //loads the menu
        menu();
    }
    
    // method to create a specific number of lines, and initialize each SuperMarketQueue
    public void createLines(int numOfLines){
        lines = new SuperMarketQueue[numOfLines];
        // initializes the new queues and names them
        for(int i = 0; i < lines.length; i++){
            lines[i] = new SuperMarketQueue(i+1);
        }
    }
    
    //method that asks the user how many lines there are
    public int askLines(){
        int numOfLines = 0;
        do{
            System.out.println("How many lines does the supermarket have? ");
            Scanner input = new Scanner(System.in);
            if(input.hasNextInt()){
                numOfLines = input.nextInt();
                if(numOfLines<1){
                    System.out.println("Invalid input. Must be a positive number!");
                }
            }else{
                System.out.println("Invalid input. You must enter an integer!");
                numOfLines = 0;
            }
        }while(numOfLines < 1);
        //loop will persist untill user enters a positive integer 
        return numOfLines;
    }
    
    // asks the user what is the maximum number of items - number is used to create customers with random number of items
    public int askMaxNumOfItems(){
        int numOfLines = 0;
        do{
            System.out.println("What is the maximum number of items a customer may have? ");
            Scanner input = new Scanner(System.in);
            if(input.hasNextInt()){
                numOfLines = input.nextInt();
                if(numOfLines<1){
                    System.out.println("Invalid input. Must be a positive number!");
                }
            }else{
                System.out.println("Invalid input. You must enter an integer!");
                numOfLines = 0;
            }
        }while(numOfLines < 1);
        //loop will persist untill user enters a positive integer 
        return numOfLines;
    }
    
    //method that adds a new Customer to the lines
    public String addCustomer(Customer newCustomer){
        // number of line to join
        int lineIndex = 0;
        // number of minimum items
        int minItems = lines[0].getTotalLineItems();
        for(int i =0; i<lines.length; i++){
            //case of an empty line customer is added and loop is stopped
            if(lines[i].isEmpty()){
                lineIndex = i;
                i = lines.length;
            }else{
                // looks for the line with the minimum items
                if(minItems>lines[i].getTotalLineItems()){
                    minItems = lines[i].getTotalLineItems();
                    lineIndex = i;
                }
            }
        }
        //adds the Customer to the line with the fewer total items 
        lines[lineIndex].enqueue(newCustomer);
        //returns a String to inform which line the customer joined
        return newCustomer.getCustomerName() + " joined " + lines[lineIndex].getQueueName();
    }
    
    //adds a number of customers to the lines
    public void addCustomers(int numOfCustomers){
        for( ; numOfCustomers>0; numOfCustomers--){
            // creates a new customer
            Customer newCustomer = new Customer(maxNumOfItems);
            // displays the new Customer information
            System.out.println(newCustomer.displayNewCustomer());
            // adds the new Customer to the appropriate line
            System.out.println(this.addCustomer(newCustomer));
        }
    }
    
    //displays the information of all lines(short version)
    public void displayLines(){
        for(int i = 0; i<lines.length; i++){
            lines[i].displayQueueShort();
        }
    }
    
    //displays the information of all lines(full version)
    public void displayLinesFull(){
        for(int i = 0; i<lines.length; i++){
            lines[i].displayQueue();
        }
    }
    
    //method that progresses time for time seconds
    public void progressTime(int time){
        for(int i=0; i<lines.length; i++){
            lines[i].processItems(time/secondsPerItem);
        }
    }
    
    //method that progresses time for 1 minute
    public void oneMinute(){
        progressTime(60);
    }
    
    //method tha progresses time for 3 minutes
    public void threeMinutes(){
        progressTime(180);
    }
    
    //method that adds a new Customer
    public void addOneCustomer(){
        addCustomers(1);
    }
    
    //method that adds a random number of Customers between 0 and the number of lines
    public void addRandomCustomers(){
        int numOfCustomers = (int) (Math.random()*(numOfLines +1));
        //System.out.println(numOfCustomers);
        addCustomers(numOfCustomers);
    }
    
    //method that adds a random number of Customers between 0 and 3*number of lines
    public void addRandomCustomersStart(){
        int numOfCustomers = (int) (Math.random()*(numOfLines +1)*3);
        //System.out.println(numOfCustomers);
        addCustomers(numOfCustomers);
    }
    
    //menu
    public void menu(){
        int option = 0;
        do{
            System.out.println("\nPlease choose one of the following options: \n" +
                "1. Add one new customer\n" +
                "2. Add 0-" + numOfLines + " new Customers\n" +
                "3. Progress time 1 minute\n" +
                "4. Progress times 3 minutes\n" +
                "5. Progress time 1 minute and a new customer might be added every 10 seconds\n" +
                "6. Display each line's information\n" +
                "7. Display information about each line and its customers\n" +
                "8. Exit");
            Scanner input = new Scanner(System.in);
            if(input.hasNextInt()){
                option = input.nextInt();
                switch(option){
                // adds 1 new customer
                case 1: addOneCustomer();
                    break;
                // adds 0 - number of lines new customers
                case 2: addRandomCustomers();
                    break;
                // progresses time 1 minute
                case 3: oneMinute();
                    break;
                //progresses time 3 minutes
                case 4: threeMinutes();
                    break;
                //progresses time for 1 minute and might add a customer every 10 seconds
                case 5: timeAndCustomer();
                    break;
                // displays information about each line
                case 6: displayLines();
                    break;
                // displays information of each line and customer
                case 7: displayLinesFull();
                    break;
                // exits program
                case 8: System.out.println("Goodbye");
                    break;
                // displays a message of wrong input
                default: System.out.println("Invalid option. Please select a number between 1-7.");
                            
                }// end of switch
            }else{ //case of non integer input
                System.out.println("Invalid input. Please select a number between 1-7.");
            }
        }while(option != 8); //loop will persist untill exit is selected
    }
    
    //method that adds or not randomly a customer every 10 seconds and progresses time by 1 minute
    public void timeAndCustomer(){
        //6 for 60 seconds
        for(int i = 0; i < 6; i++){
            //rolls either 1 or 2
            int random = (int) (Math.random()*2 + 1);
            // progresses time 10 seconds
            progressTime(10);
            // if 2 adds a customer
            if(random == 2){
                addOneCustomer();
            }
        }
    }
    
}// end of class
