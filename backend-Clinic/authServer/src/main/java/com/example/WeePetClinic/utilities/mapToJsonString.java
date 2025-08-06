package com.example.WeePetClinic.utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class mapToJsonString {

  public static String mapToJsonString(Map<String, Object> input) {
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonStr = "";
    try {
      jsonStr = objectMapper.writeValueAsString(input);
      System.out.println(jsonStr);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return jsonStr;
  }
}
