package centrality;

public class NodeInfo {
    
    private String node;
    // will change to true when a node is visited during bfs
    private boolean visited;
    // will contain the index of the parent node during bfs
    private int parent;
    // will increase if node is used during the creation of a path
    private int counter;
    
    public NodeInfo(String node, int indexID){
        this.node = node;
        counter = 0;
        visited = false;
        parent = -1;
    }


    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }


    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getParent() {
        return parent;
    }


    public void setNode(String node) {
        this.node = node;
    }

    public String getNode() {
        return node;
    }

}
