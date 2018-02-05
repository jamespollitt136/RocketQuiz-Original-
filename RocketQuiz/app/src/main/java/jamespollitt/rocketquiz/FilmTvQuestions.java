package jamespollitt.rocketquiz;

public class FilmTvQuestions {

    // array holding all questions for the Arts & Entertainment Quiz
    private String questions[] = {
            "Marvel's Black Panther is set in which fictional country?",
            "Which actress portrays Supergirl in the CW TV series?",
            "Captain Cold is the nemesis of which superhero?",
            "Leonardo Di Caprio won his first Oscar for which film?",
            "Luke Skywalker is the hero of which Sci-Fi movies?",
            "Steve Carell is the star of which animated film?",
            "Which actor is former WWE Superstar 'The Rock'?",
            "Slumdog Millionaire was largely filmed in which country?",
            "Which film centers around a talking teddy bear?",
            "Which company is the focus of 'The Social Network'?",
            "Westeros and Essos are continents from which TV show?",
            "Which film is famous for its 'found footage' style?",
            "TV show 'Supernatural' centers around which family?",
            "What type of fish is Nemo in 'Finding Nemo'?",
            "Which TV show features humans returning to Earth from Space?",
            "Fill in the blank: Chandler, Ross & _________",
            "Kingpin is the villain in which TV series?",
            "Fill in the blank: Chris, Meg & __________",
            "In American Dad, what is Roger?",
            "13 Reasons Why focuses on which issue?",
            "Archie Andrews is from which fictional city?",
            "Who is NOT a member of 'The Defenders'?",
            "Who stars in 'Superbad'?",
            "Which TV show stars Michelle Keegan as a medic in the army?",
            "What is the surname of Morty from 'Rick & Morty'?",
            "Oliver Queen is the main character on which TV show?",
            "The Walking Dead focuses on which character awaking from a coma?",
            "Who is famous for wielding a baseball bat in The Walking Dead?",
            "S.T.A.R Labs is the HQ for which TV superhero?",
            "James St. Patrick is the main character in which TV show?",
            "Who are known as 'four friends who compete to embarrass each other'?"
    };

    // 2D array to hold all possible options for each question
    private String options[][] = {
            {"Wakanda", "Latveria", "Sokovia"},
            {"Amy Adams", "Eva Green", "Melissa Benoist"},
            {"Flash", "Captain America", "Atom"},
            {"Titanic", "The Revenant", "Romeo & Juliet"},
            {"Star Trek", "Alien", "Star Wars"},
            {"Despicable Me", "Toy Story", "Cars"},
            {"Dave Bautista", "Dwayne Johnson", "John Cena"},
            {"India", "Colombia", "Chile"},
            {"Logan", "Ted", "Wall-E"},
            {"Twitter", "Snapchat", "Facebook"},
            {"Mad Men", "Game of Thrones", "The 100"},
            {"Chronicle", "Life of Pi", "Baby Driver"},
            {"Addams", "Winchesters", "Knope"},
            {"Clownfish", "Goldfish", "Sailfish"},
            {"The Middle", "The 100", "Dexter"},
            {"Kyle", "Mike", "Joey"},
            {"Daredevil", "Mad Men", "SOA"},
            {"Stan", "Stewie", "Steve"},
            {"Dog", "Alien", "Child"},
            {"Suicide", "Cancer", "Drugs"},
            {"Krypton", "Winterfell", "Riverdale"},
            {"Luke Cage", "The Punisher", "Iron Fist"},
            {"Jonah Hill", "Ryan Reynolds", "James Franco"},
            {"Our Girl", "Coronation Street", "Holby City"},
            {"Griffin", "Smith", "Marsh"},
            {"Arrow", "Daredevil", "The Flash"},
            {"Shane", "Rick", "Morgan"},
            {"Rick", "Carl", "Negan"},
            {"The Flash", "The Atom", "Jessica Jones"},
            {"Mad Men", "Power", "Breaking Bad"},
            {"Impractical Jokers", "A League of Their Own", "Jackass"}
    };

    // array to hold the correct answers for each question
    private String correct[] = {
            "Wakanda", "Melissa Benoist", "Flash", "The Revenant", "Star Wars", "Despicable Me",
            "Dwayne Johnson", "India", "Ted", "Facebook", "Game of Thrones", "Chronicle", "Winchesters",
            "Clownfish", "The 100", "Joey", "Daredevil", "Stewie", "Alien", "Suicide", "Riverdale",
            "The Punisher", "Jonah Hill", "Our Girl", "Smith", "Arrow", "Rick", "Negan", "The Flash",
            "Power", "Impractical Jokers"
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
