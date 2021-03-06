/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.


Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package ra.inge.ucr.ucraumentedreality.Vuforia.VideoPlayback.app.VideoPlayback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vuforia.CameraDevice;
import com.vuforia.DataSet;
import com.vuforia.HINT;
import com.vuforia.ObjectTracker;
import com.vuforia.STORAGE_TYPE;
import com.vuforia.State;
import com.vuforia.Tracker;
import com.vuforia.TrackerManager;
import com.vuforia.Vuforia;

import java.util.Vector;

import ra.inge.ucr.da.Data;
import ra.inge.ucr.da.entity.TargetObject;
import ra.inge.ucr.ucraumentedreality.R;
import ra.inge.ucr.ucraumentedreality.Vuforia.SampleApplicationControl;
import ra.inge.ucr.ucraumentedreality.Vuforia.SampleApplicationException;
import ra.inge.ucr.ucraumentedreality.Vuforia.SampleApplicationSession;
import ra.inge.ucr.ucraumentedreality.Vuforia.VideoPlayback.app.VideoPlayback.VideoPlayerHelper.MEDIA_STATE;
import ra.inge.ucr.ucraumentedreality.Vuforia.utils.LoadingDialogHandler;
import ra.inge.ucr.ucraumentedreality.Vuforia.utils.SampleApplicationGLView;
import ra.inge.ucr.ucraumentedreality.Vuforia.utils.Texture;
import ra.inge.ucr.ucraumentedreality.fragments.LatestRecognitionFragment;


// The AR activity for the VideoPlayback sample.
public class VideoPlayback extends AppCompatActivity implements SampleApplicationControl, OnTrackListener, View.OnClickListener {

    private static final String BIG_DATASET = "Edificios_Monumentos.xml";
    private static final String LITTLE_DATASET = "Dataset_Test.xml";
    private static final String LITE_DATASET = "LightDataSet.xml";
    private static final String MEDIUM_DATASET = "MediumDataSet.xml";

    private static final String LOGTAG = "VideoPlayback";
    public static final String DEBUG_TAG = "konri";

    SampleApplicationSession vuforiaAppSession;
    Activity mActivity;

    // Helpers to detect events such as double tapping:
    private GestureDetector mGestureDetector = null;
    private SimpleOnGestureListener mSimpleListener = null;

    // Movie for the Targets:
    public static final int NUM_TARGETS = Data.targetObjects.size();
    private VideoPlayerHelper mVideoPlayerHelper[] = null;
    private int mSeekPosition[] = null;
    private boolean mWasPlaying[] = null;
//    private String mMovieName[] = null;

    // A boolean to indicate whether we come from full screen:
    private boolean mReturningFromFullScreen = false;

    // Our OpenGL view:
    private SampleApplicationGLView mGlView;

    // Our renderer:
    private VideoPlaybackRenderer mRenderer;

    // The textures we will use for rendering:
    private Vector<Texture> mTextures;

    DataSet ucrTargetDataset = null;

    private RelativeLayout mUILayout;

    private boolean mPlayFullscreenVideo = false;

    private LoadingDialogHandler loadingDialogHandler = new LoadingDialogHandler(this);

    // Alert Dialog used to display SDK errors
    private AlertDialog mErrorDialog;

    boolean mIsInitialized = false;

    // Custom UI elements
    private LinearLayout northLayout;
    private LinearLayout southLayout;
    private LinearLayout linearLayout;
    private TextView titleTextView;
    private TextView descriptionTextView;

    private boolean isSomeonePlaying = false;

    /**
     * Arrows used to guide the user
     */
    private ImageView arrowUp, arrowLeft, arrowRight;


    @Override
    public void onTargetFound(int targetId) {

        Log.d(DEBUG_TAG, "Voy a intentar hacer play");
        if (!mVideoPlayerHelper[targetId].getmMediaPlayer().isPlaying()) {
            Log.d(DEBUG_TAG, "Vamoooos");
            TargetObject targetObject = Data.targetObjects.get(targetId);
            LatestRecognitionFragment.targetObjects.add(targetObject);
            mVideoPlayerHelper[targetId].play(mPlayFullscreenVideo, mSeekPosition[0]);



        }
    }


