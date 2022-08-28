package codesver.tannae.domain;

public class User {

    private Integer usn;
    private String id;
    private String pw;
    private String name;
    private String rrn;
    private Integer gender;
    private String email;
    private String phone;
    private Integer point;
    private Integer driver;
    private Float score;

    public User() {
    }

    public User(Integer usn, String id, String pw, String name, String rrn, Integer gender, String email, String phone, Integer point, Integer driver, Float score) {
        this.usn = usn;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.rrn = rrn;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.point = point;
        this.driver = driver;
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

    public Integer getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getPoint() {
        return point;
    }

    public Integer getDriver() {
        return driver;
    }

    public Float getScore() {
        return score;
    }
}
