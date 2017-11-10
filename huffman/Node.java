package huffman;

public class Node {
    private Node left;
    private Node right;
    private Node parent;
    private Huffnode element;
    
    // constuctor used for creating a new node
    public Node(double frequency, String value){
        element = new Huffnode(frequency, value);
        left = null;
        right = null;
        parent = null;
    }
    
    // constructor used for merging 2 nodes into a tree
    public Node(Node newLeft, Node newRight){
        element = new Huffnode(newLeft.getFrequency() + newRight.getFrequency(),newLeft.getValue()+newRight.getValue());
        this.parent = null;
        this.setLeft(newLeft);
        this.setRight(newRight);
        newLeft.setParent(this);
        newRight.setParent(this);
        newLeft.setIdentifier("0");
        newRight.setIdentifier("1");
        //depthSetIdentifier(this); //no longer needed, unique identifiers are created by dfs in Algorithm1
    }


    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getLeft() {
        return left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getRight() {
        return right;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public void setElement(Huffnode element) {
        this.element = element;
    }

    public Huffnode getElement() {
        return element;
    }
    
    public double getFrequency(){
        return this.element.getFrequency();
    }
    
    public String getValue(){
        return this.element.getValue();
    }
    
    public String getIdentifier(){
        return this.element.getIdentifier();
    }
    
    public void setIdentifier(String identifier){
        this.element.setIdentifier(identifier);
    }
    
    // method to update recursively the identifiers after each node merge - absolete after updating the dfs method in Algorithm
    public void depthSetIdentifier(Node node){
        if(node.getLeft()!=null){
            if(node.getParent()!=null){
                //children take the identifier of their parent + their location identifier (left/right)
                node.getLeft().setIdentifier(node.getIdentifier() + "0");
                node.getRight().setIdentifier(node.getIdentifier() + "1");
            }else{
                //for root nodes children take only location identifier
                node.getLeft().setIdentifier("0");
                node.getRight().setIdentifier("1");
            }
            depthSetIdentifier(node.getLeft());
            depthSetIdentifier(node.getRight());
        }
    }
    

        

}// end of class
