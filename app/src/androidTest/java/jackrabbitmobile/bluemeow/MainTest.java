package jackrabbitmobile.bluemeow;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by SamMyxer on 11/2/15.
 */
@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<EspressoActivity> mActivityRule =
            new ActivityTestRule<>(EspressoActivity.class);

    @Test
    public void testCorrectString() {
        String exampleString = "Yes";
        String wrongString = "No";
        Espresso.onView(ViewMatchers.withId(R.id.enterEditText)).perform(ViewActions.typeText(exampleString));

        Espresso.onView(ViewMatchers.withId(R.id.sendTextButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.displayTextView)).check(ViewAssertions.matches(ViewMatchers.withText(exampleString)));
    }
}
