package jamespollitt.rocketquiz;

public class LiteratureQuestions {

    // array holding all questions for the Literature Quiz
    private String questions[] = {
            "The fictional country of Panem appears in which book series?",
            "Which book features a killer clown?",
            "A Song of Ice and Fire was turned into which TV show?",
            "'The Boy Who Lived' is a name belonging to which character?",
            "Tris is the main character from which book series?",
            "To Kill a Mockingbird is a book made by which author?",
            "Which book is NOT made by Stephen King?",
            "A Dance with Dragons is a book by which author?",
            "Paper Towns & The Fault in our Stars are books by which author?",
            "50 Shades of Grey was released in which year?",
            "Who is the author of the Twilight saga?",
            "Charlotte Bronte is known for which book?",
            "Ulysses is a modernist novel by who?"
    };

    // 2D array to hold all possible options for each question
    private String options[][] = {
            {"The Hunger Games", "Divergent", "The Maze Runner"},
            {"The Dark Tower", "It", "Twilight"},
            {"Game of Thrones", "Legion", "The Gifted"},
            {"Jon Snow", "Bilbo Baggins", "Harry Potter"},
            {"Divergent", "The Hunger Games", "Harry Potter"},
            {"J.K.Rowling", "Harper Lee", "J.R.R.Tolkien"},
            {"The Death Cure", "The Dark Tower", "It"},
            {"George R.R. Martin", "Stephen King", "J.K.Rowling"},
            {"Suzanne Collins", "John Greene", "E.L.James"},
            {"2010", "2008", "2011"},
            {"Stephanie Meyer", "Suzanne Collins", "Jane Eyre"},
            {"Jane Eyre", "Macbeth", "Orlando"},
            {"Mark Twain", "Lewis Carroll", "James Joyce"}
    };

    // array to hold the correct answers for each question
    private String correct[] = {
            "The Hunger Games", "It", "Game of Thrones", "Harry Potter", "Divergent", "Harper Lee",
            "The Death Cure", "George R.R. Martin", "John Greene", "2011", "Stephanie Meyer", "Jane Eyre",
            "James Joyce"
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
