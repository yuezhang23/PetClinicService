package com.example.WeePetClinic.utilities;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class CompositeKey<K1 extends Serializable, K2 extends Serializable> implements Serializable {
  private K1 keyPart1;
  private K2 keyPart2;

  // Default constructor
  public CompositeKey() {}

  // Parameterized constructor
  public CompositeKey(K1 keyPart1, K2 keyPart2) {
    this.keyPart1 = keyPart1;
    this.keyPart2 = keyPart2;
  }

  // Getters and setters
  public K1 getKeyPart1() {
    return keyPart1;
  }

  public void setKeyPart1(K1 keyPart1) {
    this.keyPart1 = keyPart1;
  }

  public K2 getKeyPart2() {
    return keyPart2;
  }

  public void setKeyPart2(K2 keyPart2) {
    this.keyPart2 = keyPart2;
  }

  // Override equals and hashCode
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CompositeKey<?, ?> that = (CompositeKey<?, ?>) o;
    return Objects.equals(keyPart1, that.keyPart1) &&
            Objects.equals(keyPart2, that.keyPart2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(keyPart1, keyPart2);
  }
}

