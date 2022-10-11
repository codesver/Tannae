package codesver.tannae.dto;

public class UserDTO {

    private Integer usn;
    private String id;
    private String pw;
    private String name;
    private String rrn;
    private Boolean gender;
    private String email;
    private String phone;
    private Boolean isManage;
    private Boolean isDriver;
    private Boolean onBoard;
    private Integer point;
    private Float score;

    public UserDTO() {
    }

    public UserDTO(Integer usn, String id, String pw, String name, String rrn, Boolean gender, String email, String phone, Boolean isManage, Boolean isDriver, Boolean onBoard, Integer point, Float score) {
        this.usn = usn;
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.rrn = rrn;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.isManage = isManage;
        this.isDriver = isDriver;
        this.onBoard = onBoard;
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

    public Boolean getManage() {
        return isManage;
    }

    public Boolean getDriver() {
        return isDriver;
    }

    public Boolean getOnBoard() {
        return onBoard;
    }

    public Integer getPoint() {
        return point;
    }

    public Float getScore() {
        return score;
    }
}
