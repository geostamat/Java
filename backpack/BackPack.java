package backpack;

import java.io.File;

import java.util.ArrayList;
import java.util.Scanner;

public class BackPack {
    
    // array that will contain the weights
    private double[] weights;
    //solution counter
    private long solutionCounter = 0;
    //recursion counter
    private long recursionCounter = 0;
    // holds the backpack size
    private double backpackSize = 0;
    // counts the mistaken solutions
    private long errorCounter = 0;
    // Get the Java runtime
    private Runtime runtime = Runtime.getRuntime();
    
    public BackPack(){
        
        // asks the user how to get the weights(from file or manually)
        if(menu()){
            // displays the contents of the weights array
            // displayWeights(); // uncomment this line if you want to see the weights
            //asks the user for the backpack size and assigns the result
            backpackSize = backpackSize();
            //asks the user which algorithm to execute
            algorithmMenu();
            
        }
        
    }// end of constructor
    
    
    //method that reads the weights from a file and returns an ArrayList of them
    public ArrayList<Double> readFile(String textFile) throws Exception {
        File inFile = new File(textFile);
        Scanner input = new Scanner(inFile);
        ArrayList<Double> weightsList = new ArrayList<Double>();
        while(input.hasNextLine()){
            String in = input.nextLine();
            double weight = Double.parseDouble(in);
            weightsList.add(weight);
            }
        input.close();
        return weightsList;
    }
    
    //method that reads an ArrayList of doubles and loads them in weights array (in order to use the simplest fo structures)
    public void fillArray(ArrayList<Double> weightsList){
        double[] newWeights = new double[weightsList.size()];
        for(int i = 0; i < weightsList.size(); i++){
            newWeights[i] = weightsList.get(i);
        }
        weights= newWeights;
    }
    
    //method that asks the user for file to read from and handles exception
    public boolean readFromFile(){
        boolean success = false;
        boolean check = false;
        Scanner input = new Scanner(System.in);
        do{
            System.out.print("Please enter file name to read from or type \"cancel\" to exit: ");
            
            String in = input.next();
            
            if(in.equalsIgnoreCase("cancel")){
                check=false;
                success = false;
            }else{
                try {
                    ArrayList<Double> weightsList = readFile(in);
                    fillArray(weightsList);
                    check = false;
                    success = true;
                } catch (Exception e) {
                    System.out.println("Error reading file!");
                    System.out.println(e.toString());
                    check = true;
                }
            }
        }while(check); // will persist till a correct file is given or cancel is typed
        // input.close();
        return success;
    }
    
    //method that displays the contents of weights array
    public void displayWeights(){
        for(int i = 0; i<weights.length; i++){
            System.out.println(weights[i]);
        }
    }
    
    //method that adds a double to an ArrayList of doubles from keyboard
    public String manualWeights(ArrayList<Double> weightsList){
        String message;
        System.out.print("Enter weight value: ");
        Scanner input = new Scanner(System.in);
        if(input.hasNextDouble()){
            weightsList.add(input.nextDouble());
            message = "Weight added succesfully";
        }else{
            message = "Invalid input! Must be a number!";
        }
        return message;
    }
    
    // method that asks the user to add weights manually
    public boolean manualEnter(){
        int option =0;
        // will return true only if an array of numbers has been successfuly added
        boolean success = false;
        ArrayList<Double> weightsList = new ArrayList<Double>();
        do{
            System.out.println("\n1. Add a weight" +
                                "\n2. Done");
            Scanner input = new Scanner(System.in);
            if(input.hasNextInt()){
                option = input.nextInt();
                switch(option){
                    case 1: System.out.println(manualWeights(weightsList));
                        break;
                    case 2: success = true;
                        break;
                    default: System.out.println("Invalid option!");
                }
            }else{
                System.out.println("Invalid input. Please select 1 or 2!");
            }
        }while(option!=2);
        //if no weight has been added
        if(weightsList.size()==0){
            success = false;
            System.out.println("No weights have been added!");
        }else{
            // fills array weights
            fillArray(weightsList);
            System.out.println("Weights successfully added!");
        }
        return success;
    }
    
