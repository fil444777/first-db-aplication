package hibernate.validator;

public interface Validator <T>{
    ValidationResult isValid (T object);
}
