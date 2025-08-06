package com.example.WeePetClinic.Components.Model.forSql;

import com.example.WeePetClinic.utilities.RoomState;

public interface RoomOri {
  int getRoomID();

  void setRoomID(int roomID);

  int getRoomNum();

  void setRoomNum(int roomNum);

  RoomState getState();

  void setState(RoomState state);

  ClinicImp getClinic();

  void setClinic(ClinicImp clinic);
}
