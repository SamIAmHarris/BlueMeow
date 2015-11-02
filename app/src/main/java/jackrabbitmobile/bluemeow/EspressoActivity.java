package jackrabbitmobile.bluemeow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EspressoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso);

        final EditText enterET = (EditText) findViewById(R.id.enterEditText);

        Button sendButton = (Button) findViewById(R.id.sendTextButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent displayIntent = new Intent(getBaseContext(), DisplayActivity.class);
                displayIntent.putExtra(DisplayActivity.TEXT_EXTRA, enterET.getText().toString());
                startActivity(displayIntent);
            }
        });
    }
}
