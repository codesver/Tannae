package codesver.tannae.dto;

public class ServiceResponseDTO {

    private int flag;
    private int vsn;
    private String summary;
    private String path;
    private int fare;
    private int distance;
    private int duration;

    public int getFlag() {
        return flag;
    }

    public int getVsn() {
        return vsn;
    }

    public String getSummary() {
        return summary;
    }

    public String getPath() {
        return path;
    }

    public int getFare() {
        return fare;
    }

    public int getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }
}
