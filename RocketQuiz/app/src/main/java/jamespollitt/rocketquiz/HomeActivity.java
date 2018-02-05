package jamespollitt.rocketquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private AdView adView;

    private String name;
    private String email;
    private String id;
    private String signInProvider;

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

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        // Firebase user credentials
        user = FirebaseAuth.getInstance().getCurrentUser();
        name = user.getDisplayName();
        email = user.getEmail();
        id = user.getProviderId();
        signInProvider = "";

        readFromFirebase();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name = extras.getString("userName");
            email = extras.getString("userEmail");
            signInProvider = extras.getString("provider");
            //Uri photo = Uri.parse(extras.getString("userPhoto"));
        }
/*
        // AdMob Load an Ad.
        adView = (AdView) findViewById(R.id.homeAdView);
        final AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice()
                .build();
        adView.loadAd(adRequest);
        //TODO: Remove the test device line when releasing to store
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.w("Home::", "Failed to load ad.");
                adView.loadAd(adRequest);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        }); */

        // User interface profile elements
        ImageView profileImage = (ImageView)findViewById(R.id.accountProfilePic);
        TextView nameTxt = (TextView)findViewById(R.id.homeNameText);
        nameTxt.setText(name);
        profileImage.setOnClickListener(openAccount);
        nameTxt.setOnClickListener(openAccount);

        // Begin playing the game
        Button play = (Button)findViewById(R.id.homePlayBtn);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categories = new Intent(view.getContext(), CategoryActivity.class);
                startActivity(categories);
            }
        });
    }

    /*
    This method reads the user's data from the Firebase Real-Time Database when the home page loads.
    */
    private void readFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User profile = dataSnapshot.getValue(User.class);
                score = profile.getScore();
                credits = profile.getCredits();
                correct = profile.getCorrect();
                wrong = profile.getIncorrect();
                filmPlayed = profile.getFilmPlayed();
                genknoPlayed = profile.getGenknoPlayed();
                geogPlayed = profile.getGeogPlayed();
                litPlayed = profile.getLitPlayed();
                mathsPlayed = profile.getMathsPlayed();
                sciencePlayed = profile.getSciencePlayed();
                techPlayed = profile.getTechPlayed();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Home", "Failed to read data: ", databaseError.toException());
            }
        });
    }

    public void homeInteractionHandler(View view){
        switch (view.getId()) {
            case R.id.homeLeaderboardBtn:
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.storeBtn:
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settingsBtn:
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.informationBtn:
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bugBtn:
                Toast.makeText(this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    View.OnClickListener openAccount = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent account = new Intent(view.getContext(), AccountActivity.class);
            account.putExtra("name", name);
            account.putExtra("email", email);
            account.putExtra("id", id);
            account.putExtra("score", score);
            account.putExtra("credits", credits);
            account.putExtra("correct", correct);
            account.putExtra("wrong", wrong);
            account.putExtra("filmPlayed", filmPlayed);
            account.putExtra("genknoPlayed", genknoPlayed);
            account.putExtra("geogPlayed", geogPlayed);
            account.putExtra("litPlayed", litPlayed);
            account.putExtra("mathsPlayed", mathsPlayed);
            account.putExtra("sciencePlayed", sciencePlayed);
            account.putExtra("techPlayed", techPlayed);
            //TODO: read from firebase to get and pass score/credits (also display in top left corner)
            //account.putExtra(photo);
            startActivity(account);
        }
    };

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
