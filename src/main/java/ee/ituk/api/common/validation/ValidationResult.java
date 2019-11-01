package ee.ituk.api.common.validation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidationResult {

  private final List<ValidationError> errors = new ArrayList<>();

  public ValidationResult(ValidationError error) {
    this.errors.add(error);
  }

  public ValidationResult(List<ValidationError> errors) {
    this.errors.addAll(errors);
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  public ValidationResult add(ValidationError error) {
    this.errors.add(error);
    return this;
  }

  public ValidationResult add(List<ValidationError> errors) {
    this.errors.addAll(errors);
    return this;
  }

  public ValidationResult add(ValidationResult other) {
    this.errors.addAll(other.getErrors());
    return this;
  }

}
