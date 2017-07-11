package uwaterloo.ca.lab4_203_06;

/**
 * Created by Cristiano Chelotti on 7/9/2017.
 */

public class Position {
    float xPos;
    float yPos;
    int blocknum_x;
    int blocknum_y;
    boolean occupied;
    boolean stuffed;
    int num;
    GameBlock block;

    public Position(float x, float y, int blocknum_x, int blocknum_y, boolean occupied, int num) {
        xPos = x;
        yPos = y;
        this.blocknum_x = blocknum_x;
        this.blocknum_y = blocknum_y;
        this.occupied = occupied;
        this.stuffed = false;
        this.num = num;
        block = null;
    }

    public float getX() {
        return xPos;
    }

    public float getY() {
        return yPos;
    }

    public int getBlocknum_x(){return blocknum_x;};

    public int getBlocknum_y(){return blocknum_y;};

    public void setBlocknum_x(int x){
        blocknum_x = x;
    }
    public void setBlocknum_y(int y){
        blocknum_y = y;
    }
    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setStuffed(boolean stuffed){
        this.stuffed = stuffed;
    }

    public boolean getStuffed(){
        return stuffed;
    }

    public void setBlock(GameBlock gb){
        block = gb;
        if (gb != null){
            num = gb.getCurrBlockNum();
        } else{
            num = -1;
        }
    }

    public GameBlock getBlock(){
        return block;
    }
}
