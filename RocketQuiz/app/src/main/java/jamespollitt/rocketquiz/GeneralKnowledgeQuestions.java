package jamespollitt.rocketquiz;

public class GeneralKnowledgeQuestions {

    // array holding all questions for the GeneralKnowledge Quiz
    private String questions[] = {
            "What historical figure is Santa Claus based on?",
            "How many states make up the United States of America?",
            "What is the surname of the main family in Family Guy?",
            "How many daughters do Homer & Marge Simpson have?",
            "Which musician did Kim Kardashian marry in 2014?",
            "Which English football team are known as 'Spurs'?",
            "What number is Steph Curry's jersey for Golden State Warriors?",
            "Off The Wall is a slogan known for which shoe brand?",
            "Which country is between Mexico & Canada?",
            "Which company is known as 20th Century ________?",
            "Donald Trump is the ___th president of the USA.",
            "Curtis Jackson is the real name of which rapper?",
            "Which rap group is comprised of Killer Mike & El-P?",
            "What animal is 2017 the year of in terms of Chinese New Year?",
            "Which year did World War 2 begin?",
            "Which year did the Falklands War take place?",
            "How many stars are on the flag of China?"
    };

    // 2D array to hold all possible options for each question
    private String options[][] = {
            {"Jesus", "St. Nicholas", "St. Paul"},
            {"50", "52", "48"},
            {"Smith", "Griffin", "Simpson"},
            {"1", "2", "3"},
            {"Jay-Z", "Ray-J", "Kanye West"},
            {"Swansea", "Tottenham", "Burnley"},
            {"23", "17", "30"},
            {"Vans", "Converse", "Nike"},
            {"Spain", "Honduras", "USA"},
            {"Sky", "Fox", "NBC"},
            {"45", "44", "35"},
            {"Eminem", "Big Boi", "50 Cent"},
            {"Outkast", "Run The Jewels", "Wu-Tang Clan"},
            {"Dog", "Rooster", "Rat"},
            {"1914", "1954", "1939"},
            {"1982", "1986", "1988"},
            {"3", "1", "5"}
    };

    // array to hold the correct answers for each question
    private String correct[] = {
            "St. Nicholas", "50", "Griffin", "2", "Kanye West", "Tottenham", "30", "Vans", "USA", "Fox",
            "45", "50 Cent", "Run The Jewels", "Rooster", "1939", "1982", "5"
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
