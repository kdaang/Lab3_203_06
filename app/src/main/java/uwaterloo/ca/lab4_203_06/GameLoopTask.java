package uwaterloo.ca.lab4_203_06;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import java.util.Random;
import java.util.TimerTask;
import java.util.Vector;

public class GameLoopTask extends TimerTask {
    String direction;
    GameBlock gb;
    float BLOCKX,BLOCKY, SCALE;
    ImageView gameBoard;
    Activity thisActivity;
    GameLoopTask thisTask=this;
    Position[][] positions;
    public final float ORIGIN_X = -38, ORIGIN_Y = 117;
    public GameLoopTask(String dir, GameBlock gb, ImageView gameBoard, float blockx, float blocky, float SCALE, Activity act, Position[][] positions){
        direction=dir;
        this.gb=gb;
        BLOCKX=blockx;
        BLOCKY=blocky;
        this.gameBoard=gameBoard;
        BLOCKX = gb.getX();
        BLOCKY = gb.getY();
        this.SCALE = SCALE;
        thisActivity= act;
        this.positions=positions;
        Log.d("GAMEBOARD", String.format("BLOCK.XY, %f, %f", BLOCKX, BLOCKY));
        //assign();
    }

    public Position getPosition(float x, float y){
        int e=4;
        for(int i=0;i<4;i++) {
            for (int j = 0; j < 4; j++) {
                if ((positions[i][j].getX() < x+e || positions[i][j].getX() >x-e)&& (positions[i][j].getY() < y+e || positions[i][j].getY()> y-e)) {
                    return positions[i][j];
                }
            }
        }
        return null;
    }
    @Override
    public void run(){
        thisActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int xy[] = new int[2];
                gameBoard.getLocationOnScreen(xy);
                Log.d("GAMEBLOCK", "x: " + gb.getX() + " y: " + gb.getY());
                getPosition(gb.getX(), gb.getY()).setOccupied(false);

                switch (direction) {
                    case "RIGHT":
                        if (gb.getX() + gb.getVelocity() >= ORIGIN_X + (gb.getWidth() * SCALE * 3f)) {
                            gb.setX(ORIGIN_X + (gb.getWidth() * SCALE * 3f));
                            getPosition(gb.getX(), gb.getY()).setOccupied(true);
                            thisTask.cancel();
                        } else {
                            gb.setX(gb.getX() + gb.getVelocity());
                            gb.setVelocity(gb.getVelocity() + 10);
                        }
                        break;
                    case "LEFT":
                        if (gb.getX() + gb.getVelocity() <= ORIGIN_X) {
                            gb.setX(ORIGIN_X);
                            getPosition(gb.getX(), gb.getY()).setOccupied(true);
                            thisTask.cancel();
                        } else {
                            gb.setX(gb.getX() + gb.getVelocity());
                            gb.setVelocity(gb.getVelocity() - 10);
                        }
                        break;
                    case "UP":
                        if (gb.getY() + gb.getVelocity() <= ORIGIN_Y) {
                            gb.setY(ORIGIN_Y);
                            getPosition(gb.getX(), gb.getY()).setOccupied(true);
                            thisTask.cancel();
                        } else {
                            gb.setY(gb.getY() + gb.getVelocity());
                            gb.setVelocity(gb.getVelocity() - 10);
                        }
                        break;
                    case "DOWN":
                        if (gb.getY() + gb.getVelocity() >= ORIGIN_Y + (gb.getHeight() * SCALE * 3f)) {
                            gb.setY(ORIGIN_Y + (gb.getHeight() * SCALE * 3f));
                            getPosition(gb.getX(), gb.getY()).setOccupied(true);
                            thisTask.cancel();
                        } else {
                            gb.setY(gb.getY() + gb.getVelocity());
                            gb.setVelocity(gb.getVelocity() + 10);
                        }
                        break;
                }
            }
        });

    }

}