    // menu for weights input
    public boolean menu(){
        int option = 0;
        boolean success = false;
        do{
            System.out.println("\nWhat would you like to do?" + 
                               "\n1. Add weights manualy" +
                               "\n2. Read from file" +
                               "\n3. Cancel");
            Scanner input = new Scanner(System.in);
            if(input.hasNextInt()){
                option = input.nextInt();
                switch(option){
                    case 1: success = manualEnter();
                        break;
                    case 2: success = readFromFile();
                        break;
                    case 3: success = false;
                        break;
                    default: System.out.println("Invalid option! Please select a number between 1-3");
                }
            }else{
                System.out.println("Invalid input. Please select an option 1-3!");
            }
        }while(option != 3 && !success); //loop will persist untill either cancel is selected or weights have been successfully added
        return success;
    }
    
    //method that searchs recursively for solutions from starting from a source index (does not produce dublicate solutions)
    public void backpack(double remainderBackPackWeight, int index, ArrayList<Double> solution){
        // increase recursion counter
        recursionCounter++;
        // set the remaining weight
        remainderBackPackWeight = remainderBackPackWeight - weights[index];
        //System.out.println("curentWeight: " + weights[index]); // used for debugging
        // create a copy of the solution array
        ArrayList<Double> newSolution = copyList(solution);
        // add current number to solution array
        newSolution.add(weights[index]);
        //System.out.println("remainder: " + remainderBackPackWeight); //used for debugging
        //printSolution(newSolution); //used for debugging
        //if a solution has been achieved
        if(remainderBackPackWeight == 0 || (remainderBackPackWeight < 0.0001 && remainderBackPackWeight > -0.0001)){
            // display solution
            printSolution(newSolution);
            // increase solution counter
            solutionCounter++;
        }else{
            // if no solution has been reached or surpassed
            if(remainderBackPackWeight > 0){
                //for each for the rest of the numbers in the array
                for(int i = index +1; i < weights.length; i++){
                    // execute backpack recursively
                    backpack(remainderBackPackWeight, i, newSolution);
                }
            }
        }//else recursion ends (base case #2) - no solution, and weight surpassed
    }
    
    //method that runs the backpack function on every number in the array and finds unique only solutions
    public void findAllSolutions(double backpackSize){
        // Run the garbage collector to clear whatever unused objects are still in memeory
        runtime.gc();
        // stores the free memory before the algorithm is run
        long freeMemoryStart = runtime.freeMemory();
        //time point where the algorithm begins
        long startTime = System.currentTimeMillis();
        //cuurent time stored in nanoseconds
        long startTimeNano = System.nanoTime();
        // array to be used for storing the "paths" to solution
        ArrayList<Double> solution = new ArrayList<Double>();
        // execute the backpack function for all items in the array
        for(int i = 0; i < weights.length; i++){
            backpack(backpackSize, i, solution);
        }
        // difference in free memory at the end of the algorithm
        long freeMemoryEnd = freeMemoryStart - runtime.freeMemory(); 
        //time point where algorithm ends in nanoseconds
        long totalTimeNano = System.nanoTime() - startTimeNano;
        //time point where the algorithm ends in miliseconds
        long totalTime = System.currentTimeMillis()-startTime;
        // displays the difference in free Memory between before an after the algorithm execution
        System.out.println("Memory used in bytes: " + (freeMemoryEnd) + " (usually not accurate due to unknown points of garbage collection execution)");
        // estimation of memory usage n * ((a new ArrayList of doubles of average size n/2) * 8 bytes + (index, i) * 4 bytes + (remainderBackPack double)* 8 bytes) at the "peak" of the recursion
        System.out.println("Estimated Memory Usage: " +  weights.length * ((((weights.length)/2.00)*8) + 2 * 4 + 8) + " bytes");
        //printout of the total times of the algorithm execution
        if(totalTimeNano > 10000000){
            System.out.println("Total time: " + (totalTime) + " miliseconds (" + totalTime/1000.00 + " seconds)");
        }else{
            System.out.println("Total time: " + (totalTimeNano) + " nanoseconds");
        }
        // reports the times the recursion was executed
        System.out.println("Recursion executed " + recursionCounter + " times.");
        // reports how many solutions were found
        if(solutionCounter > 0){
            System.out.println("Solutions found: " + solutionCounter);
            // reports mistaken solutions
            System.out.println("Wrong solutions: " + errorCounter);
        }else{
            System.out.println("No solutions found!");
        }
        // resets the error counter
        errorCounter = 0;
        // resets the solution counter
        solutionCounter = 0;
        // resets the recursion counter
        recursionCounter = 0;
    }
    