    // Called when the activity first starts or the user navigates back
    // to an activity.
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vuforia_ui);
        vuforiaAppSession = new SampleApplicationSession(this);

        mActivity = this;

        this.setTitle("Vufofia");
        startLoadingAnimation();

        vuforiaAppSession
                .initAR(this, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Load any sample specific textures:
        mTextures = new Vector<Texture>();
        loadTextures();

        // Create the gesture detector that will handle the single and
        // double taps:
        mSimpleListener = new SimpleOnGestureListener();
        mGestureDetector = new GestureDetector(getApplicationContext(),
                mSimpleListener);

        mVideoPlayerHelper = new VideoPlayerHelper[NUM_TARGETS];
        mSeekPosition = new int[NUM_TARGETS];
        mWasPlaying = new boolean[NUM_TARGETS];
//        mMovieName = new String[NUM_TARGETS];

        // Create the video player helper that handles the playback of the movie
        // for the targets:
        for (int i = 0; i < NUM_TARGETS; i++) {
            mVideoPlayerHelper[i] = new VideoPlayerHelper();
            mVideoPlayerHelper[i].init();
            mVideoPlayerHelper[i].setActivity(this);
        }

//        mMovieName[0] = "VideoPlayback/antarticos.mp4";
//        mMovieName[1] = "VideoPlayback/osos.mp4";
//        mMovieName[2] = "VideoPlayback/mate.mp4";
//        mMovieName[3] = "VideoPlayback/leda.mp4";
//        mMovieName[4] = "VideoPlayback/24-abril.mp4";
//        mMovieName[5] = "VideoPlayback/carlos-monge.mp4";
//        mMovieName[6] = "VideoPlayback/comedor.mp4";
//        mMovieName[7] = "VideoPlayback/joaquin.mp4";
//        mMovieName[8] = "VideoPlayback/derecho.mp4";
//        mMovieName[9] = "VideoPlayback/ecci.mp4";
//        mMovieName[10] = "VideoPlayback/generales.mp4";
//        mMovieName[11] = "VideoPlayback/fernando.mp4";
//        mMovieName[12] = "VideoPlayback/centro-info.mp4";
//        mMovieName[13] = "VideoPlayback/quebrada_negritos.mp4";
//        mMovieName[14] = "VideoPlayback/juan_maria.mp4";
//        mMovieName[14] = "VideoPlayback/juan_maria.mp4";

        // Set the double tap listener:
        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            public boolean onDoubleTap(MotionEvent e) {
                // We do not react to this event
                return false;
            }

            public boolean onDoubleTapEvent(MotionEvent e) {
                // We do not react to this event
                return false;
            }


            // Handle the single tap
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return isReallyTapped(e);
            }
        });
    }


    // We want to load specific textures from the APK, which we will later
    // use for rendering.
    private void loadTextures() {
        mTextures.add(Texture.loadTextureFromApk("VideoPlayback/VuforiaSizzleReel_1.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk("VideoPlayback/VuforiaSizzleReel_2.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk("VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk("VideoPlayback/busy.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk("VideoPlayback/error.png", getAssets()));
    }


    public boolean isReallyTapped(MotionEvent e) {

        boolean isSingleTapHandled = false;

        // Do not react if the StartupScreen is being displayed
        for (int i = 0; i < NUM_TARGETS; i++) {

            // Verify that the tap happened inside the target
            if (mRenderer != null && mRenderer.isTapOnScreenInsideTarget(i, e.getX(),
                    e.getY())) {

                // Check if it is playable on texture
                if (mVideoPlayerHelper[i].isPlayableOnTexture()) {

                    // We can play only if the movie was paused, ready
                    // or stopped
                    if ((mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.PAUSED)
                            || (mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.READY)
                            || (mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.STOPPED)
                            || (mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.REACHED_END)) {

                        // Pause all other media
                        pauseAll(i);

                        // If it has reached the end then rewind
                        if ((mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.REACHED_END))
                            mSeekPosition[i] = 0;

                        Log.d(DEBUG_TAG, "isReallyTapped?");
                        mVideoPlayerHelper[i].play(mPlayFullscreenVideo,
                                mSeekPosition[i]);
                        mSeekPosition[i] = VideoPlayerHelper.CURRENT_POSITION;
                    } else if (mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.PLAYING) {

                        // If it is playing then we pause it
                        mVideoPlayerHelper[i].pause();
                    }
                } else if (mVideoPlayerHelper[i].isPlayableFullscreen()) {

                    // If it isn't playable on texture
                    // Either because it wasn't requested or because it
                    // isn't supported then request playback fullscreen.
                    Log.d(DEBUG_TAG, "isReallyTapped2 ");
                    mVideoPlayerHelper[i].play(true,
                            VideoPlayerHelper.CURRENT_POSITION);
                }

                isSingleTapHandled = true;

                // Even though multiple videos can be loaded only one
                // can be playing at any point in time. This break
                // prevents that, say, overlapping videos trigger
                // simultaneously playback.
                break;
            }
        }
        return isSingleTapHandled;

    }

    // Called when the activity will start interacting with the user.
    public void onResume() {
        Log.d(LOGTAG, "onResume");
        super.onResume();

        try {
            vuforiaAppSession.resumeAR();
        } catch (SampleApplicationException e) {
            Log.e(LOGTAG, e.getString());
        }

        // Resume the GL view:
        if (mGlView != null) {
            mGlView.setVisibility(View.VISIBLE);
            mGlView.onResume();
        }

        // Reload all the movies
        if (mRenderer != null) {
            for (int i = 0; i < NUM_TARGETS; i++) {
                if (!mReturningFromFullScreen) {
                    mRenderer.requestLoad(i, Data.targetObjects.get(i).getVideo(), // mMovieName[i],
                            mSeekPosition[i],
                            false);
                } else {
                    mRenderer.requestLoad(i, Data.targetObjects.get(i).getVideo(),// mMovieName[i],
                            mSeekPosition[i],
                            mWasPlaying[i]);
                }
            }
        }

        mReturningFromFullScreen = false;
    }


    // Called when returning from the full screen player
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            if (resultCode == RESULT_OK) {
                // The following values are used to indicate the position in
                // which the video was being played and whether it was being
                // played or not:
                String movieBeingPlayed = data.getStringExtra("movieName");
                mReturningFromFullScreen = true;

                // Find the movie that was being played full screen
                for (int i = 0; i < NUM_TARGETS; i++) {
                    if (movieBeingPlayed.compareTo(Data.targetObjects.get(i).getVideo()) == 0) {
                        mSeekPosition[i] = data.getIntExtra(
                                "currentSeekPosition", 0);
                        mWasPlaying[i] = false;
                    }
                }
            }
        }
    }


    public void onConfigurationChanged(Configuration config) {
        Log.d(LOGTAG, "onConfigurationChanged");
        super.onConfigurationChanged(config);
        vuforiaAppSession.onConfigurationChanged();
    }


    // Called when the system is about to start resuming a previous activity.
    public void onPause() {
        Log.d(LOGTAG, "onPause");
        super.onPause();

        if (mGlView != null) {
            mGlView.setVisibility(View.INVISIBLE);
            mGlView.onPause();
        }

        // Store the playback state of the movies and unload them:
        for (int i = 0; i < NUM_TARGETS; i++) {
            // If the activity is paused we need to store the position in which
            // this was currently playing:
            if (mVideoPlayerHelper[i].isPlayableOnTexture()) {
                mSeekPosition[i] = mVideoPlayerHelper[i].getCurrentPosition();
                mWasPlaying[i] = false;
//                         mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.PLAYING ? true: false;
            }

            // We also need to release the resources used by the helper, though
            // we don't need to destroy it:
            if (mVideoPlayerHelper[i] != null)
                mVideoPlayerHelper[i].unload();
        }

        mReturningFromFullScreen = false;

        try {
            vuforiaAppSession.pauseAR();
        } catch (SampleApplicationException e) {
            Log.e(LOGTAG, e.getString());
        }
    }


    // The final call you receive before your activity is destroyed.
    public void onDestroy() {
        Log.d(LOGTAG, "onDestroy");
        super.onDestroy();

        for (int i = 0; i < NUM_TARGETS; i++) {
            // If the activity is destroyed we need to release all resources:
            if (mVideoPlayerHelper[i] != null)
                mVideoPlayerHelper[i].deinit();
            mVideoPlayerHelper[i] = null;
        }

        try {
            vuforiaAppSession.stopAR();
        } catch (SampleApplicationException e) {
            Log.e(LOGTAG, e.getString());
        }

        // Unload texture:
        mTextures.clear();
        mTextures = null;

        System.gc();
    }


    // Pause all movies except one
    // if the value of 'except' is -1 then
    // do a blanket pause
    private void pauseAll(int except) {
        // And pause all the playing videos:
        for (int i = 0; i < NUM_TARGETS; i++) {
            // We can make one exception to the pause all calls:
            if (i != except) {
                // Check if the video is playable on texture
                if (mVideoPlayerHelper[i].isPlayableOnTexture()) {
                    // If it is playing then we pause it
                    mVideoPlayerHelper[i].pause();
                }
            }
        }
    }


    private void startLoadingAnimation() {

        mUILayout = (RelativeLayout) View.inflate(this, R.layout.camera_overlay, null);

        mUILayout.setVisibility(View.VISIBLE);
        mUILayout.setBackgroundColor(Color.BLACK);

        // Gets a reference to the loading dialog
        loadingDialogHandler.mLoadingDialogContainer = mUILayout
                .findViewById(R.id.loading_indicator);

        // Shows the loading indicator at start
        loadingDialogHandler
                .sendEmptyMessage(LoadingDialogHandler.SHOW_LOADING_DIALOG);

        // Adds the inflated layout to the view
        addContentView(mUILayout, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }


    // Initializes AR application components.
    private void initApplicationAR() {
        // Create OpenGL ES view:
        int depthSize = 16;
        int stencilSize = 0;
        boolean translucent = true;//Vuforia.requiresAlpha();

        mGlView = new SampleApplicationGLView(this);
        mGlView.init(translucent, depthSize, stencilSize);

        mRenderer = new VideoPlaybackRenderer(this, vuforiaAppSession);
        mRenderer.setTextures(mTextures);
        mRenderer.setOnTrackListener(this);

        // The renderer comes has the OpenGL context, thus, loading to texture
        // must happen when the surface has been created. This means that we
        // can't load the movie from this thread (GUI) but instead we must
        // tell the GL thread to load it once the surface has been created.
        for (int i = 0; i < NUM_TARGETS; i++) {
            mRenderer.setVideoPlayerHelper(i, mVideoPlayerHelper[i]);
            mRenderer.requestLoad(i, Data.targetObjects.get(i).getVideo(), 0, false);
        }

        mGlView.setRenderer(mRenderer);

        for (int i = 0; i < NUM_TARGETS; i++) {
            float[] temp = {1.0f, 1.0f, 0f};
            mRenderer.targetPositiveDimensions[i].setData(temp);
            mRenderer.videoPlaybackTextureID[i] = -1;
        }

    }

    // We do not handle the touch event here, we just forward it to the
    // gesture detector
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = false;

        // Process the Gestures
        if (!result)
            mGestureDetector.onTouchEvent(event);

        return result;
    }


    @Override
    public boolean doInitTrackers() {

        // Indicate if the trackers were initialized correctly
        boolean result = true;

        // Initialize the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        Tracker tracker = trackerManager.initTracker(ObjectTracker
                .getClassType());
        if (tracker == null) {
            Log.d(LOGTAG, "Failed to initialize ObjectTracker.");
            result = false;
        }

        return result;
    }


    @Override
    public boolean doLoadTrackersData() {
        // Get the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager
                .getTracker(ObjectTracker.getClassType());
        if (objectTracker == null) {
            Log.d(
                    LOGTAG,
                    "Failed to load tracking data set because the ObjectTracker has not been initialized.");
            return false;
        }

        // Create the data sets:
        ucrTargetDataset = objectTracker.createDataSet();
        if (ucrTargetDataset == null) {
            Log.d(LOGTAG, "Failed to create a new tracking data.");
            return false;
        }

        // Load the data sets:
        if (!ucrTargetDataset.load(LITE_DATASET,
                STORAGE_TYPE.STORAGE_APPRESOURCE)) {
            Log.d(LOGTAG, "Failed to load data set.");
            return false;
        }

        // Activate the data set:
        if (!objectTracker.activateDataSet(ucrTargetDataset)) {
            Log.d(LOGTAG, "Failed to activate data set.");
            return false;
        }

        Log.d(LOGTAG, "Successfully loaded and activated data set.");
        return true;
    }


    @Override
    public boolean doStartTrackers() {
        // Indicate if the trackers were started correctly
        boolean result = true;

        Tracker objectTracker = TrackerManager.getInstance().getTracker(
                ObjectTracker.getClassType());
        if (objectTracker != null) {
            objectTracker.start();
            Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 2);
        } else
            result = false;

        return result;
    }


    @Override
    public boolean doStopTrackers() {
        // Indicate if the trackers were stopped correctly
        boolean result = true;

        Tracker objectTracker = TrackerManager.getInstance().getTracker(
                ObjectTracker.getClassType());
        if (objectTracker != null)
            objectTracker.stop();
        else
            result = false;

        return result;
    }


    @Override
    public boolean doUnloadTrackersData() {
        // Indicate if the trackers were unloaded correctly
        boolean result = true;

        // Get the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager
                .getTracker(ObjectTracker.getClassType());
        if (objectTracker == null) {
            Log.d(
                    LOGTAG,
                    "Failed to destroy the tracking data set because the ObjectTracker has not been initialized.");
            return false;
        }

        if (ucrTargetDataset != null) {
            if (objectTracker.getActiveDataSet() == ucrTargetDataset
                    && !objectTracker.deactivateDataSet(ucrTargetDataset)) {
                Log.d(
                        LOGTAG,
                        "Failed to destroy the tracking data set StonesAndChips because the data set could not be deactivated.");
                result = false;
            } else if (!objectTracker.destroyDataSet(ucrTargetDataset)) {
                Log.d(LOGTAG,
                        "Failed to destroy the tracking data set StonesAndChips.");
                result = false;
            }

            ucrTargetDataset = null;
        }

        return result;
    }

    // Do not exit immediately and instead show the startup screen
    @Override
    public void onBackPressed() {
        pauseAll(-1);

        try {
            vuforiaAppSession.stopAR();
        } catch (SampleApplicationException e) {
            e.printStackTrace();
        }

        doStopTrackers();
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean doDeinitTrackers() {
        // Indicate if the trackers were deinitialized correctly
        boolean result = true;

        // Deinit the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        trackerManager.deinitTracker(ObjectTracker.getClassType());

        return result;
    }


    private void addMainView() {

        linearLayout = (LinearLayout) View.inflate(this, R.layout.vuforia_ui, null);

        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setBackgroundColor(Color.TRANSPARENT);

        // Adds the inflated layout to the view
        addContentView(linearLayout, new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onInitARDone(SampleApplicationException exception) {

        if (exception == null) {
            initApplicationAR();

            mRenderer.setActive(true);
            addMainView();

            // Now add the GL surface view. It is important
            // that the OpenGL ES surface view gets added
            // BEFORE the camera is started and video
            // background is configured.

            northLayout = (LinearLayout) findViewById(R.id.test_lay);
            Log.i("yupi", northLayout.toString());

            northLayout.addView(mGlView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            northLayout.setBackgroundColor(Color.TRANSPARENT);

            southLayout = (LinearLayout) findViewById(R.id.rel_layout);
            Log.i("yupi", southLayout.toString());

            southLayout.setBackgroundColor(Color.TRANSPARENT);
            southLayout.setBackgroundColor(getResources().getColor(R.color.white));

            titleTextView = (TextView) findViewById(R.id.testview);
            titleTextView.setText("- -");

            descriptionTextView = (TextView) findViewById(R.id.testview2);
            descriptionTextView.setMovementMethod(new ScrollingMovementMethod());
            descriptionTextView.setText("No hay ningún punto de interés seleccionado");

            // Sets the UILayout to be drawn in front of the camera
            mUILayout.bringToFront();

            // Hides the Loading Dialog
            loadingDialogHandler
                    .sendEmptyMessage(LoadingDialogHandler.HIDE_LOADING_DIALOG);

            // Sets the layout background to transparent
            mUILayout.setBackgroundColor(Color.TRANSPARENT);

//            FrameLayout frameLayout =  (FrameLayout) findViewById(R.id.test_frame);
//            frameLayout.setBackgroundColor(Color.TRANSPARENT);
//            frameLayout.bringToFront();

            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.arrow_view);
            relativeLayout.setBackgroundColor(Color.TRANSPARENT);
            relativeLayout.bringToFront();

            arrowUp = (ImageView) findViewById(R.id.arrow_up);
            arrowUp.bringToFront();
            arrowUp.setVisibility(View.GONE);

            arrowLeft = (ImageView) findViewById(R.id.arrow_left);
            arrowLeft.bringToFront();
            arrowLeft.setVisibility(View.GONE);

            arrowRight = (ImageView) findViewById(R.id.arrow_right);
            arrowRight.bringToFront();
            arrowRight.setVisibility(View.GONE);

            // Original
//            addContentView(testLayout, new LayoutParams(LayoutParams.WRAP_CONTENT,
//                    LayoutParams.WRAP_CONTENT));

            try {
                vuforiaAppSession.startAR(CameraDevice.CAMERA_DIRECTION.CAMERA_DIRECTION_DEFAULT);
            } catch (SampleApplicationException e) {
                Log.e(LOGTAG, e.getString());
            }

            boolean result = CameraDevice.getInstance().setFocusMode(
                    CameraDevice.FOCUS_MODE.FOCUS_MODE_CONTINUOUSAUTO);

            if (!result)
                Log.e(LOGTAG, "Unable to enable continuous autofocus");

            mIsInitialized = true;

        } else {
            Log.e(LOGTAG, exception.getString());
            showInitializationErrorMessage(exception.getString());
        }

    }


    // Shows initialization error messages as System dialogs
    public void showInitializationErrorMessage(String message) {
        final String errorMessage = message;
        runOnUiThread(new Runnable() {
            public void run() {
                if (mErrorDialog != null) {
                    mErrorDialog.dismiss();
                }

                // Generates an Alert Dialog to show the error message
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        VideoPlayback.this);
                builder
                        .setMessage(errorMessage)
                        .setTitle(getString(R.string.INIT_ERROR))
                        .setCancelable(false)
                        .setIcon(0)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });

                mErrorDialog = builder.create();
                mErrorDialog.show();
            }
        });
    }


    @Override
    public void onVuforiaUpdate(State state) {
//        Log.d(VideoPlayback.DEBUG_TAG, "Esto se updeitea mae ");

    }

    final private static int CMD_BACK = -1;
    final private static int CMD_FULLSCREEN_VIDEO = 1;

    public void updateTextFields(String title, String description) {
        titleTextView.setText(title);
        descriptionTextView.setText(description);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_up:
                toastLog(getResources().getString(R.string.straight_ahead));
                break;

            case R.id.arrow_left:
                toastLog(getResources().getString(R.string.turn_left));
                break;

            case R.id.arrow_right:
                toastLog(getResources().getString(R.string.turn_right));
                break;

        }
    }

    /**
     * Method that sets the right arrow indicated as parameter as visible
     */
    public void showArrow(Arrow arrow) {
        switch (arrow) {
            case LEFT:
                arrowLeft.setVisibility(View.VISIBLE);
                break;
            case RIGHT:
                arrowRight.setVisibility(View.VISIBLE);
                break;
            case UP:
                arrowUp.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void toastLog(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
