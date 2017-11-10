package huffman;

public class Huffnode {
    private double frequency;
    private String value;
    private String identifier;
    
    public Huffnode(){
        frequency = 0;
        value = "";
        identifier = "";
    }// end of constructor #1
    
    public Huffnode(double frequency, String value){
        this.frequency = frequency;
        this.value = value;
        identifier = "";
    }// end of constructor #2


    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }


}// end of class
