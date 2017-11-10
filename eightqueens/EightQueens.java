package eightqueens;

import java.util.ArrayList;
import java.util.Scanner;

public class EightQueens {
    
    private static class Tile{
        
        private boolean queen;
        private int conflicts;
        
        private Tile(){
            queen = false;
            conflicts = 0;
        }// end of Tile Constructor
        
        public boolean getQueen(){
            return this.queen;
        }
        public int getConficts(){
            return this.conflicts;
        }
        public void setQueen(boolean queen){
            this.queen = queen;
        }
        public void setConflicts(int conflicts){
            this.conflicts = conflicts;
        }
        public void incrementConficts(){
            this.conflicts++;
        }
    }//end of Tile class
    
    //chessboard size
    private final int size = 8;
    //chessboard declaration
    private Tile[][] board = new Tile[size][size];
    //assistant queen confict array
    private int[] queenConflicts = new int[size];
    //probability for random move of algorithm2
    private final double randomMove = 0.15;
    //max moves for initialAlgorithm method
    private final int maxMoves = 500;
    //max moves without progress for algorithm1
    private final int maxMovesWithoutProgress = 500;
    //probability for random move of algorithm2 in case of no better move
    private final double randomMoveWOProgress = 0.90;
    
    public EightQueens(){
        menu();
    }//end of constructor
    
