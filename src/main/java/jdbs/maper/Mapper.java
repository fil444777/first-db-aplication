package jdbs.maper;

public interface Mapper<T, F> {
    T mapFrom (F f);
}
