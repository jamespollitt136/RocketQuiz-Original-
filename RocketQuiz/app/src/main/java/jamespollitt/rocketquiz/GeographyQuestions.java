package jamespollitt.rocketquiz;

public class GeographyQuestions {

    // array holding all questions for the Geography Quiz
    private String questions[] = {
            "Which country is landlocked?",
            "Where can Mount Kilimanjaro be found?",
            "Where can The Alps be found?",
            "Which country is Snowdonia in?",
            "The Balearic Islands contain: Ibiza, Majorca & __________",
            "Basque Country can be found in Spain & _________",
            "Which country is 'sandwiched' between China & India?",
            "Tarfu Lake can be found in which region?",
            "Mount Teide can be found on which Spanish island?",
            "Kolkata can be found in which country?",
            "What is the capital of Australia?",
            "Which of the above is NOT part of the UK?",
            "Which US state has the highest population?"
    };

    // 2D array to hold all possible options for each question
    private String options[][] = {
            {"Armenia", "Chile", "Wales"},
            {"Nepal", "Tanzania", "Italy"},
            {"Canada", "France", "Norway"},
            {"Wales", "England", "Scotland"},
            {"Tenerife", "Benidorm", "Menorca"},
            {"Portugal", "France", "Morroco"},
            {"Nepal", "Russia", "Turkey"},
            {"Yukon", "Ohio", "Quebec"},
            {"Majorca", "Lanzarote", "Tenerife"},
            {"Pakistan", "India", "Malaysia"},
            {"Queensland", "Sydney", "Canberra"},
            {"N.Ireland", "Rep.Ireland", "Wales"},
            {"California", "Florida", "New York"}
    };

    // array to hold the correct answers for each question
    private String correct[] = {
            "Armenia", "Tanzania", "France", "Wales", "Menorca", "France", "Nepal", "Yukon", "Tenerife",
            "India", "Canberra", "Rep.Ireland", "California"
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
