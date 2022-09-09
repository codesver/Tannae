package codesver.tannae.dto;

public class ServiceResponseDTO {

    private int flag;
    private int vsn;
    private int usn;
    private String path;
    private String guides;
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

    public String getGuides() {
        return guides;
    }

    public Integer getPassed() {
        return passed;
    }
}
