package ra.inge.ucr.location;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.location.listener.OnLookAtTargetListener;

/**
 * <h1> Sensor Helper </h1>
 *
 * <p>
 *  Fragment to handle the points of Interest using Wikitude
 * </p>
 *
 *
 * @author Fofis
 * @version 1.0
 * @since 1.0
 */
public class SensorHelper implements SensorEventListener {

    private SensorManager snsrmngr;
    private Sensor accl, mgnt;
    private OnLookAtTargetListener mListener;
    TargetObject mBuilding = null;

    float[] acclReading = new float[3], mgntReading = new float[3];
    float[] orientationAngles = new float[3];
    float[] rotationVector = new float[3];
    float[] iMat = new float[9];
    float[] rMat = new float[9];
    private int mAzimuth = 0; // degree

//    LocationHelper locationHelper = new LocationHelper();

    public SensorHelper(Context context) {
        // start sensor
        snsrmngr = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        accl = snsrmngr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mgnt = snsrmngr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    /**
     * Sets the rotation update listener to receive rotation updates
     *
     * @param listener the class implenenting OnDeviceRotationListener interface
     */
    public void setOnLookAtBuildingListener(OnLookAtTargetListener listener) {
        mListener = listener;
    }

    /**
     * Starts checking for sensor updates
     */
    public void start() {
        if (sensorsSupported()) {
            snsrmngr.registerListener(this, snsrmngr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            snsrmngr.registerListener(this, snsrmngr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
        }/* else if (snsrmngr.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) { // check for deprecated orientation sensor

        }*/
    }

    /**
     * Stops checking for sensor updates
     */
    public void stop() {
        if (sensorsSupported()) {
            snsrmngr.unregisterListener(this, accl);
            snsrmngr.unregisterListener(this, mgnt);
        }
    }

    /**
     * Checks if device has accelerometer and magnetic field sensors
     *
     * @return true if device has accelerometer and magnetic field sensors, otherwise false
     */
    public boolean sensorsSupported() {
        return (accl != null && mgnt != null);
    }

    /**
     * Method that creates the logic when a sensor changes
     *
     * @param sensorEvent
     */
    @Override
    public synchronized void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == accl.getType()) {
            acclReading = sensorEvent.values;
        } else if (sensorEvent.sensor.getType() == mgnt.getType()) {
            mgntReading = sensorEvent.values;
        }
        // Rotation matrix based on current readings from accelerometer and magnetometer.
        final float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrix(rotationMatrix, null, acclReading, mgntReading);

        orientationAngles = SensorManager.getOrientation(rotationMatrix, orientationAngles);
        rotationVector[0] = (float)Math.sin(orientationAngles[0]);// x
        rotationVector[1] = (float)Math.cos(orientationAngles[1]);// y

        // z doesn't matter
        TargetObject ed =null;
        if(LocationHelper.getLastLocation()!= null) {
//            ed = locationHelper.pointingCamera((double) rotationVector[0], (double) rotationVector[1], LocationHelper.getLastLocation());
            ed = null;
        }
        if (mListener != null) {
            if (mBuilding != ed) {
                if (mBuilding != null)
                    mListener.onStopLookingAtBuilding(mBuilding);
                mBuilding = ed;
                if (ed != null)
                    mListener.onStartLookingAtBuilding(ed);
            }
        }
        if ( SensorManager.getRotationMatrix( rMat, iMat, acclReading, mgntReading ) ) {
            mAzimuth= (int) ( Math.toDegrees( SensorManager.getOrientation( rMat, orientationAngles )[0] ) + 360 ) % 360;
        }
        //if (mListener != null) mListener.onRotationUpdate(rotationVector);
    }

    /**
     * Method used to notify when a sensor changes its accuracy
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public int getAzimuth(){
        return mAzimuth;
    }
}