    //method that prints the solutions (displays and verifies the solutions by readding them)
    public void printSolution(ArrayList<Double> solution){
        for(int i = 0; i<solution.size(); i++){
            if(i==solution.size()-1){
                System.out.println(solution.get(i) + " = " + sum(solution) + "\n"); // sum is recalculated for verification
            }else{
                System.out.print(solution.get(i) + " + ");
            }
        }
    }
    
    //method that sums an array of doubles (used for verification of answers)
    public String sum(ArrayList<Double> solution){
        double sum = 0;
        for(int i = 0; i < solution.size(); i++){
            sum += solution.get(i);
        }
        if(sum == backpackSize){
            return sum + " check";
        }else{
            errorCounter++;
            return sum + " error";
        }
    }
    
    //method that creates a copy of an array of doubles (used to create a copy of the "path" at every point of the recursion)
    public ArrayList<Double> copyList(ArrayList<Double> oldArray){
        ArrayList<Double> newArray = new ArrayList<Double>();
        for(int i = 0; i<oldArray.size(); i++){
            newArray.add(oldArray.get(i));
        }
        return newArray;
    }
    
    //method that copies and array of booleans (used in creating a copy of the unvisited array at every point of the recursion)
    public boolean[] copyArray(boolean[] indexArray){
        boolean[] newIndexArray = new boolean[indexArray.length];
        for(int i = 0; i<indexArray.length; i++){
            newIndexArray[i] = indexArray[i];
        }
        return newIndexArray;
    }
    
    //method that fills an array of booleans with true (used for the "unvisited" array)
    public boolean[] fillBooleanArray(boolean[] booleanArray){
        for(int i = 0; i<booleanArray.length; i++){
            booleanArray[i] = true;
        }
        return booleanArray;
    }
    
    // method that calculates all possible combinations of numbers for each number
    public void backpack(double remainderBackPackWeight, int index, ArrayList<Double> solution, boolean[] indexArray){
        // increases the recursion counter
        recursionCounter++;
        // updates the remainderWeight
        remainderBackPackWeight = remainderBackPackWeight - weights[index];
        //System.out.println("curentWeight: " + weights[index]); // used for debugging purposes
        //creates a copyt of the unvisited numbers array
        boolean[] newIndexArray = copyArray(indexArray);
        //sets current index as visited
        newIndexArray[index] = false;
        //creates a copy of the solution array list
        ArrayList<Double> newSolution = copyList(solution);
        //adds the current weight in the solution array
        newSolution.add(weights[index]);
        //printSolution(newSolution); //used for debugging
        //System.out.println("remainder: " + remainderBackPackWeight); // used for debugging purposes
        //prints the solution if one is found, and updates the solution counter //base case #1
        if(remainderBackPackWeight == 0 || (remainderBackPackWeight < 0.0001 && remainderBackPackWeight > -0.0001)){
            printSolution(newSolution);
            solutionCounter++;
        }else{
            // if solution has not been found AND the backpack size has not been surpassed
            if(remainderBackPackWeight > 0){
                // executes the backpack function on all remaining numbers
                for(int i = 0; i < weights.length; i++){
                    // that have not been "visited" already
                    if(newIndexArray[i]){
                        backpack(remainderBackPackWeight, i, newSolution, newIndexArray);
                    }
                }
            }// else recursion ends (base case #2) - no solution found AND weight surpassed
        }
    }
    
