package jamespollitt.rocketquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        // Check if there is a user logged in via Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            // If no user is logged in, show them the login/sign up options.
            Intent landingIntent = new Intent(this, LandingActivity.class);
            startActivity(landingIntent);
            finish();
        }
        else {
            // a firebase user exists. open the home page and pass their details.
            Intent homeIntent = new Intent(this, HomeActivity.class);
            //homeIntent.putExtra("userName", user.getDisplayName());
            //homeIntent.putExtra("userEmail", user.getEmail());
            //homeIntent.putExtra("userPhoto", user.getPhotoUrl());
            startActivity(homeIntent);
            finish();
        }
    }

}
