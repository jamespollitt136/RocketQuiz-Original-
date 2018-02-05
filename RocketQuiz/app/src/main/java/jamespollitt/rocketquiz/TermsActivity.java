package jamespollitt.rocketquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_activity);

        Button close = (Button)findViewById(R.id.termsCloseBtn);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Override the onBackPressed method to close the activity from running in the background.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
