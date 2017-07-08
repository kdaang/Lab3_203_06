package uwaterloo.ca.lab4_203_06;

import android.util.Log;
import android.widget.ImageView;

import java.util.Random;
import java.util.TimerTask;

public class GameLoopTask extends TimerTask {
    String direction;
    Random rnd = new Random(16);
    GameBlock gb;
    float BLOCKX,BLOCKY, SCALE;
    ImageView gameBoard;
    Position[] positions = new Position[16];
    public final float ORIGIN_X = -38, ORIGIN_Y = 117;
    public GameLoopTask(String dir, GameBlock gb, ImageView gameBoard, float blockx, float blocky, float SCALE){
        direction=dir;
        this.gb=gb;
        BLOCKX=blockx;
        BLOCKY=blocky;
        this.gameBoard=gameBoard;
        BLOCKX = gb.getX();
        BLOCKY = gb.getY();
        this.SCALE = SCALE;
        Log.d("GAMEBOARD", String.format("BLOCK.XY, %f, %f", BLOCKX, BLOCKY));
        assign();
    }
    public void assign() {
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                positions[i+j] = new Position(ORIGIN_X + gb.getWidth() * SCALE * j, ORIGIN_Y +gb.getHeight()*SCALE*i, false);
            }
        }
    }
    public Position getPosition(float x, float y){
        for(int i=0;i<16;i++){
            if(positions[i].getX()==x&&positions[i].getY()==y) {
                return positions[i];
            }
        }
        return null;
    }
    @Override
    public void run(){
        int xy[] = new int[2];
        gameBoard.getLocationOnScreen(xy);
       // getPosition(gb.getX(),gb.getY()).setOccupied(false);

        switch (direction){
            case "RIGHT":
                if(gb.getX()+gb.getVelocity()>= ORIGIN_X + (gb.getWidth()*SCALE*3f)){
                    gb.setX(ORIGIN_X + (gb.getWidth()*SCALE*3f));
                    getPosition(gb.getX(), gb.getY()).setOccupied(true);
                    this.cancel();
                } else {
                    gb.setX(gb.getX() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()+10);
                }
                break;
            case "LEFT":
                if(gb.getX()+gb.getVelocity()<= ORIGIN_X){
                    gb.setX(ORIGIN_X);
                    getPosition(gb.getX(), gb.getY()).setOccupied(true);
                    this.cancel();
                } else {
                    gb.setX(gb.getX() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()-10);
                }
                break;
            case "UP":
                if(gb.getY()+gb.getVelocity()<=ORIGIN_Y){
                    gb.setY(ORIGIN_Y);
                    getPosition(gb.getX(), gb.getY()).setOccupied(true);
                    this.cancel();
                } else {
                    gb.setY(gb.getY() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()-10);
                }
                break;
            case "DOWN":
                if(gb.getY()+gb.getVelocity()>=ORIGIN_Y+(gb.getHeight()*SCALE*3f)){
                    gb.setY(ORIGIN_Y+(gb.getHeight()*SCALE*3f));
                    getPosition(gb.getX(), gb.getY()).setOccupied(true);
                    this.cancel();
                } else {
                    gb.setY(gb.getY() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()+10);
                }
                break;
        }

        //Position rndPos = getRandPos();
       // createBlock(rndPos.getX(), rndPos.getY());
    }
    public void createBlock(float x, float y){
        GameBlock newBlock = new GameBlock(gb.getRl(), gb.getSrcImg(), x, y, gb.getCtx(), gb.getTime(), gameBoard);
    }
    public Position getRandPos(){
        Position tmpPos=positions[rnd.nextInt()];
        while (tmpPos.isOccupied()){
            tmpPos=positions[rnd.nextInt()];
        }
        return tmpPos;
    }

    class Position {
        float xPos;
        float yPos;
        boolean occupied;
        public Position(float x, float y, boolean occupied){
            xPos=x;
            yPos=y;
            this.occupied=occupied;
        }

        public float getX() {
            return xPos;
        }

        public float getY() {
            return yPos;
        }

        public boolean isOccupied() {
            return occupied;
        }

        public void setOccupied(boolean occupied) {
            this.occupied = occupied;
        }

    }
}