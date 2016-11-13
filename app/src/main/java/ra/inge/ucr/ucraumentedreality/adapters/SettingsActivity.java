package ra.inge.ucr.ucraumentedreality.adapters;


import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import ra.inge.ucr.ucraumentedreality.R;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity implements Preference.OnPreferenceClickListener {

    private static String appVersion;
    private Toolbar toobar;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        getSupportActionBar().hide();
    }

    /**
     * @param savedInstanceState
     */
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        AppBarLayout bar;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            LinearLayout root = (LinearLayout) findViewById(R.id.settings_layout);//etParent().getParent().getParent();
            bar = (AppBarLayout) LayoutInflater.from(this).inflate(R.layout.toolbar_settings, root, false);
            root.addView(bar, 0);
        } else {
            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
            ListView content = (ListView) root.getChildAt(0);
            content.setDivider(new ColorDrawable(Color.DKGRAY));
            content.setDividerHeight((int) getApplicationContext().getResources().getDisplayMetrics().density);
            root.removeAllViews();
            bar = (AppBarLayout) LayoutInflater.from(this).inflate(R.layout.toolbar_settings, root, false);

            int height;
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            } else {
                height = bar.getHeight();
            }

            content.setPadding(0, height, 0, 0);

            root.addView(content);
            root.addView(bar);
        }

        toobar = (Toolbar) bar.getChildAt(0);

        toobar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "unknown";
        }
        setupSimplePreferencesScreen();
    }

    /**
     *
     */
    @SuppressWarnings("deprecation")
    private void setupSimplePreferencesScreen() {
        addPreferencesFromResource(R.xml.preferences);

        /**
         *  Reference the preference keys
         */
        findPreference("settings_notifications").setOnPreferenceClickListener(this);
        findPreference("settings_profile").setOnPreferenceClickListener(this);
        findPreference("settings_language").setOnPreferenceClickListener(this);
        findPreference("settings_help").setOnPreferenceClickListener(this);
        findPreference("settings_feedback").setOnPreferenceClickListener(this);
        findPreference("settings_version").setOnPreferenceClickListener(this);

        /**
         *  Add the summary preference
         */
        bindPreferenceSummaryToValue(findPreference("settings_notifications"));
        bindPreferenceSummaryToValue(findPreference("settings_profile"));
        bindPreferenceSummaryToValue(findPreference("settings_language"));
        bindPreferenceSummaryToValue(findPreference("settings_help"));
        bindPreferenceSummaryToValue(findPreference("settings_feedback"));
        bindPreferenceSummaryToValue(findPreference("settings_version"));

    }


    /**
     *
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    private static void setPreferenceSummary(Preference preference, String value) {
        preference.setSummary(value);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        // Allow super to try and create a view first
        final View result = super.onCreateView(name, context, attrs);
        if (result != null) {
            return result;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // If we're running pre-L, we need to 'inject' our tint aware Views in place of the
            // standard framework versions
            switch (name) {
                case "EditText":
                    return new AppCompatEditText(this, attrs);
                case "Spinner":
                    return new AppCompatSpinner(this, attrs);
                case "CheckBox":
                    return new AppCompatCheckBox(this, attrs);
                case "RadioButton":
                    return new AppCompatRadioButton(this, attrs);
                case "CheckedTextView":
                    return new AppCompatCheckedTextView(this, attrs);
            }
        }

        return null;
    }


    /**
     * @param newConfig
     */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * @param preferenceScreen
     * @param preference
     * @return
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        super.onPreferenceTreeClick(preferenceScreen, preference);

        if (preference != null) {
            if (preference instanceof PreferenceScreen) {
                if (((PreferenceScreen) preference).getDialog() != null) {
                    ((PreferenceScreen) preference).getDialog().getWindow().getDecorView().setBackgroundDrawable(this.getWindow().getDecorView().getBackground().getConstantState().newDrawable());
                    setUpNestedScreen((PreferenceScreen) preference);
                }
            }
        }

        return false;
    }

    /**
     * @param preferenceScreen
     */
    public void setUpNestedScreen(PreferenceScreen preferenceScreen) {
        final Dialog dialog = preferenceScreen.getDialog();

        AppBarLayout appBar;

        View listRoot = dialog.findViewById(android.R.id.list);
        ViewGroup mRootView = (ViewGroup) dialog.findViewById(android.R.id.content);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            LinearLayout root = (LinearLayout) dialog.findViewById(android.R.id.list).getParent();
            appBar = (AppBarLayout) LayoutInflater.from(this).inflate(R.layout.toolbar_settings, root, false);
            root.addView(appBar, 0);
        } else {
            ListView content = (ListView) mRootView.getChildAt(0);
            mRootView.removeAllViews();

            LinearLayout LL = new LinearLayout(this);
            LL.setOrientation(LinearLayout.VERTICAL);

            ViewGroup.LayoutParams LLParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            LL.setLayoutParams(LLParams);

            appBar = (AppBarLayout) LayoutInflater.from(this).inflate(R.layout.toolbar_settings, mRootView, false);

            LL.addView(appBar);
            LL.addView(content);

            mRootView.addView(LL);
        }

        if (listRoot != null) {
            listRoot.setPadding(0, listRoot.getPaddingTop(), 0, listRoot.getPaddingBottom());
        }

        Toolbar toobar = (Toolbar) appBar.getChildAt(0);
        toobar.setTitle(preferenceScreen.getTitle());
        toobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**
     * Preferences Activity
     *
     * @param preference
     * @return
     */
    @Override
    public boolean onPreferenceClick(Preference preference) {
//        switch (preference.getKey()) {
//            case "settings_profile":
//                startActivity(new Intent(getApplicationContext(), GeneralPreferences.class));
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                break;
//            case "settings_notifications":
//                startActivity(new Intent(getApplicationContext(), NotificationPreferences.class));
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                break;
//            case "settings_language":
//                new LanguageDialog().show(getFragmentManager(), getResources().getString(R.string.languages));
//                break;
//            case "settings_help":
//                new HelpDialog().show(getFragmentManager(), null);
//                break;
//            case "settings_feedback":
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto", "feedback@symbiotic.net", null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
//                startActivity(Intent.createChooser(emailIntent, "Send email..."));
//                break;
//            case "settings_version":
//                new VersionDialog().show(getFragmentManager(), null);
//                break;
//        }
        return false;
    }


    /**
     * Autogenerated method that sets the current preference as subtitle
     */
    public static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };





}

