package codesver.tannae.domain;


import org.json.JSONArray;

import java.util.Optional;

public class ResultDTO<T> {
    private int flag;
    private final Optional<T> option;
    private JSONArray guides;

    public ResultDTO() {
        option = Optional.empty();
    }

    public ResultDTO(int flag) {
        this.flag = flag;
        option = Optional.empty();
    }

    public ResultDTO(int flag, T t) {
        this.flag = flag;
        option = Optional.of(t);
    }

    public ResultDTO(int flag, T t, JSONArray guides) {
        this.flag = flag;
        option = Optional.of(t);
        this.guides = guides;
    }

    public ResultDTO<T> setFlag(int flag) {
        this.flag = flag;
        return this;
    }

    public int getFlag() {
        return flag;
    }

    public T get() {
        return option.orElse(null);
    }

    public JSONArray getGuides() {
        return guides;
    }

    public boolean isPresent() {
        return option.isPresent();
    }
}
