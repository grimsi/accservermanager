package grimsi.accservermanager.backend.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets InstanceState
 */
public enum InstanceState {
  STOPPED("stopped"),
    RUNNING("running"),
    CRASHED("crashed"),
    UNKNOWN("unknown");

  private String value;

  InstanceState(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static InstanceState fromValue(String text) {
    for (InstanceState b : InstanceState.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
