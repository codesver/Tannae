package codesver.tannae.dto;

public class RegisterLostDTO {

    private String lost;
    private Integer vsn;

    public RegisterLostDTO(String lost, Integer vsn) {
        this.lost = lost;
        this.vsn = vsn;
    }
}
