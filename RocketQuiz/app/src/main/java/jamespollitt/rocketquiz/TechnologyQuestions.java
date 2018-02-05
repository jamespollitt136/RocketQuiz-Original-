package jamespollitt.rocketquiz;

public class TechnologyQuestions {

    // array holding all questions for the Technology Quiz
    private String questions[] = {
            "The Operating System 'Android' is owned by which company?",
            "Which number did Apple & Windows skip for one of their products?",
            "Transistors doubling yearly is known as __________?",
            "Which of the above is not a programming language?",
            "'Home' is a product of which company?",
            "VR is an abbreviation for what term?",
            "iOS devices belong to which company?",
            "Aibo was a robot ______",
            "Which of the above is a cloud based storage service?",
            "Which of the above is a music recognition service?",
            "Which social network began as 'FaceMash'?",
            "Which platform incorporates Bitmoji's?",
            "What company created the ThinkPad?"
    };

    // 2D array to hold all possible options for each question
    private String options[][] = {
            {"Apple", "Google", "Microsoft"},
            {"9", "8", "7"},
            {"Murphy's Law", "Sod's Law", "Moore's Law"},
            {"Java", "C#", "CSS"},
            {"Google", "Oracle", "Apple"},
            {"Very Real", "Virtual Reality", "Vue Ray"},
            {"Microsoft", "HP", "Apple"},
            {"Dog", "Cat", "Fish"},
            {"Firefox", "DropBox", "Edge"},
            {"Shazam", "Kazoo", "Groove"},
            {"Snapchat", "LinkedIn", "Facebook"},
            {"Facebook", "Twitter", "Snapchat"},
            {"Apple", "IBM", "Dell"}
    };

    // array to hold the correct answers for each question
    private String correct[] = {
            "Google", "9", "Moore's Law", "CSS", "Google", "Virtual Reality", "Apple", "Dog", "Dropbox",
            "Shazam", "Facebook", "Snapchat", "IBM"
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
