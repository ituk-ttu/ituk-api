package ee.ituk.api.common.validation;

import ee.ituk.api.common.exception.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidationResult {

  private final List<ErrorMessage> errors = new ArrayList<>();

  public ValidationResult(ErrorMessage error) {
    this.errors.add(error);
  }

  public ValidationResult(List<ErrorMessage> errors) {
    this.errors.addAll(errors);
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  public ValidationResult add(ErrorMessage error) {
    this.errors.add(error);
    return this;
  }

  public ValidationResult add(List<ErrorMessage> errors) {
    this.errors.addAll(errors);
    return this;
  }

  public ValidationResult add(ValidationResult other) {
    this.errors.addAll(other.getErrors());
    return this;
  }

}
