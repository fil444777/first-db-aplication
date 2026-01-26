package jdbs.validator;

public interface Validator <T>{
    ValidationResult isValid (T object);
}
