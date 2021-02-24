package frc.robot.files;

public enum RIOLevel{
    DEBUG(0),
    IO(1), // change maybe but works for now
    SYSTEM(2),
    ERROR(3);
    private int id;
    private RIOLevel(int level){
        id = level;
    }

    public int getLevel(){
        return id;
    }

}
