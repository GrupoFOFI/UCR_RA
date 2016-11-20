package ra.inge.ucr.location.listener;

import ra.inge.ucr.da.entity.TargetObject;

/**
 * <h1> OnLookAtTargetListener </h1>
 * <p>
 * Interface used to create a callback when the user points to a building
 * </p?>
 */
public interface OnLookAtTargetListener {

    /**
     * Method called when the user starts looking to a building
     *
     * @param targetObject
     */
    public void onStartLookingAtBuilding(TargetObject targetObject);

    /**
     * Method called when the user stops looking to a target
     *
     * @param targetObject
     */
    public void onStopLookingAtBuilding(TargetObject targetObject);
}
