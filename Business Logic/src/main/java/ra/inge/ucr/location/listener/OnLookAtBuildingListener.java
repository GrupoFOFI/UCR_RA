package ra.inge.ucr.location.listener;

import ra.inge.ucr.da.Edificio;

/**
 * Created by tete on 10/12/16.
 */
public interface OnLookAtBuildingListener {
    public void onStartLookingAtBuilding(Edificio edificio);
    public void onStopLookingAtBuilding(Edificio edificio);
}
