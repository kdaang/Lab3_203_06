package uwaterloo.ca.lab3_203_06;

import android.util.Log;
import android.widget.ImageView;

import java.util.TimerTask;

public class GameLoopTask extends TimerTask {
    String direction;
    GameBlock gb;
    float BLOCKX,BLOCKY, SCALE;
    ImageView gameBoard;
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
    }
    @Override
    public void run(){
        int xy[] = new int[2];
        switch (direction){
            case "RIGHT"://move gameblock to the right
                Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                if(gb.getX()+gb.getVelocity()>= ORIGIN_X + (gb.getWidth()*SCALE*3f)){
                    gb.setX(ORIGIN_X + (gb.getWidth()*SCALE*3f));
                    Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                    this.cancel();
                } else {
                    gb.setX(gb.getX() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()+10);
                    //Log.d("Check", "X: "+gb.getX());
                }
                break;
            case "LEFT"://move gameblock to the left
                Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                if(gb.getX()+gb.getVelocity()<= ORIGIN_X){
                    gb.setX(ORIGIN_X);
                    Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                    this.cancel();
                } else {
                    gb.setX(gb.getX() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()-10);
                }
                break;
            case "UP"://move gameblock up
                Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                if(gb.getY()+gb.getVelocity()<=ORIGIN_Y){
                    gb.setY(ORIGIN_Y);
                    Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                    this.cancel();
                } else {
                    gb.setY(gb.getY() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()-10);
                }
                break;
            case "DOWN"://move gameblock to the down
                Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                if(gb.getY()+gb.getVelocity()>=ORIGIN_Y+(gb.getHeight()*SCALE*3f)){
                    gb.setY(ORIGIN_Y+(gb.getHeight()*SCALE*3f));
                    Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                    this.cancel();
                } else {
                    gb.setY(gb.getY() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()+10);
                }
                break;
        }
    }
}