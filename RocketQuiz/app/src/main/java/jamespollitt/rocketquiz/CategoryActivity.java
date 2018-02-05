package jamespollitt.rocketquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class CategoryActivity extends AppCompatActivity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
/*
        adView = (AdView)findViewById(R.id.categoryAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
*/
        Button filmTvBtn = (Button)findViewById(R.id.filmTvBtn);
        Button genKnowBtn = (Button)findViewById(R.id.genKnowledgeBtn);
        Button geogBtn = (Button)findViewById(R.id.geographyBtn);
        Button litBtn = (Button)findViewById(R.id.literatureBtn);
        Button mathsBtn = (Button)findViewById(R.id.mathsBtn);
        Button scienceBtn = (Button)findViewById(R.id.scienceBtn);
        Button techBtn = (Button)findViewById(R.id.technologyBtn);
    }

    public void categoryClicked(View view){
        String gameType = "";
        switch (view.getId()){
            case R.id.filmTvBtn:
                gameType = "Film&TV";
                break;
            case R.id.genKnowledgeBtn:
                gameType = "GenKnow";
                break;
            case R.id.geographyBtn:
                gameType = "Geography";
                break;
            case R.id.literatureBtn:
                gameType = "Literature";
                break;
            case R.id.mathsBtn:
                gameType = "Maths";
                break;
            case R.id.scienceBtn:
                gameType = "Science";
                break;
            case R.id.technologyBtn:
                gameType = "Technology";
                break;
        }
        if(!gameType.equals("")){
            startGame(gameType);
        }
    }

    private void startGame(String gameType){
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra("quizType", gameType);
        startActivity(gameIntent);
        finish();
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
