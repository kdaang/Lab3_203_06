package uwaterloo.ca.lab4_203_06;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;




public class MainActivity extends AppCompatActivity {

    //Declaring class-wide fields
    SensorEventListener accel;
    Vector<float[]> accelData = new Vector<>();
    Vector<GameBlock>  blocks = new Vector<>();
    public final float BOARDX = 0, BOARDY = -200, BLOCKX = 0, BLOCKY = 0;
    public final float ORIGIN_X = -38, ORIGIN_Y = 117;
    public final float SCALE = .65f;
    ImageView gameBoard;
    Timer time = new Timer();
    RelativeLayout rl;
    final Activity activity = this;
    Position[][] positions = new Position[4][4];
    Random rnd = new Random();
    int[][] moves = new int[4][4];
    boolean legal;
//    int[][] occMatrix = new int[4][4];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_203_06);

        //Declares and initializes the linear layout used in the application
        rl = (RelativeLayout) findViewById(R.id.activity_lab3_203_06);

        //Initializes a blank label to display the direction of the gesture
        TextView dirLbl = makeLabel(rl, "Left", 700, 1200);
        dirLbl.setTextSize(32);
        dirLbl.setTextColor(Color.RED);
        TextView l = makeLabel(rl, "Left", 0, 1200);
        l.setTextSize(32);
        TextView r = makeLabel(rl, "Right", 0, 1400);
        r.setTextSize(32);
        TextView u = makeLabel(rl, "Up", 500, 1200);
        u.setTextSize(32);
        TextView d = makeLabel(rl, "Down", 500, 1400);
        d.setTextSize(32);

