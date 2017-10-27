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
public class AcceloInput implements SensorEventListener {
    final Sensor accelorometer;
    final float[] accel;
    
    
    public AcceloInput(Context context) {
        accel = new float[3];
        SensorManager manager = (SensorManager)context
                .getSystemService(Context.SENSOR_SERVICE);
        if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0) {
            accelorometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            manager.registerListener(this, accelorometer, 
                    SensorManager.SENSOR_DELAY_GAME);
        } else {
            accelorometer = null;
        }
    }
    

    @Override
    public void onSensorChanged(SensorEvent e) {
        accel[0] = e.values[0];
        accel[1] = e.values[1];
        accel[2] = e.values[2];
    }
    
    
    public void getAngles(float[] avector) {
        avector[0] = accel[0];
        avector[1] = accel[1];
        avector[2] = accel[2];
    }
    
    
    public float[] getAccel() {
        return accel;
    }
    
    
    public float getAx() {
        return accel[0];
    }
    
    
    public float getAy() {
        return accel[1];
    }
    
    
    public float getAz() {
        return accel[2];
    }
    
    
    /*-------------------------------------------------------------------------/
     *                          UNUSED METHODS                                 /    
     *------------------------------------------------------------------------*/
    

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {/*Do nothing*/}
}