    //method that runs the backpack function (which considers a + b diffrent than b + a) on every number in the array
    public void findAllCombinations(double backpackSize){
        // Run the garbage collector to clear whatever unused objects are still in memeory
        runtime.gc();
        // stores the free memory before the algorithm is run
        long freeMemoryStart = runtime.freeMemory();
        //time point where the algorithm begins in milliseconds
        long startTime = System.currentTimeMillis();
        //cuurent time stored in nanoseconds
        long startTimeNano = System.nanoTime();
        // array to store the numbers as the recursion is executed
        ArrayList<Double> solution = new ArrayList<Double>();
        // boolean array that will hold the "unvisited" numbers
        boolean[] indexArray = new boolean[weights.length];
        // executes the backpack function on every number in the array
        for(int i = 0; i < weights.length; i++){
            // resets the "unvisited" array to all true
            indexArray = fillBooleanArray(indexArray);
            backpack(backpackSize, i, solution, indexArray);
        }
        //free memory at the end
        long freeMemoryEnd = freeMemoryStart - runtime.freeMemory();
        //time point where algorithm ends in nanoseconds
        long totalTimeNano = System.nanoTime() - startTimeNano;
        //time point where the algorithm ends in milliseconds
        long totalTime = System.currentTimeMillis()-startTime;
        // displays the difference in free Memory between before an after the algorithm execution
        System.out.println("Memory used in bytes: " + (freeMemoryEnd) + " (usually not accurate due to unknown points of garbage collection execution)");
        // estimation of memory usage n * ((a new ArrayList of doubles of average size n/2) * 8 bytes + (index, i) * 4 bytes) + (a new array of n booleans) * 2bytes + (remainderBackPackSize) * 8 bytes) at the "peak" of the recursion
        System.out.println("Estimated Memory Usage: " +  weights.length * ((((weights.length)/2.00)*8) + 2*4 + weights.length*2 + 8) + " bytes");
        //printout of the total times of the algorithm execution
        if(totalTimeNano > 10000000){
            System.out.println("Total time: " + (totalTime) + " miliseconds (" + totalTime/1000.00 + " seconds)");
        }else{
            System.out.println("Total time: " + (totalTimeNano) + " nanoseconds");
        }
        // times the recursion was executed
        System.out.println("Recursion executed " + recursionCounter + " times.");
        // reports how many solutions were found
        if(solutionCounter > 0){
            System.out.println("Solutions found: " + solutionCounter);
            // reports mistaken solutions
            System.out.println("Wrong solutions: " + errorCounter);
        }else{
            System.out.println("No solutions found!");
        }
        // resets the error counter
        errorCounter = 0;
        // resets the solution counter
        solutionCounter = 0;
        // resets the recursion counter
        recursionCounter = 0;
    }
    
    // method that asks the user for the size of the backpack
    public double backpackSize(){
        double backpackSize = 0;
        do{
            System.out.println("What is the size of the backpack?");
            Scanner input = new Scanner(System.in);
            if(input.hasNextDouble()){
                backpackSize = input.nextDouble();
                if(backpackSize <= 0){
                    System.out.println("Error! Backpack size must be a positive number greater than zero!");
                }
            }else{
                System.out.println("Error! You must enter a number!");
            }
        }while(backpackSize <= 0);
        return backpackSize;
    }
    
    //method that asks the user which algorithm to use
    public void algorithmMenu(){
        int option = 0;
        do{
            System.out.println("\nPlease choose one of the following options: " +
                               "\n1. Calculate and display all possible solution combinations." +
                               "\n2. Calculate and display unique only solutions." +
                               "\n3. Find one solution only." +
                               "\n4. Calculate and display all possible solution combinations.(inefficient method, creating copy of structures)" +
                               "\n5. Calculate and display unique only solutions. (inefficient method, creating copy of structures)" +
                               "\n6. Add new weights." +
                               "\n7. Display current weights." + 
                               "\n8. Change Backpack Size - current size: " + backpackSize +
                               "\n9. Exit");
            Scanner input = new Scanner(System.in);
            if(input.hasNextInt()){
                option = input.nextInt();
                switch(option){
                    case 1: findAllCombinationsImproved(backpackSize);
                        break;
                    case 2: findAllSolutionsImproved(backpackSize);
                        break;
                    case 3: oneSolution(backpackSize);
                        break;
                    case 4: findAllCombinations(backpackSize);
                        break;
                    case 5: findAllSolutions(backpackSize);
                        break;
                    case 6: newWeights();
                        break;
                    case 7: displayWeights();
                        break;
                    case 8: changeBackpackSize();
                        break;
                    case 9: System.out.println("Goodbye");
                        break;
                    default: System.out.println("Invalid option! Please select a number between 1-9");
                }
            }else{
                System.out.println("Invalid option! Please select a number between 1-9");
            }
        }while(option!=9);
    }
    
