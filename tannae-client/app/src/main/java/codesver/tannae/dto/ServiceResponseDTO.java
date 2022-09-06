package codesver.tannae.dto;

public class ServiceResponseDTO {

    private int flag;
    private int vsn;
    private int usn;
    private String path;
    private String guider;
    private Integer passed;

    public int getFlag() {
        return flag;
    }

    public int getVsn() {
        return vsn;
    }

    public int getUsn() {
        return usn;
    }

    public String getPath() {
        return path;
    }

    public String getGuider() {
        return guider;
    }

    public Integer getPassed() {
        return passed;
    }
}
