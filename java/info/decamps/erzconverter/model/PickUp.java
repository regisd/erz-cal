package info.decamps.erzconverter.model;

import com.google.auto.value.AutoValue;
import java.time.LocalDate;

@AutoValue
public abstract class PickUp {
  public enum Type {
    BIOABFALL("Bioabfall");
    private final String summary;

    Type(String summary) {
      this.summary = summary;
    }

    @Override
    public String toString() {
      return "ERZ " + summary;
    }
  }

  public abstract String postcode();

  public abstract LocalDate date();

  public abstract Type type();

  public static PickUp create(String postcode, LocalDate date) {
    return new AutoValue_PickUp(postcode, date, Type.BIOABFALL);
  }
}
