package ra.inge.ucr.ucraumentedreality.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.ucraumentedreality.R;

public class SingleTargetObjectActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        int objectId = bundle.getInt("objectId");
        final TargetObject targetFound = Data.targetObjects.get(objectId - 1);
        Log.d("konrad", targetFound.getName());

        setContentView(R.layout.activity_single_target_object);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fabDriveMe);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("TargetName", targetFound.getName());
                startActivity(intent);
            }
        });
    }

}
