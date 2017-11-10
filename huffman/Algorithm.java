package huffman;

import java.io.File;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class Algorithm {
    
    // ArrayList the contains the nodes
    private ArrayList<Node> nodeList = new ArrayList<Node>();
    // ArrayList that contains the Huffnodes with the prefix code identifier
    private ArrayList<Huffnode> decodedList = new ArrayList<Huffnode>();
    // root node of the tree
    private Node root;
    // private String message="";
    
    public Algorithm(){
        
        // readFromFile will ask the user the filename to read frequencies from
        if(readFromFile()){
            
            //method to create the tree
            root = mergeNodes();
            
            //method to create the array with the symbol-unique identifier solutions
            dfs(root, root.getIdentifier());
            
            //comment method below if you do not want to display the symbol - identifier list
            printHuffnodes();
            
            //uncomment method below if you wish to encode a word
            //readWord();
            
            //method to ask user for input to decode
            readInput();
            
        }
    }//end of constructor
    
    //method tha prints the current nodeList's elements - (only parent nodes are printed)
    public void printArray(){
        for(int i=0;i<nodeList.size();i++){
            System.out.println(nodeList.get(i).getFrequency()+ " " + nodeList.get(i).getValue() + " " + nodeList.get(i).getIdentifier());
        }
    }
    
    // method that searches the tree and returns a list of its childless nodes with their unique identifiers
    public ArrayList<Huffnode> dfs(Node node, String id){
        // uniqueID will contain the identifier of the nodes once the dfs is finished
        String uniqueID = id + node.getIdentifier();
        // assumes a proper binary tree, so the existance of one child is enough to move on to both
        if(node.getLeft() != null){
            //dfs is called recursively on both children
            dfs(node.getLeft(), uniqueID);
            dfs(node.getRight(), uniqueID);
        }else{
            //if it has reached at a leaf of the tree, it creates a new node (so as not to destroy the tree structure) with the node's information, and adds it to the decodeList
            Huffnode newHuffnode = new Huffnode(node.getFrequency(), node.getValue());
            newHuffnode.setIdentifier(uniqueID);
            this.decodedList.add(newHuffnode);
        }
        return this.decodedList;
    }
    
    //method that reads the frequencies and symbols from file and adds them to nodeList array
    public void readFile(String textFile) throws Exception {
        File inFile = new File(textFile);
        Scanner input = new Scanner(inFile);
        while(input.hasNextLine()){
            String in = input.nextLine();
            double frequency = Double.parseDouble(in.substring(0,in.indexOf(" ")));
            String value = in.substring(in.indexOf(" ")+1);
            nodeList.add(new Node(frequency, value));
            
        }
        input.close();
        //comment method below if you do not want to display the initial frequencies-symbols read from file
        printArray();
       
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
    
    //method used for merging all the nodes into one tree keeping their relative position on the list and returns a single root node
    public Node mergeNodes(){
        while(nodeList.size()>1){
            Node left = nodeList.get(0);
            Node right = nodeList.get(1);
            int indexLeft = 0;
            int indexRight = 1;
            for(int i = 2;i<nodeList.size();i++){
                // if left and right have the same frequency or left has lower frequency than right, the right is replaced if a new node has smaller frequency
                if(right.getFrequency()>nodeList.get(i).getFrequency() && left.getFrequency() <= right.getFrequency()){
                    right = nodeList.get(i);
                    indexRight = i;
                }else{
                    //if right has higher frequency than the new node and but lower than the left, right is moved to left and new node to right
                    if(right.getFrequency()>= nodeList.get(i).getFrequency() && left.getFrequency() > right.getFrequency()){
                        left = right;
                        indexLeft = indexRight;
                        right = nodeList.get(i);
                        indexRight = i;
                    }else{
                        //if right has lower frequency than the new node and lower than the left, AND left has higher frequency than the new node again right is moved to left and new node to right
                        if(right.getFrequency() < nodeList.get(i).getFrequency() && left.getFrequency()>right.getFrequency() && left.getFrequency()>nodeList.get(i).getFrequency()){
                            left = right;
                            indexLeft = indexRight;
                            right = nodeList.get(i);
                            indexRight = i;
                        }
                    }// if none of the above statements are true for all other nodes in the list, the first two will be merged in the order they are found in the array
                }
            }
            
            nodeList.set(indexLeft, new Node(left,right));
            nodeList.remove(indexRight);
            //comment next two lines if you do not want to display the creation of the tree step by step
            System.out.println("Merging " + left.getValue() + " with " + right.getValue());
            printArray();
        }
        return nodeList.get(0);
    }
    
    //Method that accepts the encoded message and decodes it using the decryptionList
    public String decode(String encodedMessage){
        String decodedMessage = "";
        int currentIndex = 0;
        for(int i=0;i<encodedMessage.length()+1;i++){
            for(int j=0;j<decodedList.size();j++){
                // System.out.println(encodedMessage.substring(currentIndex, i));
                if(encodedMessage.substring(currentIndex, i).equals(decodedList.get(j).getIdentifier())){
                    decodedMessage = decodedMessage + decodedList.get(j).getValue();
                    j = decodedList.size(); // stops the search if a match is found
                    currentIndex = i; // continues the search from current i position
                }
            }
        }
        
        return decodedMessage;
    }
    
    //method that prints the encryption identifier of each symbol
    public void printHuffnodes(){
        System.out.println("Identifier - Symbol List\n----------------------------");
        for(int i=0;i<decodedList.size();i++){
            System.out.println(decodedList.get(i).getIdentifier() + " " + decodedList.get(i).getValue());
        }
    }
    
    //encodes a String into 0s and 1s using the decodedList
    public String encode(String message){
        String code = "";
        for(int i =0;i<message.length(); i++){
            String msg = "" + message.charAt(i);
            for(int j=0;j<decodedList.size();j++){
                
                if(msg.equals(decodedList.get(j).getValue())){
                    code = code + decodedList.get(j).getIdentifier();
                }
            }
        }
        return code;
    }
    
    // method that asks the user for message to be decoded
    public void readInput(){
        System.out.println("Please enter message to decode: ");
        try{
            Scanner input = new Scanner(System.in);
            //System.out.println(decode(input.next())); //method that decodes using the decodedList - uncomment to use this one instead
            System.out.println(decodeTree2(input.next())); //method that uses directly the tree to decode the message
        // input.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    //method that asks the user for a word to encode
    public void readWord(){
        System.out.println("Please enter message to encode: ");
        try{
            Scanner input = new Scanner(System.in);
            System.out.println(encode(input.nextLine()));
        // input.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    /*
    //failed attempt to create a recursive method of decoding using directly the tree
    public void decodeTree(String code, int index, Node node){
        Node currentNode = node;

        if(code.charAt(index)=='0'){
            currentNode = node.getLeft();
            index++;
        }else{
            if(code.charAt(index)=='1'){
            currentNode = node.getRight();
            index++;
            }
        }
        if(node.getLeft()== null){
            message = message + node.getValue();
            currentNode = root;
        }

        if(index < code.length()){
            decodeTree(code, index, currentNode);
        }
    }*/
    
    //method that decodes the message using directly the tree
    public String decodeTree2(String code){
        String dMessage = "";
        //start from root
        Node currentNode = root;
        int index = 0;
        while(index<code.length()){
            //if 0 currentNode is left
            if(code.charAt(index)=='1'){
                currentNode = currentNode.getRight();
                index++;
            }else{
                //if 1 currentNode is right
                if(code.charAt(index)=='0'){
                    currentNode = currentNode.getLeft();
                    index++;
                }
            }
            //if node is a leaf, add its value to the message and reset currentNode to root
            if(currentNode.getLeft()==null){
                dMessage = dMessage + currentNode.getValue();
                currentNode = root;
            }
        }
        return dMessage;
    }
    
    
}// end of class
