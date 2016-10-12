package ra.inge.ucr.location.listener;

/**
 * Created by tete on 10/12/16.
 */
public interface OnDeviceRotationUpdateListener {
    /**
     * Called when sensors receive updated device rotation.
     *
     * @param rotationVector the x, y and z components of the rotation vector
     */
    public void onRotationUpdate(float[] rotationVector);
}
