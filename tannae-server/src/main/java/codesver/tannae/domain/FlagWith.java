package codesver.tannae.domain;


import java.util.Optional;

public class FlagWith<T> {
    private int flag;
    private Optional<T> option;

    public FlagWith(int flag) {
        this.flag = flag;
        option = Optional.empty();
    }

    public FlagWith(int flag, T t) {
        this.flag = flag;
        option = Optional.of(t);
    }

    public int getFlag() {
        return flag;
    }

    public T get() {
        return option.orElse(null);
    }

    public boolean isPresent() {
        return option.isPresent();
    }
}
