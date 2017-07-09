package uwaterloo.ca.lab4_203_06;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import java.util.Random;
import java.util.TimerTask;
import java.util.Vector;

public class GameLoopTask extends TimerTask {
    String direction;
    Random rnd = new Random();
    GameBlock gb;
    float BLOCKX,BLOCKY, SCALE;
    ImageView gameBoard;
    Position[][] positions = new Position[4][4];
    Activity thisActivity;
    GameLoopTask thisTask=this;
    Vector<GameBlock> blocks;
    public final float ORIGIN_X = -38, ORIGIN_Y = 117;
    public GameLoopTask(String dir, GameBlock gb, ImageView gameBoard, float blockx, float blocky, float SCALE, Activity act, Vector<GameBlock> blocks){
        direction=dir;
        this.gb=gb;
        BLOCKX=blockx;
        BLOCKY=blocky;
        this.gameBoard=gameBoard;
        BLOCKX = gb.getX();
        BLOCKY = gb.getY();
        this.SCALE = SCALE;
        thisActivity= act;
        this.blocks=blocks;
        Log.d("GAMEBOARD", String.format("BLOCK.XY, %f, %f", BLOCKX, BLOCKY));
        assign();
    }
    public void assign() {
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                positions[i][j] = new Position(ORIGIN_X+4 + (gb.getWidth() * SCALE * j), ORIGIN_Y-4 +(gb.getHeight()*SCALE*i), false);
                Log.d("Position","X: "+positions[i][j].getX()+ "Y: "+positions[i][j].getY());
            }
        }
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
                            Position rndPos = getRandPos();
                            Log.d("RandPos", "X: "+rndPos.getX()+" Y: "+rndPos.getY());
                            createBlock(rndPos.getX(), rndPos.getY());
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
                            Position rndPos = getRandPos();
                            createBlock(rndPos.getX(), rndPos.getY());
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
                            Position rndPos = getRandPos();
                            createBlock(rndPos.getX(), rndPos.getY());
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
                            Position rndPos = getRandPos();
                            createBlock(rndPos.getX(), rndPos.getY());
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


    public Position getRandPos(){
        Position tmpPos=positions[rnd.nextInt(4)][rnd.nextInt(4)];
        if (tmpPos.isOccupied()){
            return getRandPos();
        }else {
            return tmpPos;
        }
    }
    private void createBlock(float x, float y) {
       blocks.add(new GameBlock(gb.getRl(),gb.getSrcImg(),x ,y ,gb.getCtx(),gb.getTime(),gameBoard,thisActivity,blocks));
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