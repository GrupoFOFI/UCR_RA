package ra.inge.ucr.ucraumentedreality.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.ucraumentedreality.R;

public class SingleTargetObjectActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle =  getIntent().getExtras();
        int objectId = bundle.getInt("objectId");
        setObject(objectId);
        setContentView(R.layout.activity_single_target_object);
    }

    void  setObject(int objectId) {
        TargetObject targetFound = Data.targetObjects.get(objectId-1);
        Log.d("konrad", targetFound.getName());


    }
}
