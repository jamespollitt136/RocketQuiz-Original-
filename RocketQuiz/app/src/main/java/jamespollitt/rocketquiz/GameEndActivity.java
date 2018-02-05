package jamespollitt.rocketquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameEndActivity extends AppCompatActivity {

    public final Context context = this;

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_end_activity);

        /*
        adView = (AdView)findViewById(R.id.gameEndAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        */

        //TODO: send data to firebase here.

        int score;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = extras.getInt("score");
        } else {
            score = 0;
        }

        TextView scoredText = findViewById(R.id.gameEndScoredText);
        scoredText.setText("You scored: " +  score + " points.");
        TextView quoteText = findViewById(R.id.gameEndQuoteText);
        int randomNumber = random.nextInt(numberOfQuotes());
        quoteText.setText(pullQuote(randomNumber));

        Button continueBtn = findViewById(R.id.gameEndContBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(context, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }

    // array holding all questions for the Literature Quiz
    private String quotes[] = {
            "\"Shoot for the moon because if you miss you will be amongst the stars\"",
            "\"in a galaxy far far away...\"",
            "\"I just need some space\"",
            "\"Saturn: Undefeated Solar System Hula Hoop Champ\"",
            "\"How do you organise a space party? You planet!\"",
            "\"Comet me bro!\"",
            "\"What currency do they use in space? Starbucks!\"",
            "\"Star Trekking across the universe\""
    };

    public int numberOfQuotes(){
        return quotes.length;
    }

    public String pullQuote(int i){
        return quotes[i];
    }
}
