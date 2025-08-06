package com.example.WeePetClinic.Components.Model;
import com.example.WeePetClinic.utilities.TypeCreditcard;

public interface UserClientOri extends UserOri {
  String getCardNumber();
  void setCardNumber(String cardNumber);
  TypeCreditcard getTypeCreditcard();
  void setTypeCreditCard(TypeCreditcard typeCreditcard);

}
