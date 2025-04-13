package hw9;

import java.util.Objects;

public class Seat {

  private String name;
  private String reservedFor;

  public Seat(String name, String reservedFor) {
    this.name = name;
    this.reservedFor = reservedFor;
  }

  public String getName() {
    return name;
  }

  public String getReservedFor() {
    return reservedFor;
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
