package jamespollitt.rocketquiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class AccountActivity extends AppCompatActivity {

    private AdView adView;

    private ProgressBar progressBar;
    private TextView percentageText;

    private String name;
    private String email;

    private String id;

    private String score;
    private String credits;
    private String correct;
    private String wrong;
    private String filmPlayed;
    private String genknoPlayed;
    private String geogPlayed;
    private String litPlayed;
    private String mathsPlayed;
    private String sciencePlayed;
    private String techPlayed;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

     /*   // AdMob Load an Ad.
        adView = (AdView) findViewById(R.id.accountAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
*/
        userId = user.getUid();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name = extras.getString("name");
            email = extras.getString("email");
            id = extras.getString("id");
            score = extras.getString("score");
            credits = extras.getString("credits");
            correct = extras.getString("correct");
            wrong = extras.getString("wrong");
            filmPlayed = extras.getString("filmPlayed");
            genknoPlayed = extras.getString("genknoPlayed");
            geogPlayed = extras.getString("geogPlayed");
            litPlayed = extras.getString("litPlayed");
            mathsPlayed = extras.getString("mathsPlayed");
            sciencePlayed = extras.getString("sciencePlayed");
            techPlayed = extras.getString("techPlayed");
            //Uri photo = Uri.parse(extras.getString("userPhoto"));
        }

        ImageView profileImage = (ImageView)findViewById(R.id.accountProfilePic);
        TextView profileName = (TextView)findViewById(R.id.accountName);
        profileName.setText(name);
        TextView profileScore = (TextView)findViewById(R.id.accountScore);
        progressBar = (ProgressBar)findViewById(R.id.accWinLossBar);
        percentageText = (TextView)findViewById(R.id.accPercText);
        Button logoutBtn = (Button)findViewById(R.id.accountLogoutBtn);

        //setProgressBar();
    }

    /**
     * This method is the onClick method for all of the account page action buttons.
     * @param view the AccountActivity view.
     */
    public void accountButtonClicked(final View view){
        switch (view.getId()){
            case R.id.accountLogoutBtn:
                logout();
                break;
        }
    }

    /*
    Method to set the progress bar with the users stats from Firebase.
    */
    private void setProgressBar(){
        int iCorrect = Integer.getInteger(correct);
        int iWrong = Integer.getInteger(wrong);
        int total = iCorrect + iWrong;
        progressBar.setMax(total);
        progressBar.setProgress(iCorrect);
        progressBar.setSecondaryProgress(iWrong);
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            progressBar.setSecondaryProgressTintList(ColorStateList.valueOf(Color.RED));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(getResources()
                    .getColor(R.color.green), PorterDuff.Mode.SRC_IN);
        }
        float perc = (iCorrect * 100) / total;
        percentageText.setText(Float.toString(perc) + "%");
    }

    /*
    This method is to be used when the user clicks the log out button. The method will sign a Firebase
    user out and end the running activities, opening the landing screen.
    NOTE: Code for any additional login providers will need to be added here if they are implemented
    such as Facebook and Google Login.
    */
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent landingIntent = new Intent(this, LandingActivity.class);
        startActivity(landingIntent);
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adView != null){
            adView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(adView != null){
            adView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adView != null){
            adView.destroy();
        }
    }
}
