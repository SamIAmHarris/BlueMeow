package jackrabbitmobile.bluemeow;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.bluecats.sdk.BCBeacon;
import com.bluecats.sdk.BCCategory;
import com.bluecats.sdk.BCEventFilter;
import com.bluecats.sdk.BCEventManager;
import com.bluecats.sdk.BCEventManagerCallback;
import com.bluecats.sdk.BCMicroLocation;
import com.bluecats.sdk.BCMicroLocationManager;
import com.bluecats.sdk.BCMicroLocationManagerCallback;
import com.bluecats.sdk.BCSite;
import com.bluecats.sdk.BCTrigger;
import com.bluecats.sdk.BCTriggeredEvent;
import com.bluecats.sdk.BlueCatsSDK;
import com.bluecats.sdk.IBCEventFilter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public final static String API_TOKEN = "a8325eb3-450d-4a99-9bc2-a0bc79c6ba67";

    private static final String TAG = "BLUEMEOW_DEBUG";

    Switch blackSwitch;
    Switch stickerSwitch;
    Switch whiteSwitch;

    Button updateBeaconsButton;
    Button clearBeaconsButton;

    boolean searchingForBeacons = false;

    public static final String BLACK_BEACON = "BLACK BEACON";
    public static final String STICKER_BEACON = "STICKER BEACON";
    public static final String WHITE_BEACON = "WHITE BEACON";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        blackSwitch = (Switch) findViewById(R.id.black_beacon_switch);
        stickerSwitch = (Switch) findViewById(R.id.sticker_beacon_switch);
        whiteSwitch = (Switch) findViewById(R.id.white_beacon_switch);

        updateBeaconsButton = (Button) findViewById(R.id.update_beacon_button);
        updateBeaconsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchingForBeacons) {
                    searchingForBeacons = false;
                    cancelTracker();
                    updateBeaconsButton.setText("UPDATE BEACON STATUS");
                } else {
                    searchingForBeacons = true;
                    setUp3SecondTracker();
                    updateBeaconsButton.setText("SEARCHING FOR BEACONS");
                }
            }
        });

        clearBeaconsButton = (Button) findViewById(R.id.clear_beacon_button);
        clearBeaconsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blackSwitch.setChecked(false);
                stickerSwitch.setChecked(false);
                whiteSwitch.setChecked(false);
            }
        });

        BlueCatsSDK.startPurringWithAppToken(getApplicationContext(), API_TOKEN);
    }

    private BCEventManagerCallback mBCEventManagerCallback = new BCEventManagerCallback() {
        @Override
        public void onTriggeredEvent(BCTriggeredEvent bcTriggeredEvent) {
            if (bcTriggeredEvent.getEvent().getEventIdentifier().equals("3SecondTrigger") && searchingForBeacons) {
                final BCBeacon currentBeacon = bcTriggeredEvent.getFilteredMicroLocation().getBeacons().get(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        blackSwitch.setChecked(false);
                        stickerSwitch.setChecked(false);
                        whiteSwitch.setChecked(false);
                        switch (currentBeacon.getName()) {
                            case BLACK_BEACON:
                                blackSwitch.setChecked(true);
                                break;
                            case STICKER_BEACON:
                                stickerSwitch.setChecked(true);
                                break;
                            case WHITE_BEACON:
                                whiteSwitch.setChecked(true);
                                break;
                        }
                    }
                });
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        BlueCatsSDK.didEnterForeground();
    }

    @Override
    protected void onPause() {
        BlueCatsSDK.didEnterBackground();
        super.onPause();
    }

    public void setUp3SecondTracker() {
        //Set up filters to apply to ranged beacons
        List<IBCEventFilter> filters = new ArrayList<IBCEventFilter>();

        //Within approximately 2 meters of the beacon
        filters.add(BCEventFilter.filterByAccuracyRangeFrom(0.0, 2.0));

        //Needs to pass the previous conditions for at least 5 seconds before firing, allowing a gap of at most 2 seconds within that time.
        filters.add(BCEventFilter.filterByMinTimeIntervalBetweenBeaconMatches(10000));

        //Create the trigger
        BCTrigger nearFor3SecondsTrigger = new BCTrigger("3SecondTrigger", filters);
        nearFor3SecondsTrigger.setRepeatCount(1000);

        //Send the trigger to the event manager to be monitored
        BCEventManager.getInstance().monitorEventWithTrigger(nearFor3SecondsTrigger, mBCEventManagerCallback);
    }

    public void cancelTracker() {
        BCEventManager.getInstance().removeMonitoredEvent("3SecondTrigger");
    }
}
