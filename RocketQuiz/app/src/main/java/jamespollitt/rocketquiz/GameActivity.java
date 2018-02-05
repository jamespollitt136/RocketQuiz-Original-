package jamespollitt.rocketquiz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity  implements OnClickListener {

    private final Context context = this;

    private TextView questionTextView;
    private TextView answer1TextView;
    private TextView answer2TextView;
    private TextView answer3TextView;

    private String answer;
    private int score;

    private ImageView rocket;
    private ImageView bPlanet;
    private ImageView oPlanet;
    private ImageView rPlanet;

    private int livesRemaining;

    private String gameType;

    private FilmTvQuestions filmTvQuestions;
    private GeneralKnowledgeQuestions generalKnowledgeQuestions;
    private GeographyQuestions geographyQuestions;
    private LiteratureQuestions literatureQuestions;
    private MathsQuestions mathsQuestions;
    private ScienceQuestions scienceQuestions;
    private TechnologyQuestions technologyQuestions;

    private int questionNumber;
    private int randomNumber;
    private int lastRandom;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        /*
        Change the colour of the status bar to black if the device is Lollipop or newer to fit background.
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }

        livesRemaining = 3;
        questionNumber = 0;
        lastRandom = 0;

        // UI binding
        rocket = findViewById(R.id.gameRocketImage);
        questionTextView = findViewById(R.id.gameQuestionText);
        answer1TextView = findViewById(R.id.answerOneText);
        answer2TextView = findViewById(R.id.answerTwoText);
        answer3TextView = findViewById(R.id.answerThreeText);
        bPlanet = findViewById(R.id.bluePlanetImage);
        oPlanet = findViewById(R.id.orangePlanetImage);
        rPlanet = findViewById(R.id.redPlanetImage);
        answer1TextView.setOnClickListener(this);
        answer2TextView.setOnClickListener(this);
        answer3TextView.setOnClickListener(this);
        bPlanet.setOnClickListener(this);
        oPlanet.setOnClickListener(this);
        rPlanet.setOnClickListener(this);

        //TODO: Remove this and fields when finished testing questions.
        Button changeQuestionBtn = findViewById(R.id.changeQbtn);
        View.OnClickListener changeQuestion = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeQuestion();
            }
        };
        changeQuestionBtn.setOnClickListener(changeQuestion);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameType = extras.getString("quizType");
        } else {
            gameType = "";
        }

        switch (gameType) {
            case "":
                // This should never occur but if it does the activity will close.
                finish();
                break;
            case "Film&TV":
                filmTvQuestions = new FilmTvQuestions();
                randomNumber = random.nextInt(filmTvQuestions.numberOfQuestions());
                loadFilmAndTV(randomNumber);
                lastRandom = randomNumber;
                animateIn();
                break;
            case "GenKnow":
                generalKnowledgeQuestions = new GeneralKnowledgeQuestions();
                randomNumber = random.nextInt(generalKnowledgeQuestions.numberOfQuestions());
                loadGeneralKnowledge(randomNumber);
                lastRandom = randomNumber;
                animateIn();
                break;
            case "Geography":
                geographyQuestions = new GeographyQuestions();
                randomNumber = random.nextInt(geographyQuestions.numberOfQuestions());
                loadGeography(randomNumber);
                lastRandom = randomNumber;
                animateIn();
                break;
            case "Literature":
                literatureQuestions = new LiteratureQuestions();
                randomNumber = random.nextInt(literatureQuestions.numberOfQuestions());
                loadLiterature(randomNumber);
                lastRandom = randomNumber;
                animateIn();
                break;
            case "Maths":
                mathsQuestions = new MathsQuestions();
                randomNumber = random.nextInt(mathsQuestions.numberOfQuestions());
                loadMaths(randomNumber);
                lastRandom = randomNumber;
                animateIn();
                break;
            case "Science":
                scienceQuestions = new ScienceQuestions();
                randomNumber = random.nextInt(scienceQuestions.numberOfQuestions());
                loadScience(randomNumber);
                lastRandom = randomNumber;
                animateIn();
                break;
            case "Technology":
                technologyQuestions = new TechnologyQuestions();
                randomNumber = random.nextInt(technologyQuestions.numberOfQuestions());
                loadTechnology(randomNumber);
                lastRandom = randomNumber;
                animateIn();
                break;
            //TODO: other cases for game here: follow above structure
            default:
                break;
        }
    }

    /*
    Method for adding a fade in animation to all the text views. This method will be called at the
    start of a question.
    */
    private void animateIn(){
        questionTextView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        answer1TextView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        answer2TextView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        answer3TextView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        questionTimer();
    }

    /*
    Method for adding a fade out animation to all the text views. This method will be called at the
    end of a question.
    */
    private void animateOut(){
        questionTextView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        answer1TextView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        answer2TextView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        answer3TextView.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
    }

    //TODO: rocket launch move with sensors

    //TODO: collison detection between rocket and textview/images check answer of text view.

    /*
    Method for changing the question. This method will control the animations and check which game
    mode is being played (i.e. Film & TV) to pull the questions and answers required. The method
    generates a random number between 0 and the length of the questions array for the game mode and
    uses the random number as the index for the 3 arrays (question, options, correct).
    */
    private void changeQuestion(){
        questionNumber += 1;
        animateOut();
        if(livesRemaining > 0){
            switch (gameType) {
                case "Film&TV":
                    //random number generator - limit is the total number of questions in the array
                    randomNumber = random.nextInt(filmTvQuestions.numberOfQuestions());
                    if (randomNumber != lastRandom){ // if the new random isn't the same as the last
                        loadFilmAndTV(randomNumber); // load a new question from the film category
                        lastRandom = randomNumber; // reset lastRandom to avoid repeated questions
                    } else {
                        loadFilmAndTV(randomNumber + 1); // use the next question in the set
                        lastRandom = randomNumber + 1; // reset lastRandom to avoid repeated questions
                    }
                    break;
                case "GenKnow": //General Knowledge category
                    //random number generator - limit is the total number of questions in the array
                    randomNumber = random.nextInt(generalKnowledgeQuestions.numberOfQuestions());
                    if (randomNumber != lastRandom){ // if the new random isn't the same as the last
                        loadGeneralKnowledge(randomNumber); // load a new question from the general knowledge category
                        lastRandom = randomNumber; // reset lastRandom to avoid repeated questions
                    } else {
                        loadGeneralKnowledge(randomNumber + 1); // use the next question in the set
                        lastRandom = randomNumber + 1; // reset lastRandom to avoid repeated questions
                    }
                    break;
                case "Geography":
                    //random number generator - limit is the total number of questions in the array
                    randomNumber = random.nextInt(geographyQuestions.numberOfQuestions());
                    if (randomNumber != lastRandom){ // if the new random isn't the same as the last
                        loadGeography(randomNumber); // load a new question from the geography category
                        lastRandom = randomNumber; // reset lastRandom to avoid repeated questions
                    } else {
                        loadGeography(randomNumber + 1); // use the next question in the set
                        lastRandom = randomNumber + 1; // reset lastRandom to avoid repeated questions
                    }
                    break;
                case "Literature":
                    //random number generator - limit is the total number of questions in the array
                    randomNumber = random.nextInt(literatureQuestions.numberOfQuestions());
                    if (randomNumber != lastRandom){ // if the new random isn't the same as the last
                        loadLiterature(randomNumber); // load a new question from the literature category
                        lastRandom = randomNumber; // reset lastRandom to avoid repeated questions
                    } else {
                        loadLiterature(randomNumber + 1); // use the next question in the set
                        lastRandom = randomNumber + 1; // reset lastRandom to avoid repeated questions
                    }
                    break;
                case "Maths":
                    //random number generator - limit is the total number of questions in the array
                    randomNumber = random.nextInt(mathsQuestions.numberOfQuestions());
                    if (randomNumber != lastRandom){ // if the new random isn't the same as the last
                        loadMaths(randomNumber); // load a new question from the maths category
                        lastRandom = randomNumber; // reset lastRandom to avoid repeated questions
                    } else {
                        loadMaths(randomNumber + 1); // use the next question in the set
                        lastRandom = randomNumber + 1; // reset lastRandom to avoid repeated questions
                    }
                    break;
                case "Science":
                    //random number generator - limit is the total number of questions in the array
                    randomNumber = random.nextInt(scienceQuestions.numberOfQuestions());
                    if (randomNumber != lastRandom){ // if the new random isn't the same as the last
                        loadScience(randomNumber); // load a new question from the science category
                        lastRandom = randomNumber; // reset lastRandom to avoid repeated questions
                    } else {
                        loadScience(randomNumber + 1); // use the next question in the set
                        lastRandom = randomNumber + 1; // reset lastRandom to avoid repeated questions
                    }
                    break;
                case "Technology":
                    //random number generator - limit is the total number of questions in the array
                    randomNumber = random.nextInt(technologyQuestions.numberOfQuestions());
                    if (randomNumber != lastRandom){ // if the new random isn't the same as the last
                        loadTechnology(randomNumber); // load a new question from the tech category
                        lastRandom = randomNumber; // reset lastRandom to avoid repeated questions
                    } else {
                        loadTechnology(randomNumber + 1); // use the next question in the set
                        lastRandom = randomNumber + 1; // reset lastRandom to avoid repeated questions
                    }
                    break;
            }
            animateIn();
        }
        else {
            Intent gameEndIntent = new Intent(this, GameEndActivity.class);
            gameEndIntent.putExtra("score", score); // pass the score to the new activity
            startActivity(gameEndIntent); // start the end game activity
            finish(); // close game activity
        }
    }

    /**
     * Method for loading the questions and answers into the game if the game type is Film and TV.
     * @param index random number which is used to pull from the 3 array lists
     */
    private void loadFilmAndTV(int index){
        questionTextView.setText(filmTvQuestions.pullQuestion(index));
        answer1TextView.setText(filmTvQuestions.pullOptionOne(index));
        answer2TextView.setText(filmTvQuestions.pullOptionTwo(index));
        answer3TextView.setText(filmTvQuestions.pullOptionThree(index));
        answer = filmTvQuestions.pullCorrect(index);
    }

    private void loadGeneralKnowledge(int index){
        questionTextView.setText(generalKnowledgeQuestions.pullQuestion(index));
        answer1TextView.setText(generalKnowledgeQuestions.pullOptionOne(index));
        answer2TextView.setText(generalKnowledgeQuestions.pullOptionTwo(index));
        answer3TextView.setText(generalKnowledgeQuestions.pullOptionThree(index));
        answer = generalKnowledgeQuestions.pullCorrect(index);
    }

    private void loadGeography(int index){
        questionTextView.setText(geographyQuestions.pullQuestion(index));
        answer1TextView.setText(geographyQuestions.pullOptionOne(index));
        answer2TextView.setText(geographyQuestions.pullOptionTwo(index));
        answer3TextView.setText(geographyQuestions.pullOptionThree(index));
        answer = geographyQuestions.pullCorrect(index);
    }

    private void loadLiterature(int index){
        questionTextView.setText(literatureQuestions.pullQuestion(index));
        answer1TextView.setText(literatureQuestions.pullOptionOne(index));
        answer2TextView.setText(literatureQuestions.pullOptionTwo(index));
        answer3TextView.setText(literatureQuestions.pullOptionThree(index));
        answer = literatureQuestions.pullCorrect(index);
    }

    private void loadMaths(int index){
        questionTextView.setText(mathsQuestions.pullQuestion(index));
        answer1TextView.setText(mathsQuestions.pullOptionOne(index));
        answer2TextView.setText(mathsQuestions.pullOptionTwo(index));
        answer3TextView.setText(mathsQuestions.pullOptionThree(index));
        answer = mathsQuestions.pullCorrect(index);
    }

    private void loadScience(int index){
        questionTextView.setText(scienceQuestions.pullQuestion(index));
        answer1TextView.setText(scienceQuestions.pullOptionOne(index));
        answer2TextView.setText(scienceQuestions.pullOptionTwo(index));
        answer3TextView.setText(scienceQuestions.pullOptionThree(index));
        answer = scienceQuestions.pullCorrect(index);
    }

    private void loadTechnology(int index){
        questionTextView.setText(technologyQuestions.pullQuestion(index));
        answer1TextView.setText(technologyQuestions.pullOptionOne(index));
        answer2TextView.setText(technologyQuestions.pullOptionTwo(index));
        answer3TextView.setText(technologyQuestions.pullOptionThree(index));
        answer = technologyQuestions.pullCorrect(index);
    }

    /*
    Accessor method for returning the question number.
    */
    private int getQuestionNumber(){
        return questionNumber;
    }

    private String getAnswer(){
        return answer;
    }

    /*
    This method is used for overriding the default on back pressed action in order to finish this
    activity, and go back to the home screen.
    */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /*
    This method is used to show the question for a set period (3 seconds), then fading out, so the
    game can begin.
    */
    private void questionTimer(){
        //set timer for 3 seconds then fade out question text. then call another method for launching rocket.
        Handler fadeOutHandler = new Handler();
        fadeOutHandler.postDelayed(new Runnable() {
            public void run() {
                // fade question out
                questionTextView.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
            }
        }, 5000); //5 second delay

        Handler textHandler = new Handler();
        textHandler.postDelayed(new Runnable() {
            public void run() {
                questionTextView.setText("");
            }
        }, 5300);

        //call launch rocket method.
    }

    /**
     * This method is used to allow the user to click on an answer instead of navigating the rocket.
     * The method checks for both an image or text click to cover both possibilities. The method will
     * check the answer after clicking and continue the game.
     * @param v View - passes a UI object which was clicked to get its ID.
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bluePlanetImage:
                if(answer1TextView.getText().equals(answer)){
                    score += 10;
                    changeQuestion();
                }
                else {
                    livesRemaining -= 1;
                    // vibrate the phone to simulate the rocket crashing
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    changeQuestion();
                }
                break;
            case R.id.orangePlanetImage:
                if(answer2TextView.getText().equals(answer)){
                    score += 10;
                    changeQuestion();
                }
                else {
                    livesRemaining -= 1;
                    // vibrate the phone to simulate the rocket crashing
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    changeQuestion();
                }
                break;
            case R.id.redPlanetImage:
                if(answer3TextView.getText().equals(answer)){
                    score += 10;
                    changeQuestion();
                }
                else {
                    livesRemaining -= 1;
                    // vibrate the phone to simulate the rocket crashing
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    changeQuestion();
                }
                break;
            case R.id.answerOneText:
                if(answer1TextView.getText().equals(answer)){
                    score += 10;
                    changeQuestion();
                }
                else {
                    livesRemaining -= 1;
                    // vibrate the phone to simulate the rocket crashing
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    changeQuestion();
                }
                break;
            case R.id.answerTwoText:
                if(answer2TextView.getText().equals(answer)){
                    score += 10;
                    changeQuestion();
                }
                else {
                    livesRemaining -= 1;
                    // vibrate the phone to simulate the rocket crashing
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    changeQuestion();
                }
                break;
            case R.id.answerThreeText:
                if(answer3TextView.getText().equals(answer)){
                    score += 10;
                    changeQuestion();
                }
                else {
                    livesRemaining -= 1;
                    // vibrate the phone to simulate the rocket crashing
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);
                    changeQuestion();
                }
                break;
        }
    }
}