    // method that stops at the first solution - returns true is a solution has been found
    public boolean backpack(ArrayList<Double> solution, double remainderBackPackWeight, int index){
        //updates the recursion counter
        recursionCounter++;
        // boolean that will be returned
        boolean success = false;
        // sets the remaining backpack weight
        remainderBackPackWeight = remainderBackPackWeight - weights[index];
        // base case #1 where a solution is found
        if(remainderBackPackWeight == 0 || (remainderBackPackWeight < 0.0001 && remainderBackPackWeight > -0.0001)){
            //set boolean to true
            success = true;
            //updates the solution counter
            solutionCounter++;
        }else{
            // case where the backpack weight has not been achieved
            if(remainderBackPackWeight > 0){
                // method is executed recursively on the weights remaining after current
                for(int i = index+1 ; i<weights.length; i++){
                    //if a solution is found
                    if(backpack(solution, remainderBackPackWeight, i)){
                        // break loop
                        i  = weights.length;
                        // set boolean to true
                        success = true;
                    }
                }
            }// else base case #2 where no solution has been found boolean remains false
        }
        // if a solution has been found
        if(success){
            // add current number to the array list of solution
            solution.add(weights[index]);
        }
        return success;
    }
    
    //method that applies the above method on all the weights in the array
    public void oneSolution(double backpackSize){
        // Run the garbage collector to clear whatever unused objects are still in memeory
        runtime.gc();
        // stores the free memory before the algorithm is run
        long freeMemoryStart = runtime.freeMemory();
        //time point where the algorithm begins
        long startTime = System.currentTimeMillis();
        //cuurent time stored in nanoseconds
        long startTimeNano = System.nanoTime();
        // array that will carry the solution
        ArrayList<Double> solution = new ArrayList<Double>();
        for(int i = 0; i<weights.length; i++){
            // if a solution is found the loop stops
            if(backpack(solution, backpackSize,i)){
                i = weights.length;
                System.out.println();
            }
        }
        //the solution is displayed
        printSolution(solution);
        //difference of free memory at the end
        long freeMemoryEnd = freeMemoryStart - runtime.freeMemory();
        //time point where algorithm ends in nanoseconds
        long totalTimeNano = System.nanoTime() - startTimeNano;
        //time point where the algorithm ends
        long totalTime = System.currentTimeMillis()-startTime;
        // displays the difference in free Memory between before an after the algorithm execution
        System.out.println("Memory used in bytes: " + (freeMemoryEnd) + " (usually not accurate due to unknown points of garbage collection execution)");
        // estimation of memory usage n * ((a new boolean) * 2 bytes + (an ArrayList of doubles with n elements at maximum) * 8 bytes + (int index, i) * 4 bytes + (remainderBP size double) * 8 bytes) at the "peak" of the recursion
        System.out.println("Estimated Maximum Memory Usage: " +  weights.length * (2 + 8 + 2*4 + 8) + " bytes");
        //printout of the total times of the algorithm execution
        if(totalTimeNano > 10000000){
            System.out.println("Total time: " + (totalTime) + " miliseconds (" + totalTime/1000.00 + " seconds)");
        }else{
            System.out.println("Total time: " + (totalTimeNano) + " nanoseconds");
        }
        // times the recursion was executed
        System.out.println("Recursion executed " + recursionCounter + " times.");
        // reports how many solutions were found
        if(solutionCounter > 0){
            System.out.println("Solutions found: " + solutionCounter);
            // reports mistaken solutions
            System.out.println("Wrong solutions: " + errorCounter);
        }else{
            System.out.println("No solutions found!");
        }
        // resets the error counter
        errorCounter = 0;
        // resets the solution counter
        solutionCounter = 0;
        // resets the recursion counter
        recursionCounter = 0;
    }
    