        //Creates a label that indicates the current accelerometer reading
        gameBoard = makeLabel(rl, R.drawable.gameboard, BOARDX, BOARDY);
        assign();
        positions[0][0].setOccupied(true);
        final GameBlock gameBlock = new GameBlock(rl, R.drawable.gameblock, positions[0][0].getX(), positions[0][0].getY(),
                getApplicationContext(), time, gameBoard, this, positions, positions[0][0].getBlocknum_x(),positions[0][0].getBlocknum_y(),2);
        blocks.add(gameBlock);

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveAll("LEFT");
                time.schedule(rndBlockTask(), 25, 25);
                //moveAllLeft();
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveAll("RIGHT");
                time.schedule(rndBlockTask(), 25, 25);
                //blocks.add(genRandGameBlock());
                //moveAllRight();
            }
        });
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveAll("UP");
                time.schedule(rndBlockTask(), 25, 25);
                //blocks.add(genRandGameBlock());
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveAll("DOWN");
                time.schedule(rndBlockTask(), 25, 25);
                //blocks.add(genRandGameBlock());
            }
        });


        //Registers and assigns listeners and managers to the linear accelerometer
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        TextView accelSensorLbl = makeLabel(rl, "", 0, 350);
        Sensor accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        accel = new MySensorEventListener(accelSensorLbl, accelData, dirLbl, gameBlock);
        sensorManager.registerListener(accel, accelSensor, SensorManager.SENSOR_DELAY_GAME);

    }
    public TimerTask rndBlockTask(){
        return new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable(){
                    public void run(){
                        if(isDoneMov()){

//                            for(int row = 0; row < 4; row ++){
//                                for(int col = 0; col < 4; col ++){
//                                    occMatrix[row][col] = 0;
//                                }
//                            }
//
//                            for(GameBlock e : blocks){
//                                occMatrix[e.getBlocknum_y()][e.getBlocknum_x()] ++;
//                            }
//
//                            for(int row = 0; row < 4; row ++){
//                                for(int col = 0; col < 4; col ++){
//                                    if(!positions[row][col].isOccupied()){
//
//                                    }
//                                }
//                            }



                            for(int row = 0; row < 4; row ++){
                                for(int col = 0; col < 4; col ++){
                                    positions[row][col].setOccupied(false);
                                    positions[row][col].setBlock(null);
                                }
                            }
                            Vector<GameBlock> rem = new Vector<GameBlock>();
                            for(GameBlock e : blocks){
                                if(positions[e.getBlocknum_y()][e.getBlocknum_x()].isOccupied()){
                                    positions[e.getBlocknum_y()][e.getBlocknum_x()].getBlock().twice();
                                    positions[e.getBlocknum_y()][e.getBlocknum_x()].setNum(2*e.getCurrBlockNum());
                                    //Log.d("MERGE", e.getBlocknum_y() + "" + e.getBlocknum_x() + "" + e.getCurrBlockNum());
                                    e.setVisibility(View.INVISIBLE);
                                    e.getCurrNum().setVisibility(View.INVISIBLE);
                                    rem.add(e);
                                }else{
                                    positions[e.getBlocknum_y()][e.getBlocknum_x()].setBlock(e);
                                    positions[e.getBlocknum_y()][e.getBlocknum_x()].setOccupied(true);
                                }
                            }
                            for(GameBlock e : rem){
                                blocks.remove(e);
                            }




                            if(legal) {
                                blocks.add(genRandGameBlock());
                                if(blocks.size() == 16){
                                    getStop("LEFT");
                                    if(!checkLegal()){
                                        getStop("RIGHT");
                                        if(!checkLegal()){
                                            getStop("UP");
                                            if(!checkLegal()){
                                                getStop("DOWN");
                                                if(!checkLegal()){
                                                    //YOU LOSE
                                                    Toast.makeText(activity, "YOU LOSE!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                            cancel();
                        }
                    }
                });
            }
        };
    }
    public boolean isDoneMov(){
        for(GameBlock e: blocks){
            if(!e.isDoneMov()){
                return false;
            }
        }
        return true;
    }
    private void assign() {
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                positions[i][j] = new Position(255*j ,255*i, j, i, false,-1);

                //makeLabel(rl,R.drawable.red,-490+(255*j),-1325+(255*i));
                //Log.d("Position","Width: "+gameBoard.getMeasuredWidth());
                //Log.d("Position","X: "+positions[i][j].getX()+ "Y: "+positions[i][j].getY());
            }
        }
    }



    //A method that creates a label with specific text and adds it to a specified layout
    private TextView makeLabel(RelativeLayout rl, String text, float x, float y) {
        TextView tv1 = new TextView(getApplicationContext());
        tv1.setText(text);
        tv1.setX(x);
        tv1.setY(y);
        rl.addView(tv1);
        return tv1;
    }

    private ImageView makeLabel(RelativeLayout rl, int img, float x, float y) {
        ImageView iv = new ImageView(getApplicationContext());
        iv.setImageResource(img);
        iv.setY(y);
        iv.setX(x);
        rl.addView(iv);
        return iv;
    }
    private void moveAll(String dir){
        getStop(dir);
        for (GameBlock e : blocks){
            if(dir=="RIGHT"){
                e.setStop(moves[e.getBlocknum_y()][e.getBlocknum_x()]+e.getBlocknum_x());
            }
            else if (dir == "LEFT"){
                e.setStop(-moves[e.getBlocknum_y()][e.getBlocknum_x()]+e.getBlocknum_x());
            }
            else if (dir == "UP"){
                e.setStop(-moves[e.getBlocknum_y()][e.getBlocknum_x()]+e.getBlocknum_y());
            }
            else if (dir == "DOWN"){
                e.setStop(moves[e.getBlocknum_y()][e.getBlocknum_x()]+e.getBlocknum_y());
            }

            //e.move(dir);
        }
        for (GameBlock e : blocks){
            e.move(dir);
        }
    }
//    public void moveStuff(){
//        for(GameBlock e : blocks){
//            //detail
//        }
//    }

    public void getStop(String dir){
        switch (dir){
            case "RIGHT":
                for(int x = 0; x < 4; x++){
                    int counter=0, last=0;
                    for(int y = 3; y >= 0; y--){
                        if(positions[x][y].isOccupied()){
                            if(positions[x][y].getNum()==last){
                                counter++;
                                last=0;
                            }
                            else{
                                last=positions[x][y].getNum();
                            }
                            moves[x][y]=counter;

                        }
                        else {
                            counter++;
                        }
                    }
                }
                break;
            case "LEFT":
                for(int x = 0; x < 4; x++){
                    int counter=0, last=0;
                    for(int y = 0; y < 4; y++){
                        if(positions[x][y].isOccupied()){
                            if(positions[x][y].getNum()==last){
                                counter++;
                                last=0;
                            }
                            else{
                                last=positions[x][y].getNum();
                            }
                            moves[x][y]=counter;
                        }
                        else {
                            counter++;
                        }
                    }
                }
                break;
            case "UP":
                for(int col = 0; col < 4; col++){
                    int counter=0, last=0;
                    for(int row = 0; row < 4;row++){
                        if(positions[row][col].isOccupied()){
                            if(positions[row][col].getNum()==last){
                                counter++;
                                last=0;
                            }
                            else {
                                last = positions[row][col].getNum();
                            }

                            moves[row][col]=counter;

                            Log.d("SURPRISE", "row: " + row + "\tcol:" + col + "\tcount: " + counter);
                            Log.d("FUKK SURPRISE", "" + moves[0][0]);
                        }
                        else {
                            counter++;
                        }
                        Log.d("Stuff", "row: " + row + "\tcol:" + col + "\tcount: " + counter + "\tlast: " + last);
                    }
                }
                break;
            case "DOWN":
                for(int y = 0; y < 4; y++){
                    int counter=0, last=0;
                    for(int x = 3; x >= 0 ;x--){
                        if(positions[x][y].isOccupied()){
                            if(positions[x][y].getNum()==last){
                                counter++;
                                last=0;
                            }
                            else{
                                last=positions[x][y].getNum();
                            }
                            moves[x][y]=counter;
                        }
                        else {
                            counter++;
                        }
                    }
                }
                break;
            default:
                break;
        }

        Log.d("FUKK SURPRISE", "" + moves[0][0]);
        for(int i = 0; i < 4; i ++){
            for(int j = 0; j < 4; j ++){
                Log.d("CheckMoves",""+ moves[i][j]);
            }
        }

        legal = checkLegal();
    }

    public boolean checkLegal(){
        for(GameBlock e : blocks){
            if(moves[e.getBlocknum_y()][e.getBlocknum_x()] != 0) {
                return true;
            }
        }
        return false;
    }
    /*private void moveAll(String dir){
        for (GameBlock e : blocks){
            e.setStop(getStop(dir, e));
            e.move(dir);
        }
        for (GameBlock e : blocks){
            e.move(dir);
        }
    }
    public int getStop(String dir, GameBlock gb){
        switch (dir){
            case "RIGHT":
                int pos_xr = gb.getBlocknum_x();
                int counter = 0;
                for(int i = pos_xr+1; i < 4; i++){
                    if(positions[gb.getBlocknum_y()][i].isOccupied() == false){
                        counter++;
                    }
                }
                return counter+pos_xr;
            case "LEFT":
                int pos_xl = gb.getBlocknum_x();
                int counter2 = 0;
                for(int i = pos_xl-1; i >= 0; i--){
                    if(positions[gb.getBlocknum_y()][i].isOccupied() == false){
                        counter2++;
                    }
                }
                return pos_xl-counter2;
            case "UP":
            int pos_yu = gb.getBlocknum_y();
            int counter3 = 0;
            for(int i = pos_yu-1; i >= 0; i--){
                if(positions[i][gb.getBlocknum_x()].isOccupied() == false){
                    counter3++;
                }
            }
                return pos_yu-counter3;
            case "DOWN":
            int pos_yd = gb.getBlocknum_y();
            int counter4 = 0;
            for(int i = pos_yd+1; i < 4; i++){
                if(positions[i][gb.getBlocknum_x()].isOccupied() == false){
                    counter4++;
                }
            }
            return counter4+pos_yd;
            default:
                return 0;
        }
    }*/
    /*private void moveAllLeft(){
        for(int y = 0; y < 4; y++){
            for(int x = 0 ; x <4; x++){
                if(positions[y][x].isOccupied()){
                    for (GameBlock e : blocks){
                        if((e.getBlocknum_x() == x)&&(e.getBlocknum_y()==y)){
                            e.move("LEFT");
                            break;
                        }
                    }
                }
            }
        }
    }*/
    /*private void moveAllRight(){
        for(int y = 0; y < 4; y++){
            for(int x = 3 ; x >= 0; x--){
                if(positions[y][x].isOccupied()){
                    for (GameBlock e : blocks){
                        if((e.getBlocknum_x() == x)&&(e.getBlocknum_y()==y)){
                            e.move("RIGHT");
                            break;
                        }
                    }
                }
            }
        }
    }*/
    public GameBlock genRandGameBlock(){

        Position tmpPos = getRandPos();

        int tmp = 2 * rnd.nextInt(2) + 2;

        tmpPos.setNum(tmp);
        return new GameBlock(rl, R.drawable.gameblock, tmpPos.getX(), tmpPos.getY(),
                getApplicationContext(), time, gameBoard, activity, positions,
                tmpPos.getBlocknum_x(), tmpPos.getBlocknum_y(), tmp);

    }
    public Position getRandPos(){
        int randx = rnd.nextInt(4);
        int randy = rnd.nextInt(4);
        Position tmpPos=positions[randx][randy];
        while(tmpPos.isOccupied()){
            randx = rnd.nextInt(4);
            randy = rnd.nextInt(4);
            tmpPos = positions[randx][randy];
        }
        positions[randx][randy].setOccupied(true);
        Log.d("Position","BLOCK_x,y: " +randy+" " + randx);
        for (int i = 0; i < 4; i++) {
            for(int j=0;j<4;j++){
                Log.d("Position","BLOCK_: " + j + ", " + i +" " + positions[i][j].isOccupied());
            }
        }
        Log.d("Position","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return tmpPos;
        /*if (tmpPos.isOccupied() == true){
            return getRandPos();
        }else {
            positions[randy][randx].setOccupied(true);
            Log.d("Position","BLOCK_x,y: " +randx+" " + randy);
            for (int i = 0; i < 4; i++) {
                for(int j=0;j<4;j++){
                    Log.d("Position","BLOCK_: " + j + ", " + i +" " + positions[j][i].isOccupied());
                }
            }
            Log.d("Position","~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return tmpPos;
        }*/
    }





}







