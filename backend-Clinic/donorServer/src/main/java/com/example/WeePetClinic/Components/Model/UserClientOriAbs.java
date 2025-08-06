package com.example.WeePetClinic.Components.Model;
import com.example.WeePetClinic.utilities.TypeCreditcard;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserClientOriAbs extends UserOriAbs implements UserClientOri {
  @Column(name = "card_number")
  private String cardNumber;

  @Column(name = "card_type")
  @Enumerated(EnumType.STRING)
  private TypeCreditcard typeCreditcard;

  // getters setters
  @Override
  public String getCardNumber() {
    return cardNumber;
  }

  @Override
  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  @Override
  public TypeCreditcard getTypeCreditcard() {
    return typeCreditcard;
  }

  @Override
  public void setTypeCreditCard(TypeCreditcard typeCreditcard) {
    this.typeCreditcard = typeCreditcard;}
}
