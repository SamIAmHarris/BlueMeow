package jackrabbitmobile.bluemeow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    public static final String TEXT_EXTRA = "text_to_pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        if(getIntent().getExtras() != null) {
            Bundle yos = getIntent().getExtras();

            TextView displayTextView = (TextView) findViewById(R.id.displayTextView);
            displayTextView.setText(yos.getString(TEXT_EXTRA));
        }
    }

}
