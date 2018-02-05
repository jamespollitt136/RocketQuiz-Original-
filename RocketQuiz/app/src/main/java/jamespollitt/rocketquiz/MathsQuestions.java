package jamespollitt.rocketquiz;

public class MathsQuestions {

    // array holding all questions for the Maths Quiz
    private String questions[] = {
            "16 + 4 = ?",
            "What is the square root of 64?",
            "14 x 11 = ?",
            "(3 x 4) + 2 x 5 = ?",
            "What is the product of 2 & 8?",
            "What is 15% of 30?",
            "The price of an item increase from £70 by 20%, what is the new price?",
            "There is 5 circles and 3 squares, what is the ratio of squares to circles?",
            "What is the decimal value of 1/4?",
            "What fraction of a year is 4 months?",
            "What is 8.5 rounded to the nearest whole number?"
    };

    // 2D array to hold all possible options for each question
    private String options[][] = {
            {"12", "20", "18"},
            {"8", "7", "6"},
            {"139", "154", "147"},
            {"22", "44", "70"},
            {"10", "258", "16"},
            {"4.5", "5", "6"},
            {"£77", "£81", "£84"},
            {"5:3", "5/3", "3:5"},
            {"0.25", "0.4", "2.5"},
            {"1/3", "1/4", "1/5"},
            {"9", "8", "10"}
    };

    // array to hold the correct answers for each question
    private String correct[] = {
            "20", "8", "154", "22", "16", "4.5", "£84", "3:5", "0.25", "1/3", "9"
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
