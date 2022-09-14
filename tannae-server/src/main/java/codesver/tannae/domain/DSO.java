package codesver.tannae.domain;


import org.json.JSONArray;

import java.util.Optional;

public class DSO<T> {
    private int flag;
    private final Optional<T> option;
    private JSONArray guides;

    public DSO() {
        option = Optional.empty();
    }

    public DSO(int flag) {
        this.flag = flag;
        option = Optional.empty();
    }

    public DSO(int flag, T t) {
        this.flag = flag;
        option = Optional.of(t);
    }

    public DSO(int flag, T t, JSONArray guides) {
        this.flag = flag;
        option = Optional.of(t);
        this.guides = guides;
    }

    public DSO<T> setFlag(int flag) {
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
