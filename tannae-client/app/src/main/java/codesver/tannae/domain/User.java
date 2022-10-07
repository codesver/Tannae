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
    private Boolean isDriver;
    private Boolean onBoard;
    private Integer point;
    private Float score;

    public User() {
    }

    public User(Integer usn, String id, String pw, String name, String rrn, Boolean gender, String email, String phone, Boolean isDriver, Boolean onBoard, Integer point, Float score) {
        this.usn = usn;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.rrn = rrn;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.isDriver = isDriver;
        this.onBoard = onBoard;
        this.point = point;
        this.score = score;
    }

    public Integer getUsn() {
        return usn;
    }

    public void setUsn(Integer usn) {
        this.usn = usn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getDriver() {
        return isDriver;
    }

    public void setDriver(Boolean driver) {
        isDriver = driver;
    }

    public Boolean getOnBoard() {
        return onBoard;
    }

    public void setOnBoard(Boolean onBoard) {
        this.onBoard = onBoard;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