    //chessboard initialization
    public void boardInit(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                board[i][j] = new Tile();
            }
        }

    }
    
    //random placement of the queens (random row, one in each column)
    public void placeQueens(){
        for(int i=0; i<size; i++){
            int position = (int) (Math.random()*8);
            board[i][position].setQueen(true);
        }
    }
    
    //display board and Queens' conflicts method
    public void displayBoard(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(board[j][i].getQueen()){
                    System.out.print("Q ");
                }else{
                    System.out.print(board[j][i].getConficts()+ " ");
                }
            }
            System.out.println();
        }
        System.out.println();
        for(int i=0; i<size; i++){
            System.out.print(queenConflicts[i] + " ");
        }
        System.out.println("\"Queens' conflicts\"" + "\n");
    }
    
    //method used to find the conficts
    public void findConficts(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(board[i][j].getQueen()){
                    //set conficts horizontally and vertically
                    for(int k=0; k<size; k++){
                        if(k!=j){
                            board[i][k].incrementConficts();
                        }
                        if(k!=i){
                            board[k][j].incrementConficts();
                        }
                    }
                    //set diagonal conficts "before" Queen's current position
                    int offset = 1;
                    for(int k=j-1; k>=0; k--){
                        if(i-offset>=0){
                            board[i-offset][k].incrementConficts();
                        }
                        if(i+offset<size){
                            board[i+offset][k].incrementConficts();
                        }
                        offset++;
                    }
                    //reset offset
                    offset = 1;
                    //set diagonal conficts "after" Queen's current position
                    for(int k=j+1; k<size; k++){
                        if(i-offset>=0){
                            board[i-offset][k].incrementConficts();
                        }
                        if(i+offset<size){
                            board[i+offset][k].incrementConficts();
                        }
                        offset++;
                    }
                }
            }
        }
    }
    
    //method that sets the queen conficts in the assistant array
    public void queenConflicts(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(board[i][j].getQueen()){
                    queenConflicts[i] = board[i][j].getConficts();
                }
            }
        }
    }
    
    //method that finds the index of the queen which has the most conflicts or 
    //randomly selects one (in case of same number of conflicts)
    public int findQueen(){
        //list that will hold the queen indices with the most conflicts
        ArrayList<Integer> queensToMove = new ArrayList<Integer>();
        //variable to hold the index of the queen with the most conflicts
        int queenIndex = 0;
        //loop to find the queen's index with the most conflicts
        for(int i=1; i<size; i++){
            if(queenConflicts[i]>queenConflicts[queenIndex]){
                queenIndex = i;
            }
        }
        //loop to add the other queens' indices with the same number of conflicts to the list
        for(int i=0; i<size; i++){
            if(queenConflicts[i] == queenConflicts[queenIndex]){
                queensToMove.add(i);
            }
        }
        //if only one queen return its index
        if(queensToMove.size()==1){
            return queensToMove.get(0);
        }else{ //if many choose one randomly
            queenIndex = (int) (Math.random()*queensToMove.size());
            return queensToMove.get(queenIndex);
        }
    }
    
    //method that takes the "column" index of the queen to move, and moves it vertically 
    //to the tile with the fewer conflicts (or randomly chooses if more than one exists)
    public void moveQueen(int index){
        //tile to move to
        int newTile;
        //temporary value of tile
        //if there is a queen at the first tile of this column
        if(board[index][0].getQueen()){
            //set the second as the one with the fewer conflicts
            newTile = 1;
        }else{ //if there is not set the first
            newTile = 0;
        }
        //tile queen is at (temporary value)
        int queenTile = 0;
        //find the tile with the fewer conflicts and the tile the queen is at
        for(int i=0; i<size; i++){
            //if there is no queen at that tile
            if(!board[index][i].getQueen()){
                //and it has fewer conflicts than the current one
                if(board[index][i].getConficts()<board[index][newTile].getConficts()){
                    //set this to be the Tile to move to
                    newTile = i;
                }
            }else{
                queenTile = i;
            }
        }
        //list that will hold the tiles with the fewer conflicts
        ArrayList<Integer> tilesToMove = new ArrayList<Integer>();
        //add the tiles with the fewer conflicts to list
        for(int i=0; i<size; i++){
            //if there is not queen at the tile and its conflicts are equal to the one that has the fewer ones
            if(!board[index][i].getQueen() && board[index][i].getConficts()==board[index][newTile].getConficts()){
                //add its index to the list
                tilesToMove.add(i);
            }
        }
        //if there is only one tile in the list
        if(tilesToMove.size()==1){
            //move the queen to that one
            board[index][queenTile].setQueen(false);
            board[index][tilesToMove.get(0)].setQueen(true);
        }else{ //if more than one tiles exist
            //choose one randomly
            newTile = (int) (Math.random()*tilesToMove.size());
            //and move the queen to that one
            board[index][queenTile].setQueen(false);
            board[index][tilesToMove.get(newTile)].setQueen(true);
        }
    }
    
    //method that returns true is a solution is found (if all queen conflicts are 0)
    public boolean solution(){
        int sum = queenConflicts[0];
        for(int i=1; i<size; i++){
            sum = sum + queenConflicts[i];
        }
        return sum == 0;
    }
    
    //method that returns the actual number of the sum of queen conficts (to be used to track progress)\
    public int sumQueenConflicts(){
        int sum = queenConflicts[0];
        for(int i=1; i<size; i++){
            sum = sum + queenConflicts[i];
        }
        return sum;
    }
    
    //method that resets the conflicts on the board
    public void resetBoard(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                board[i][j].setConflicts(0);
            }
        }
    }
    
    //Algorithm 1 - Moves the Queens with the most conflicts, to the tile with the least conflicts. 
    //It will restart the board with random positions if there is no progress after maxMovesWithoutProgress 
    //(defined at line 45) moves.
    //int parameter represents the number of games to be solved
    public void algorithmOne(int games){
        // counter will be increased when a game is solved
        int counter=0;
        // counter for the games where there is no progress
        int progressCounter=0;
        //sum of queen conflicts (to be used for chcking the progress per move
        int sumOfConflicts=0;
        //counter for the restarts (for total games)
        int restarts=0;
        //counter to count the restarts per game
        int restartsPerGame=0;
        //counter for the moves per games
        int moves=0;
        
        for(int i = 0; i<games; i++){
            System.out.println("Starting new game!");
            //creates a new board
            boardInit();
            //places queens in random positions
            placeQueens();
            //finds all conflicts on board
            findConficts();
            //fill the assistant array with the queen conflicts
            queenConflicts();
            //displays the initial board
            displayBoard();
            //boolean which changes to true if a solution is found
            boolean solution = solution();
            if(solution){
                System.out.println("Game already solved!");
                counter++;
            }
            //initialize track of sum
            sumOfConflicts = sumQueenConflicts();
            //while there is no solution
            while(!solution){
                int queenToMove = findQueen();
                System.out.println("Moving Queen in column " + queenToMove + "." + "\n");
                moveQueen(queenToMove);
                //increase the moves counter
                moves++;
                resetBoard();
                findConficts();
                queenConflicts();
                displayBoard();

                solution = solution();
                if(solution){
                    counter++;
                    System.out.println("Solution found after " + moves + " moves and " + restartsPerGame 
                                       + " restarts." + "\n");
                    moves=0;
                    restartsPerGame=0;
                    progressCounter=0;
                }else{
                    //debugging
                    //System.out.println("Sum Of Conflicts = " + sumOfConflicts);
                    //System.out.println("sumoQueenConflicts() = " + sumQueenConflicts() +"\n");
                    //if there is progress update the progress counter
                    if(sumOfConflicts > sumQueenConflicts()){
                        sumOfConflicts=sumQueenConflicts();
                        progressCounter=0;
                        
                    }else{ //else increase the progress counter
                        progressCounter++;
                        //debugging
                        //System.out.println("No progress counter: " + progressCounter);
                        //if the counter where there has been no progress reaches maxMovesWithoutProgress,
                        //place the queens in random positions and start again
                        if(progressCounter==maxMovesWithoutProgress){
                            System.out.println("No progress has been made after " + maxMovesWithoutProgress 
                                               + " moves. Resetting Queens to random positions" + "\n");
                            //creates a new board
                            boardInit();
                            //places queens in random positions
                            placeQueens();
                            //finds all conflicts on board
                            findConficts();
                            //fill the assistant array with the queen conflicts
                            queenConflicts();
                            //displays the initial board
                            displayBoard();
                            //increase restartPerGame counter
                            restartsPerGame++;
                            //increase the total restarts counter
                            restarts++;
                            //reset sum of conflicts to the new value
                            sumOfConflicts=sumQueenConflicts();
                            //reset progress counter
                            progressCounter = 0;
                        }
                    }
                }
            }
        }
        System.out.println(counter + " games were solved with a total of " + restarts + " restarts" +"\n");
    }
    
    //Initial version of the above algorithm that will not restart if no solution is found,
    //but will stop after maxMoves (defined at line 42)
    //int parameter represents the number of games to be solved
    public void algorithmInitial(int games){
        // counter will be increased when a game is solved
        int counter=0;
        //array to hold the moves per solution needed (to find the average moves needed)
        ArrayList<Integer> arrayOfMoves = new ArrayList<Integer>();
        //counter for the moves per game
        int moves=0;
        for(int i = 0; i<games; i++){
            System.out.println("Starting new game!");
            //reset counter for the moves per games
            moves=0;
            //creates a new board
            boardInit();
            //places queens in random positions
            placeQueens();
            //finds all conflicts on board
            findConficts();
            //fill the assistant array with the queen conflicts
            queenConflicts();
            //displays the initial board
            displayBoard();
            //boolean which changes to true if a solution is found
            boolean solution = solution();
            if(solution){
                System.out.println("Game already solved!" + "\n");
                counter++;
                arrayOfMoves.add(0);
            }
            //while there is no solution
            while(!solution && moves < maxMoves){
                moveQueen(findQueen());
                //increase the moves counter
                moves++;
                resetBoard();
                findConficts();
                queenConflicts();
                displayBoard();

                solution = solution();
                if(solution){
                    counter++;
                    System.out.println("Solution found after " + moves + " moves." + "\n");
                    arrayOfMoves.add(moves);
                    moves=0;
                }else{
                    if(moves==maxMoves){
                        System.out.println("No solution found after " + maxMoves + " moves!");
                    }
                }
            }
        }
        double avgMoves=0;
        double sum = 0;
        int max = 0;
        int min = 0;
        if(arrayOfMoves.size()>0){
            max = arrayOfMoves.get(0);
            min = max;
            for(int i = 0; i<arrayOfMoves.size(); i++){
                if(max < arrayOfMoves.get(i)){
                    max = arrayOfMoves.get(i);
                }
                if(min > arrayOfMoves.get(i)){
                    min = arrayOfMoves.get(i);
                }
                sum = sum + arrayOfMoves.get(i);
            }
            avgMoves=sum/arrayOfMoves.size();
        }
        
        System.out.println(counter + " out of " + games + " games were solved with an average of " + avgMoves 
                           + " per solved game, with a maximum of " + max + " moves and a minimum of " 
                           + min + " moves." + "\n");
    }
    
    //method that finds the index of the queen with the greatest "benefit" meaning the greatest difference 
    //between the two positions or randomly selects one (in case of same amount of "benefit")
    public int findQueen2(){
        //list that will hold the queen indices with the greatest benfit
        ArrayList<Integer> queensToMove = new ArrayList<Integer>();
        //variable to hold the index of the queen with the greatest benefit
        int queenIndex = 0;
        //array which will hold the amount of "benefit" per queen
        int[] benefitArray = new int[size];
        //temporaty storage for each queen
        int queenTempIndex=0;
        //temporary tile with minimum Conflicts value
        int tileConflicts=0;
        for(int i=0; i<size; i++){
            //setting an initial minimum value
            if(board[i][0].getQueen()){
                tileConflicts = board[i][1].getConficts();
            }else{
                tileConflicts = board[i][0].getConficts();
            }

            for(int j=0; j<size; j++){
                //keeping the queen index
                if(board[i][j].getQueen()){
                    queenTempIndex=j;
                }else{
                    //else if the value of the tile is smaller than the current one, store that
                    if(tileConflicts>board[i][j].getConficts()){
                        tileConflicts = board[i][j].getConficts();
                    }
                }
            }//by the end of this loop the queen position and the tile with the least conflicts have been found
            
            //the "benefit" value from moving the queen to the next best position is stored in the array
            //if it is negative the new position is not better
            benefitArray[i] = board[i][queenTempIndex].getConficts() - tileConflicts;
        }//by the end of this loop the benefitArray is full
        
        //temporary value for max benefit
        int maxBenefit = benefitArray[0];
        //loop to find the max benefit value
        for(int i=1; i<size; i++){
            if(maxBenefit<benefitArray[i]){
                maxBenefit=benefitArray[i];
            }
        }
        
        //if max benefit is zero or negative return -1 in order to make a random move
        if(maxBenefit<=0){
            return -1;
        }else{
            //loop to find all the queens with the same benefit value
            for(int i=0; i<size; i++){
                if(benefitArray[i]==maxBenefit){
                    queensToMove.add(i);
                }
            }
            
            //debugging
            /*
            System.out.print("BenefitArray ");
            for(int i=0; i<size; i++){
                
                System.out.print(benefitArray[i] + " ");
            }
            System.out.print("Queens to move ");
            for(int i=0; i<queensToMove.size(); i++){
                
                System.out.print(queensToMove.get(i) + " ");
            }
            */
            //if only one queen return its index
            if(queensToMove.size()==1){
                System.out.println("Moving queen in column " + queensToMove.get(0) + "." + "\n");
                return queensToMove.get(0);
            }else{ //if many choose one randomly
                queenIndex = (int) (Math.random()*queensToMove.size());
                //comment out next line to stop displaying which queen is being moved
                System.out.println("Moving queen in column " + queensToMove.get(queenIndex) + "." + "\n");
                return queensToMove.get(queenIndex);
            }
        }

    }
    
    //Algorithm that uses the above method (findQueen2) to find which queen to move (with the greatest "benefit") 
    //or makes a random move with a randomMove probability (line 40)
    //if no move with benefit exists it makes a random move with probability randomMoveWOProgress (line 47),
    //or restarts the board
    //int parameter represents the number of games to be solved
    public void algorithmTwo(int games){
        // counter will be increased when a game is solved
        int counter=0;
        //array to hold the moves per solution needed (to find the average moves needed)
        ArrayList<Integer> arrayOfMoves = new ArrayList<Integer>();
        //counter for the moves per game
        int moves=0;
        //resets counter
        int resetCounter=0;
        //global reset counter
        int totalResets=0;
        for(int i = 0; i<games; i++){
            System.out.println("Starting new game!");
            //reset counter for the moves per games
            moves=0;
            //creates a new board
            boardInit();
            //places queens in random positions
            placeQueens();
            //finds all conflicts on board
            findConficts();
            //fill the assistant array with the queen conflicts
            queenConflicts();
            //displays the initial board
            displayBoard();
            //boolean which changes to true if a solution is found
            boolean solution = solution();
            if(solution){
                System.out.println("Game already solved!");
                counter++;
                arrayOfMoves.add(0);
            }
            //random roll
            double random = 0;
            //while there is no solution
            while(!solution){
                //make a random move
                random = Math.random();
                if(random < randomMove){
                    System.out.println("Making random move!" + "\n");
                    moveRandomQueen();
                }else{
                    int queenToMove = findQueen2();
                    //if it is -1 no benefit from moving any queen
                    if(queenToMove==-1){
                        random = Math.random();
                        //either make a random move
                        if(random < randomMoveWOProgress){
                            System.out.println("Making random move due to lack of better moves" + "\n");
                            moveRandomQueen();
                        //or reset the board    
                        }else{
                            System.out.println("No better positions exist. Resetting Queens to random positions" + "\n");
                            resetCounter++;
                            totalResets++;    
                            //creates a new board
                            boardInit();
                            //places queens in random positions
                            placeQueens();
                            //finds all conflicts on board
                            findConficts();
                            //fill the assistant array with the queen conflicts
                            queenConflicts();
                            //displays the initial board
                            displayBoard();
                        }
                    }else{
                        moveQueen(queenToMove);
                    }
                }
                //increase the moves counter
                moves++;
                resetBoard();
                findConficts();
                queenConflicts();
                displayBoard();

                solution = solution();
                if(solution){
                    counter++;
                    System.out.println("Solution found after " + moves + " moves and " 
                                       + resetCounter + " resets." + "\n");
                    arrayOfMoves.add(moves);
                    moves=0;
                    resetCounter=0;
                }
            }
        }
        double avgMoves=0;
        double sum = 0;
        int max = 0;
        int min = 0;
        if(arrayOfMoves.size()>0){
            max = arrayOfMoves.get(0);
            min = max;
            for(int i = 0; i<arrayOfMoves.size(); i++){
                if(max < arrayOfMoves.get(i)){
                    max = arrayOfMoves.get(i);
                }
                if(min > arrayOfMoves.get(i)){
                    min = arrayOfMoves.get(i);
                }
                sum = sum + arrayOfMoves.get(i);
            }
            avgMoves=sum/arrayOfMoves.size();
        }
        System.out.println(counter + " out of " + games + " games were solved with an average of " + avgMoves 
                           + " moves per solved game, with a maximum of " + max + " moves and a minimum of " 
                           + min + " moves and " + totalResets + " total resets." + "\n");
    }
    
    //method that moves a queen randomly
    public void moveRandomQueen(){
        //index of queen that is to be moved
        int queenToMove = (int) (Math.random()*size);
        //location of the queen in the column
        int queenIndex = 0;
        //find actual queen position
        for(int i=0; i<size; i++){
            if(board[queenToMove][i].getQueen()){
                queenIndex=i;
                i=size;
            }
        }
        //roll for a new random position different than the current one
        int newPosition = (int) (Math.random()*size);
        while(newPosition==queenIndex){
            //debugging
            //System.out.println("rolling " + newPosition);
            newPosition = (int) (Math.random()*size);
        }
        //comment out next line to stop displaying which queen is moved by this method
        System.out.println("Moving queen in column " + queenToMove + "." + "\n");
        //move queen to new position
        board[queenToMove][queenIndex].setQueen(false);
        board[queenToMove][newPosition].setQueen(true);
        
    }
    
    //menu for selecting algorithm
    public void menu(){
        int choice = 0;
        do{
            Scanner input = new Scanner(System.in);
            System.out.println("which algorithm would you like to use?" + "\n" +
                               "1. Initial algorithm (Queen with the most conflicts moved " +
                "to position with the least - will not always find solution)" + "\n" 
                               + "2. Algorithm One (Same as initial, " +
                "but game will restart if there is no progress after some moves)" + "\n" 
                               + "3. Algorithm Two (Queen that will benefit most is moved, " +
                "chance for random move, if no benefit game restart or make a random move)" +"\n" 
                               + "4. Exit" + "\n");
            if(input.hasNextInt()){
                choice = input.nextInt();
                switch(choice){
                case 1: algorithmInitial(getGames());
                    break;
                case 2: algorithmOne(getGames());
                    break;
                case 3: algorithmTwo(getGames());
                    break;
                case 4: choice = 4;
                    break;
                default: System.out.println("You must chose one of options 1-4!");
                    
                }
                
            }else{
                System.out.println("You must chose one of options 1-4!");
            }
        }while(choice!=4);
    }
    
    //method for asking for number of games
    public int getGames(){
        int games = 1;
        boolean done = false;
        do{
            System.out.println("How many games would you like to solve? ");
            Scanner input = new Scanner(System.in);
            if(input.hasNextInt()){
                games = input.nextInt();
                if(games < 1){
                    System.out.println("Please enter a positive integer!");
                }else{
                    done=true;
                }
            }else{
                System.out.println("Please enter a positive integer!");
            }
        }while(!done);
        return games;    
    }
        
    
}// end of class
