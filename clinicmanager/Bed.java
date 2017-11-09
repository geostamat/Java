import java.io.Serializable;

public class Bed implements Serializable{
    
    private String room;
    private String bedNumber;
    private BedStatus bStatus = BedStatus.AVAILABLE;

    
    public Bed(String room, String bedNumber) {
        this.room = room;
        this.bedNumber = bedNumber;

    }// end of constuctor #1
    
    public Bed() {
        room = "";
        bedNumber = "";


    }// end of constuctor #2
    
    public Bed(String room, String bedNumber, BedStatus bedstatus) {
        this.room = room;
        this.bedNumber = bedNumber;
        this.bStatus = bedstatus;
    }

    public void setRoom(String roomNumber) {
        this.room = roomNumber;
    }

    public String getRoom() {
        return room;
    }

    public void setBedNumber(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public void setBStatus(BedStatus bStatus) {
        this.bStatus = bStatus;
        
    }

    public BedStatus getBStatus() {
        return bStatus;
    }
    
    public String toString(){
        return "Room " + room + " Bed " + bedNumber;
    }
    
    public String toString2(){

        String string = room + " " + bedNumber;
        return string;
    }
}// end of class
