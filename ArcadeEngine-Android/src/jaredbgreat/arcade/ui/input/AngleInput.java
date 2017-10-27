package jaredbgreat.arcade.ui.input;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 *
 * @author Jared Blackburn
 */
public class AngleInput implements SensorEventListener {
    final Sensor orientation;
    final float[] angles;
    
    
    public AngleInput(Context context) {
        angles = new float[3];
        SensorManager manager = (SensorManager)context
                .getSystemService(Context.SENSOR_SERVICE);
        if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0) {
            orientation = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            manager.registerListener(this, orientation, 
                    SensorManager.SENSOR_DELAY_GAME);
        } else {
            orientation = null;
        }
    }
    

    @Override
    public void onSensorChanged(SensorEvent e) {
        angles[0] = e.values[0];
        angles[1] = e.values[1];
        angles[2] = e.values[2];
    }
    
    
    public void getAngles(float[] avector) {
        avector[3] = angles[0];
        avector[4] = angles[1];
        avector[5] = angles[2];
    }
    
    
    public float[] getAngles() {
        return angles;
    }
    
    
    public float getYaw() {
        return angles[0];
    }
    
    
    public float getPitch() {
        return angles[1];
    }
    
    
    public float getRoll() {
        return angles[2];
    }
    
    
    /*-------------------------------------------------------------------------/
     *                          UNUSED METHODS                                 /    
     *------------------------------------------------------------------------*/
    

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {/*Do nothing*/}
}
