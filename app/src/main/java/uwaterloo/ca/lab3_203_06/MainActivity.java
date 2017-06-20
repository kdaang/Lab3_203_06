package uwaterloo.ca.lab3_203_06;

import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    //Declaring class-wide fields
    SensorEventListener accel;
    Vector<float[]> accelData = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3_203_06);

        //Declares and initializes the linear layout used in the application
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_lab3_203_06);

        //Initializes a blank label to display the direction of the gesture
        TextView dirLbl = makeLabel(rl, "NONE",0,500);
        dirLbl.setTextSize(32);


        //ACCEL

        //Creates a label that indicates the current accelerometer reading
        ImageView gameBoard = makeLabel(rl, getDrawable(R.drawable.gameboard), 0, -200);

        ImageView gameBlock = makeLabel(rl, getDrawable(R.drawable.gameblock),-42,-35);
        gameBlock.setScaleX(.57f);
        gameBlock.setScaleY(.57f);

        //Registers and assigns listeners and managers to the linear accelerometer
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        TextView accelSensorLbl = makeLabel(rl, "",0,350);
        Sensor accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        accel = new MySensorEventListener(accelSensorLbl, accelData, dirLbl);
        sensorManager.registerListener(accel, accelSensor, SensorManager.SENSOR_DELAY_GAME);

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
    private ImageView makeLabel (RelativeLayout rl, Drawable img, float x, float y){
        ImageView iv = new ImageView(getApplicationContext());
        iv.setImageDrawable(img);
        iv.setY(y);
        iv.setX(x);
        rl.addView(iv);
        return iv;
    }




}


