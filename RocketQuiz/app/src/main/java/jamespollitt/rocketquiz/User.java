package jamespollitt.rocketquiz;

public class User {

    private String name;
    private String email;
    private String score;
    private String credits;
    private String correct;
    private String incorrect;
    private String filmPlayed;
    private String genknoPlayed;
    private String geogPlayed;
    private String litPlayed;
    private String mathsPlayed;
    private String sciencePlayed;
    private String techPlayed;

    public User(){
        // Required empty constructor.
    }

    public User(String name, String email, String score, String credits, String correct, String incorrect,
                String filmPlayed, String genknoPlayed, String geogPlayed, String litPlayed, String mathsPlayed,
                String sciencePlayed, String techPlayed){
        this.name = name;
        this.email = email;
        this.score = score;
        this.credits = credits;
        this.correct = correct;
        this.incorrect = incorrect;
        this.filmPlayed = filmPlayed;
        this.genknoPlayed = genknoPlayed;
        this.geogPlayed = geogPlayed;
        this.litPlayed = litPlayed;
        this.mathsPlayed = mathsPlayed;
        this.sciencePlayed = sciencePlayed;
        this.techPlayed = techPlayed;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getScore() {
        return score;
    }

    public String getCredits() {
        return credits;
    }

    public String getCorrect() {
        return correct;
    }

    public String getIncorrect() {
        return incorrect;
    }

    public String getFilmPlayed() {
        return filmPlayed;
    }

    public String getGenknoPlayed() {
        return genknoPlayed;
    }

    public String getGeogPlayed() {
        return geogPlayed;
    }

    public String getLitPlayed() {
        return litPlayed;
    }

    public String getMathsPlayed() {
        return mathsPlayed;
    }

    public String getSciencePlayed() {
        return sciencePlayed;
    }

    public String getTechPlayed() {
        return techPlayed;
    }
}
