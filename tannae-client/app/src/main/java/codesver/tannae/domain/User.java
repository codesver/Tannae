package codesver.tannae.domain;

public class User {

    private Integer usn;
    private String id;
    private String pw;
    private String name;
    private String rrn;
    private Boolean gender;
    private String email;
    private String phone;
    private Boolean driver;
    private Boolean board;
    private Integer point;
    private Float score;

    public User() {
    }

    public User(Integer usn, String id, String pw, String name, String rrn, Boolean gender, String email, String phone, Boolean driver, Boolean board, Integer point, Float score) {
        this.usn = usn;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.rrn = rrn;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.driver = driver;
        this.board = board;
        this.point = point;
        this.score = score;
    }

    public Integer getUsn() {
        return usn;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public String getRrn() {
        return rrn;
    }

    public Boolean getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getDriver() {
        return driver;
    }

    public Boolean getBoard() {
        return board;
    }

    public Integer getPoint() {
        return point;
    }

    public Float getScore() {
        return score;
    }
}
