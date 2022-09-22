package codesver.tannae.domain;

public class Vehicle {

    private Integer vsn;
    private String vrn;
    private Double longitude;
    private Double latitude;
    private Integer num;
    private Boolean running;
    private Boolean gender;
    private Boolean share;
    private User user;

    public Integer getVsn() {
        return vsn;
    }

    public String getVrn() {
        return vrn;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Integer getNum() {
        return num;
    }

    public Boolean getRunning() {
        return running;
    }

    public Boolean getGender() {
        return gender;
    }

    public Boolean getShare() {
        return share;
    }

    public User getUser() {
        return user;
    }
}
