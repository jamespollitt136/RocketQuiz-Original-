package jamespollitt.rocketquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);

        Button signinButton = (Button)findViewById(R.id.signinButton);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signin = new Intent(view.getContext(), LoginActivity.class);
                startActivity(signin);
            }
        });

        Button signupButon = (Button)findViewById(R.id.createButton);
        signupButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(view.getContext(), RegistrationActivity.class);
                startActivity(signup);
            }
        });
    }
}
