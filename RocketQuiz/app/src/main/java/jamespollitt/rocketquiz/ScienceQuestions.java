package jamespollitt.rocketquiz;

public class ScienceQuestions {

    // array holding all questions for the Science Quiz
    private String questions[] = {
            "What is joule a unit of?",
            "Which of the above is found in pencils?",
            "What is the chemical formula for water?",
            "Which is not a greenhouse gas?",
            "Pilots can sometimes feel this force when flying?",
            "What is the resistance of any physical object to the change of its motion?",
            "What is a class of flavoproteins which are sensitive to blue light?",
            "Photosynthesis is the act of absorbing what element?"
    };

    // 2D array to hold all possible options for each question
    private String options[][] = {
            {"Energy", "Force", "Gravity"},
            {"Graphite", "Silver", "Mercury"},
            {"CO2", "H2O", "W2a"},
            {"Carbon Dioxide", "Methane", "Hydrogen"},
            {"Air Force", "G-Force", "X-Force"},
            {"Inertia", "Gravity", "Drag"},
            {"Phytochrome", "OPN5", "Cryptochrome"},
            {"Light", "Water", "Oxygen"}
    };

    // array to hold the correct answers for each question
    private String correct[] = {
            "Energy", "Graphite", "H2O", "Hydrogen", "G-Force", "Inertia", "Cryptochrome", "Light"
    };

    public int numberOfQuestions(){
        return questions.length;
    }

    public String pullQuestion(int i){
        return questions[i];
    }

    public String pullOptionOne(int i){
        return options[i][0];
    }
    public String pullOptionTwo(int i){
        return options[i][1];
    }
    public String pullOptionThree(int i){
        return options[i][2];
    }

    public String pullCorrect(int i){
        return correct[i];
    }
}
