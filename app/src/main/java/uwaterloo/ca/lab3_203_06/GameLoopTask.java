package uwaterloo.ca.lab3_203_06;

import android.util.Log;
import android.widget.ImageView;

import java.util.TimerTask;

public class GameLoopTask extends TimerTask {
    String direction;
    GameBlock gb;
    float BLOCKX,BLOCKY;
    ImageView gameBoard;
    public GameLoopTask(String dir, GameBlock gb, ImageView gameBoard, float blockx, float blocky){
        direction=dir;
        this.gb=gb;
        BLOCKX=blockx;
        BLOCKY=blocky;
        this.gameBoard=gameBoard;

    }
    @Override
    public void run(){
        switch (direction){
            case "RIGHT":
                if(gb.getX()+gb.getVelocity()>=BLOCKX+25+gameBoard.getWidth()*0.75f){
                    this.cancel();
                    gb.setX(BLOCKX+25+gameBoard.getWidth()*0.75f);
                } else {
                    gb.setX(gb.getX() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()+10);
                    Log.d("Check", "X: "+gb.getX());
                }
                break;
            case "LEFT":
                if(gb.getX()+gb.getVelocity()<=BLOCKX+25){
                    this.cancel();
                    gb.setX(BLOCKX+25);
                } else {
                    gb.setX(gb.getX() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()-10);
                }
                break;
            case "UP":
                if(gb.getY()+gb.getVelocity()<=BLOCKY + 25){
                    this.cancel();
                    gb.setY(BLOCKY+25);
                } else {
                    gb.setY(gb.getY() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()-10);
                }
                break;
            case "DOWN":
                if(gb.getY()+gb.getVelocity()>=BLOCKY + (gameBoard.getHeight()-9)*0.53f){
                    this.cancel();
                    gb.setY(BLOCKY + (gameBoard.getHeight()-9)*0.53f);
                } else {
                    gb.setY(gb.getY() + gb.getVelocity());
                    gb.setVelocity(gb.getVelocity()+10);
                }
                break;
        }
    }
}