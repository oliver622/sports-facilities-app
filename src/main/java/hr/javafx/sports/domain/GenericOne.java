package hr.javafx.sports.domain;

public class GenericOne<T>{
    private T value;

    public GenericOne(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
