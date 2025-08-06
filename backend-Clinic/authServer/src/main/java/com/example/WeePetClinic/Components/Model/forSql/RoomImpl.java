package com.example.WeePetClinic.Components.Model.forSql;
import com.example.WeePetClinic.utilities.RoomState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "room")
public class RoomImpl implements RoomOri {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column (name = "room_id")
  private int roomID;

  @Column (name = "room_number")
  private int roomNum;
  @Column (name = "state_of_use")
  RoomState state;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "clinic_id", nullable = false)
  private ClinicImp clinic;

  //getters setters
  @Override
  public int getRoomID() {
    return roomID;
  }

  @Override
  public void setRoomID(int roomID) {
    this.roomID = roomID;
  }

  @Override
  public int getRoomNum() {
    return roomNum;
  }

  @Override
  public void setRoomNum(int roomNum) {
    this.roomNum = roomNum;
  }

  @Override
  public RoomState getState() {
    return state;
  }

  @Override
  public void setState(RoomState state) {
    this.state = state;
  }

  @Override
  public ClinicImp getClinic() {
    return clinic;
  }

  @Override
  public void setClinic(ClinicImp clinic) {
    this.clinic = clinic;
  }
}