    // method that calculates all possible combinations of numbers for each number (improved version)
    public void backpackImproved(double remainderBackPackWeight, int index, ArrayList<Double> solution, boolean[] indexArray){
        // increases the recursion counter
        recursionCounter++;
        // updates the remainderWeight
        remainderBackPackWeight = remainderBackPackWeight - weights[index];
        //System.out.println("curentWeight: " + weights[index]); // used for debugging purposes
        //sets current index as visited
        indexArray[index] = false;
        //adds the current weight in the solution array
        solution.add(weights[index]);
        //printSolution(solution); //used for debugging
        //System.out.println("remainder: " + remainderBackPackWeight); // used for debugging purposes
        //prints the solution if one is found, and updates the solution counter //base case #1
        if(remainderBackPackWeight == 0 || (remainderBackPackWeight < 0.0001 && remainderBackPackWeight > -0.0001)){
            printSolution(solution);
            solutionCounter++;
        }else{
            // if solution has not been found AND the backpack size has not been surpassed
            if(remainderBackPackWeight > 0){
                // executes the backpack function on all remaining numbers
                for(int i = 0; i < weights.length; i++){
                    // that have not been "visited" already
                    if(indexArray[i]){
                        backpackImproved(remainderBackPackWeight, i, solution, indexArray);
                    }
                }
            }// else if no remaining weight<0  and no solution is found recursion ends (base case #2)
        }
        // before the current recursion ends
        // sets current number back to visited
        indexArray[index] = true;
        // removes current number (last number added) from the solution array
        solution.remove(solution.size()-1);
        
    }
    
    //method that runs the above improved backpack function (which considers a + b diffrent than b + a) on every number in the array
    public void findAllCombinationsImproved(double backpackSize){
        // Run the garbage collector to clear whatever unused objects are still in memeory
        runtime.gc();
        // stores the free memory before the algorithm is run
        long freeMemoryStart = runtime.freeMemory();
        //time point where the algorithm begins
        long startTime = System.currentTimeMillis();
        //cuurent time stored in nanoseconds
        long startTimeNano = System.nanoTime();
        // array to store the numbers as the recursion is executed
        ArrayList<Double> solution = new ArrayList<Double>();
        // boolean array that will hold the "unvisited" numbers
        boolean[] indexArray = new boolean[weights.length];
        // executes the backpack function on every number in the array
        for(int i = 0; i < weights.length; i++){
            // resets the "unvisited" array to all true
            indexArray = fillBooleanArray(indexArray);
            backpackImproved(backpackSize, i, solution, indexArray);
        }
        // difference of free memory at the end
        long freeMemoryEnd = freeMemoryStart - runtime.freeMemory();
        //time point where algorithm ends in nanoseconds
        long totalTimeNano = System.nanoTime() - startTimeNano;
        //time point where the algorithm ends in miliseconds
        long totalTime = System.currentTimeMillis()-startTime;
        // displays the difference in free Memory between before an after the algorithm execution
        System.out.println("Memory used in bytes: " + (freeMemoryEnd) + " (usually not accurate due to unknown points of garbage collection execution)");
        // estimation of memory usage n * ((an ArrayList of doubles with n elements at maximum) * 8 + (an array of n booleans)*2 + (int index, i) * 4 + (remainder BP size double) * 8) at the "peak" of the recursion
        System.out.println("Estimated Maximum Memory Usage: " +  weights.length * (8 + 2 + 2 * 4 + 8) + " bytes");
        //printout of the total times of the algorithm execution
        if(totalTimeNano > 10000000){
            System.out.println("Total time: " + (totalTime) + " miliseconds (" + totalTime/1000.00 + " seconds)");
        }else{
            System.out.println("Total time: " + (totalTimeNano) + " nanoseconds");
        }
        // times the recursion was executed
        System.out.println("Recursion executed " + recursionCounter + " times.");
        // reports how many solutions were found
        if(solutionCounter > 0){
            System.out.println("Solutions found: " + solutionCounter);
            // reports mistaken solutions
            System.out.println("Wrong solutions: " + errorCounter);
        }else{
            System.out.println("No solutions found!");
        }
        // resets the error counter
        errorCounter = 0;
        // resets the solution counter
        solutionCounter = 0;
        // resets the recursion counter
        recursionCounter = 0;
    }
    
