package jackrabbitmobile.bluemeow;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.junit.Rule;
import org.junit.Test;

/**
 * Created by SamMyxer on 11/2/15.
 */
public class TestIntent {


    @Rule
    public IntentsTestRule<EspressoActivity> mActivityRule =
            new IntentsTestRule<>(EspressoActivity.class);

    @Test
    public void triggerIntentTest() {
        onView(withId(R.id.sendTextButton)).perform(click());
        intended(hasComponent(DisplayActivity.class.getName()));
    }



}
