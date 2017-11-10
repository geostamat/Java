package centrality;

import java.io.File;

import java.util.ArrayList;
import java.util.Scanner;

public class Centrality {
    
    // will contain the information for each node and its index will correspond to the matrix index
    private ArrayList<NodeInfo> nodeList = new ArrayList<NodeInfo>();
    // adjacency matrix
    private int[][] adjMatrix;
    //used for reading the lines from file
    private ArrayList<String> fileInfo = new ArrayList<String>();
    //used for displaying the paths
    private String path = "";
    //used for counting paths
    private double pathCounter = 0;
    
    
    public Centrality(){
        
        //loads file contents
        readFromFile();
        
        //finds unique nodes and creates a NodeInfo for each loading it on the nodeList array
        identifySingleNodes();
        
        //creates the 2D array
        createArray();
        
        //fills the 2D array
        fillAdjArray();
        
        //comment out line below if you don't want the adjMatrix displayed
        printMatrix();
        
        //finds shortest routes        
        findPaths();
        
        //prints the centrality report
        centralityReport();
    
    }// end of constructor
    
    //method that reads file and loads all line in a String ArrayList, cause information will have to be accessed more than once
    public void readFile(String textFile) throws Exception {
        File inFile = new File(textFile);
        Scanner input = new Scanner(inFile);
        while(input.hasNextLine()){
            fileInfo.add(input.nextLine());
        }
        input.close();
       
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
                    readFile(in);
                    check = false;
                    success = true;
                } catch (Exception e) {
                    System.out.println("Error reading file!");
                    System.out.println(e.toString());
                    check = true;
                }

            }

        }while(check);
        // input.close();
        return success;
    }
    
    // method to create the array of correct size after file is read
    public void createArray(){
        adjMatrix = new int[nodeList.size()][nodeList.size()];
    }
    
    // method to identify how many and which are the unique nodes and fill the nodeList array
    // the indices of this array will corespond to the indices of the adjMatrix
    public void identifySingleNodes(){
        for(int j=0;j<fileInfo.size(); j++){
            String valueA = fileInfo.get(j).substring(0,fileInfo.get(j).indexOf(",")).trim();
            String valueB = fileInfo.get(j).substring(fileInfo.get(j).indexOf(",")+1).trim();
            boolean checkA = true;
            boolean checkB = true;
            for(int i =0;i<nodeList.size();i++){
                if(nodeList.get(i).getNode().equals(valueA)){
                    checkA = false;
                }
                if(nodeList.get(i).getNode().equals(valueB)){
                    checkB = false;
                }
            }
            if(checkA){
            nodeList.add(new NodeInfo(valueA,nodeList.size()));
            }
            if(checkB){
            nodeList.add(new NodeInfo(valueB,nodeList.size()));
            }
        }
    }
    
    // method that returns the index of node in the nodeList(same index is used for each node in the adjMatrix)
    public int searchNodeList(String node){
        int index = 0;
        for(int i=0 ; i<nodeList.size(); i++){
            if(nodeList.get(i).getNode().equals(node)){
                index = i;
                i = nodeList.size();
            }
        }
        return index;
    }
    
    // method that fills the adjacency matrix placing 1s where there is a connection between nodes and 0s if not
    public void fillAdjArray(){
        // initially all the array is filled with 0s
        for(int i=0; i<nodeList.size(); i++){
            for(int j=0; j<nodeList.size(); j++){
                adjMatrix[i][j]=0;
            }
        }
        // 1s are placed where a connection between nodes exists
        for(int j=0;j<fileInfo.size(); j++){
            int x = searchNodeList(fileInfo.get(j).substring(0,fileInfo.get(j).indexOf(",")).trim());
            int y = searchNodeList(fileInfo.get(j).substring(fileInfo.get(j).indexOf(",")+1).trim());
            adjMatrix[x][y] = 1;
            adjMatrix[y][x] = 1;
            
        }
    }
    
    // method that prints the matrix on the console
    public void printMatrix(){
        System.out.print("  ");
        for(int i=0; i<nodeList.size(); i++){
            System.out.print(nodeList.get(i).getNode() + " ");
        }
        System.out.println();
        for(int i=0; i<adjMatrix.length; i++){
            System.out.print(nodeList.get(i).getNode() + " ");
            for(int j=0; j<adjMatrix.length; j++){
                 System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    //Breadth first search which finds one of the shortest paths between 2 nodes
    public void bfs(int source, int destination){
        //queue to be used for bfs
        LinkedQueue<Integer> queue = new LinkedQueue<Integer>();
        // adds source node as first node to the queue and marks it as visited
        queue.enqueue(source);
        nodeList.get(source).setVisited(true);
        // keeps going till destination becomes visited - or queue is empty (case where there is no path)
        while(!queue.isEmpty() && !nodeList.get(destination).isVisited()){
            int index = queue.first();
            for(int i = 0; i<nodeList.size(); i++){
                //if the node is not visited and there is a connection between the two nodes
                if(!nodeList.get(i).isVisited() && adjMatrix[index][i]==1){
                    //node is added to the queue
                    queue.enqueue(i);
                    // node is marked as visited upon entering the queue
                    nodeList.get(i).setVisited(true);
                    // parent node of newly added node is updated
                    nodeList.get(i).setParent(index);
 
                }
            }
            //the node has been processed and is removed from the queue
            queue.dequeue();
            
        }
    }
    
    //find and stores the path backtracking the array and increases the counters of all nodes involved
    public boolean returnPath(int source,int destination){
        path = nodeList.get(destination).getNode() + path;
        nodeList.get(destination).setCounter(nodeList.get(destination).getCounter() + 1);
        //creates the path recursively
        if(nodeList.get(destination).getParent()!= -1){
            returnPath(destination,nodeList.get(destination).getParent());  
        }
        //case of disconnected node
        if(path.length()<2){
            path = "No path between node " + nodeList.get(source).getNode() + " and " + nodeList.get(destination).getNode();
            //corrects the counter that was increased above
            nodeList.get(destination).setCounter(nodeList.get(destination).getCounter() - 1);
            return false;
        }else{
            return true;
        }
    }
    
    //method that displays all current information of the nodeList
    public void returnNodeList(){
        for(int i=0; i<nodeList.size(); i++){
            System.out.print(i + " ");
            System.out.println(nodeList.get(i).getNode() + " " + nodeList.get(i).getCounter() + " " + nodeList.get(i).getParent() + " " + nodeList.get(i).isVisited());
        }
    }
    
    //method that resets the parent and visited values of nodeList - also resets the path String
    public void resetNodeList(){
        for(int i=0; i<nodeList.size(); i++){
            nodeList.get(i).setParent(-1);
            nodeList.get(i).setVisited(false);
        }
        path = "";
    }
    
    //method that bfsearches all the adjMatrix
    public void findPaths(){
        for(int i=0; i<nodeList.size(); i++){
            for(int j=i+1; j<nodeList.size(); j++){
                
                //will bfs all combinations of nodes
                bfs(i,j);
                
                //updates node counters, the pathCounter and the path
                if(returnPath(i,j)){
                    pathCounter++;
                }
                
                //comment line below if you do not want the paths displayed
                System.out.println(path);
                
                //uncomment line below if you want to see the nodeList info after each path
                //returnNodeList();
                
                //resets the values of path, and visited/parent values of nodeList in order to use it for the next combination
                resetNodeList();
            }
        }
    }
    
    //method that returns the centrality of each node
    public void centralityReport(){
        if(nodeList.size()>0){
            System.out.println("Total paths: " + pathCounter);
            for(int i = 0; i<nodeList.size(); i++){
                System.out.println("Node: " + nodeList.get(i).getNode() + " Frequency: " + nodeList.get(i).getCounter() + " Centrality: " + nodeList.get(i).getCounter()/pathCounter);
            }
        }
    }
    
    
}// end of class
