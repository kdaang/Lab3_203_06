package uwaterloo.ca.lab3_203_06;

import android.util.Log;
import android.widget.ImageView;

import java.util.TimerTask;

public class GameLoopTask extends TimerTask {
    String direction;
    GameBlock gb;
    float BLOCKX,BLOCKY;
    ImageView gameBoard;
    public final float ORIGIN_X = -38, ORIGIN_Y = 117;
    public GameLoopTask(String dir, GameBlock gb, ImageView gameBoard, float blockx, float blocky){
        direction=dir;
        this.gb=gb;
        BLOCKX=blockx;
        BLOCKY=blocky;
        this.gameBoard=gameBoard;
        BLOCKX = gb.getX();
        BLOCKY = gb.getY();
        Log.d("GAMEBOARD", String.format("BLOCK.XY, %f, %f", BLOCKX, BLOCKY));
    }
    @Override
    public void run(){
        int xy[] = new int[2];
        gameBoard.getLocationOnScreen(xy);
        Log.d("GAMEBOARD", String.format("GBOARD.X, %d", xy[0]));
        Log.d("GAMEBOARD", String.format("GBOARD.Y, %d", xy[1]));
        Log.d("GB", String.format("GB.SCALE, %f", gameBoard.getScaleY()));
        switch (direction){
            case "RIGHT":
                Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                if(gb.getX()+gb.getVelocity()>= ORIGIN_X + (gb.getWidth()*0.65f*3f)){
                    gb.setX(ORIGIN_X + (gb.getWidth()*0.65f*3f));
                    Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                    this.cancel();
                } else {
                    gb.setX(gb.getX() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()+10);
                    //Log.d("Check", "X: "+gb.getX());
                }
                break;
            case "LEFT":
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
            case "UP":
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
            case "DOWN":
                Log.d("X", String.format("GB.X, %f, %f", gb.getX(),gb.getY()));
                if(gb.getY()+gb.getVelocity()>=ORIGIN_Y+(gb.getHeight()*0.65f*3f)){
                    gb.setY(ORIGIN_Y+(gb.getHeight()*0.65f*3f));
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