package hw9;

import java.util.Objects;

public class Seat {

  private String name;
  private String reservedFor;
  private Boolean reserved;

  public Seat(String name, String reservedFor, Boolean reserved) {
    this.name = name;
    this.reservedFor = reservedFor;
    this.reserved = reserved;
  }

  public String getName() {
    return name;
  }
  public String getReservedFor() {
    return reservedFor;
  }
  public Boolean isReserved() {
    return reserved;
  }

  public void setName(String name) {
    this.name = name;
  }
  public void setReservedFor(String reservedFor) {
    this.reservedFor = reservedFor;
  }
  public void setReserved(Boolean reserved) {
    this.reserved = reserved;
  }

  @Override
  public String toString() {
    return "Seat{" +
        "name='" + name + '\'' +
        ", reservedFor='" + reservedFor + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Seat seat = (Seat) o;
    return Objects.equals(name, seat.name) && Objects.equals(reservedFor,
        seat.reservedFor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, reservedFor);
  }
}
