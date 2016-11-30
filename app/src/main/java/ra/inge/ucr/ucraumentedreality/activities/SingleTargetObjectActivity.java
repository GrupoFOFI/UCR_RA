package ra.inge.ucr.ucraumentedreality.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.Vuforia.VideoPlayback.app.VideoPlayback.VideoPlayback;
import ra.inge.ucr.ucraumentedreality.adapters.CustomBottomSheetDialog;

/**
 * <h1>SingleTargetObjectActivity</h1>
 *
 * @author konrad
 * @version 1.0
 * @since 23/11/2016
 */
public class SingleTargetObjectActivity extends AppCompatActivity {

    /**
     * The target found
     */
    private TargetObject targetFound;
//    private CustomBottomSheetDialog bottomSheetDialog;

    /***
     * Method that initializes the main content used by the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_target_object);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        int objectId = bundle.getInt("objectId");
        targetFound = Data.targetObjects.get(objectId - 1);
        Log.d("konrad", targetFound.getName());
//
//        bottomSheetDialog = new CustomBottomSheetDialog();
//        bottomSheetDialog.setOnButtonInteractionListener(this);
        if (targetFound != null) {

            setTitle(targetFound.getName());

            FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fabDriveMe);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  bottomSheetDialog.show(getSupportFragmentManager(), "Custom Bottom Sheet");

                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("TargetName", targetFound.getName());
                    startActivity(intent);
                }
            });

            ImageView targetImageView = (ImageView) findViewById(R.id.targetImageView);
            targetImageView.setImageResource(targetFound.getImage());

            TextView nameTextView = (TextView) findViewById(R.id.targetName);
            nameTextView.setText(targetFound.getName());

            TextView descriptionTextView = (TextView) findViewById(R.id.targetDescription);
            descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
            descriptionTextView.setText(targetFound.getDescription());

            TextView websiteTextView = (TextView) findViewById(R.id.targetWebsite);
            websiteTextView.setText("http://www." +  targetFound.getWebsite());

            websiteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www." + targetFound.getWebsite()));
                    startActivity(browserIntent);
                }
            });

        }
    }

//
//    /**
//     * Method that enables the user to choose google maps
//     */
//    @Override
//    public void onMapsChosen() {
//        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//        intent.putExtra("TargetName", targetFound.getName());
//        startActivity(intent);
//    }
//
//    /**
//     * Method that enables the user to choose vuforia
//     */
//    @Override
//    public void onVuforiaChosen() {
//        Intent intent = new Intent(getApplicationContext(), VideoPlayback.class);
//        startActivity(intent);
//
//    }

}