    //method that searchs recursively for solutions from starting from a source index (does not produce dublicate solutions)
    public void backpackImproved(double remainderBackPackWeight, int index, ArrayList<Double> solution){
        // increase recursion counter
        recursionCounter++;
        // set the remaining weight
        remainderBackPackWeight = remainderBackPackWeight - weights[index];
        //System.out.println("curentWeight: " + weights[index]); // used for debugging
        // add current number to solution array
        solution.add(weights[index]);
        //System.out.println("remainder: " + remainderBackPackWeight); //used for debugging
        //printSolution(solution); //used for debugging
        //if a solution has been achieved (base case #1)
        if(remainderBackPackWeight == 0 || (remainderBackPackWeight < 0.0001 && remainderBackPackWeight > -0.0001)){
            // display solution
            printSolution(solution);
            // increase solution counter
            solutionCounter++;
        }else{
            // if no solution has been reached or surpassed
            if(remainderBackPackWeight > 0){
                //for each for the rest of the numbers in the array
                for(int i = index +1; i < weights.length; i++){
                    // execute backpack recursively
                    backpackImproved(remainderBackPackWeight, i, solution);
                }
            }// else if no solution has been found and remainderBackPack weight is less than 0, recursion ends (base case #2)
        }
        // current number is removed from the array of the solution (being the last)
        solution.remove(solution.size() - 1);
    }
    
    //method that runs the above backpackImproved function on every number in the array and finds unique only solutions
    public void findAllSolutionsImproved(double backpackSize){
        // Run the garbage collector to clear whatever unused objects are still in memeory
        runtime.gc();
        // stores the free memory before the algorithm is run
        long freeMemoryStart = runtime.freeMemory();
        //time point where the algorithm begins
        long startTime = System.currentTimeMillis();
        //cuurent time stored in nanoseconds
        long startTimeNano = System.nanoTime();
        // array to be used for storing the "paths" to solution
        ArrayList<Double> solution = new ArrayList<Double>();
        // execute backpackImproved on every number in the array 
        for(int i = 0; i < weights.length; i++){
            backpackImproved(backpackSize, i, solution);
        }
        //difference of free memory at the end of the algorithm
        long freeMemoryEnd = freeMemoryStart - runtime.freeMemory();
        //time point where algorithm ends in nanoseconds
        long totalTimeNano = System.nanoTime() - startTimeNano;
        //time point where the algorithm ends in miliseconds
        long totalTime = System.currentTimeMillis()-startTime;
        // displays the difference in free Memory between before an after the algorithm execution
        System.out.println("Memory used in bytes: " + (freeMemoryEnd) + " (usually not accurate due to unknown points of garbage collection execution)");
        // estimation of memory usage n * ((an ArrayList of doubles with n elements at maximum) * 8 + (int index, i) * 4 + (remainderBP size) * 8) at the "peak" of the recursion
        System.out.println("Estimated Maximum Memory Usage: " +  weights.length * (8 + 2*4 + 8) + " bytes");
        //printout of the total times of the algorithm execution
        if(totalTimeNano > 10000000){
            System.out.println("Total time: " + (totalTime) + " miliseconds (" + totalTime/1000.00 + " seconds)");
        }else{
            System.out.println("Total time: " + (totalTimeNano) + " nanoseconds");
        }
        // reports the times the recursion was executed
        System.out.println("Recursion executed " + recursionCounter + " times.");
        // reports how many solutions were found
        if(solutionCounter > 0){
            System.out.println("Solutions found: " + solutionCounter);
            // reports mistaken solutions
            System.out.println("Wrong solutions: " + errorCounter);
        }else{
            System.out.println("No solutions found!");
        }
        // resets the error counter
        errorCounter = 0;
        // resets the solution counter
        solutionCounter = 0;
        // resets the recursion counter
        recursionCounter = 0;
    }
    
    //method that asks the use for new weights
    public void newWeights(){
        //keeps current weights in case of failure
        double[] currentArray = weights;
        //if adding new weights fails weights are restored
        if(!menu()){
            weights = currentArray;
        }
    }
    
    // method that sets a new backpack size
    public void changeBackpackSize(){
        backpackSize = backpackSize();
    }

    
}// end of class


