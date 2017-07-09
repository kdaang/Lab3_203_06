package uwaterloo.ca.lab4_203_06;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.Vector;

public class GameBlock extends AppCompatImageView {
    float velocity = 0, blockx, blocky;
    public final float OFFSET_X = -68, OFFSET_Y = 87;
    public final float SCALE = .65f;
    Timer time;
    ImageView gameBoard;
    RelativeLayout rl;
    int srcImg;
    Context ctx;
    Activity act;
    Position[][] positions;

    public GameBlock(RelativeLayout rl, int img, float x, float y, Context ctx, Timer time, ImageView gameBoard, Activity act, Position[][] positions) {
        super(ctx);
        setImageResource(img);
        setX(OFFSET_X+x);
        setY(OFFSET_Y+y);
        setScaleX(SCALE);
        setScaleY(SCALE);
        rl.addView(this);
        this.ctx=ctx;
        srcImg=img;
        this.rl=rl;
        this.time = time;
        this.blockx=x;
        this.blocky=y;
        this.gameBoard=gameBoard;
        this.act=act;
        this.positions=positions;
    }

    public void move(String dir) {
        velocity = 0;
        time.schedule(new GameLoopTask(dir, this, gameBoard, blockx,blocky, SCALE,act, positions), 25, 25);
    }



    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float v) {
        velocity = v;
    }

    public Context getCtx() {
        return ctx;
    }

    public RelativeLayout getRl() {
        return rl;
    }

    public int getSrcImg() {
        return srcImg;
    }

    public Timer getTime() {
        return time;
    }
}

