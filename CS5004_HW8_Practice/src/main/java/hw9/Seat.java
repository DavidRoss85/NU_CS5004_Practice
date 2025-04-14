package hw9;

import java.util.Objects;

/**
 * Represents a seat in a theater row.
 */
public class Seat {

  private String name;
  private String reservedFor;
  private Boolean reserved;

  /**
   * Constructs a Seat with a name, a reservation holder, and reservation status.
   *
   * @param name The identifier for the seat.
   * @param reservedFor The person or group the seat is reserved for.
   * @param reserved {@code true} if the seat is reserved, {@code false} otherwise.
   */
  public Seat(String name, String reservedFor, Boolean reserved) {
    this.name = name;
    this.reservedFor = reservedFor;
    this.reserved = reserved;
  }

  /**
   * Returns the name of the seat.
   *
   * @return The seat name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the reservation holder.
   *
   * @return The name of the person or group the seat is reserved for.
   */
  public String getReservedFor() {
    return reservedFor;
  }

  /**
   * Returns whether the seat is reserved.
   *
   * @return {@code true} if the seat is reserved, {@code false} otherwise.
   */
  public Boolean isReserved() {
    return reserved;
  }

  /**
   * Sets the seat name.
   *
   * @param name The new name for the seat.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the reservation holder.
   *
   * @param reservedFor The new reservation holder's name.
   */
  public void setReservedFor(String reservedFor) {
    this.reservedFor = reservedFor;
  }

  /**
   * Sets the reservation status of the seat.
   *
   * @param reserved {@code true} if reserved, {@code false} otherwise.
   */
  public void setReserved(Boolean reserved) {
    this.reserved = reserved;
  }

  /**
   * Returns a string representation of the seat.
   *
   * @return A string describing the seat and its reservation status.
   */
  @Override
  public String toString() {
    return "Seat{" +
        "name='" + name + '\'' +
        ", reservedFor='" + reservedFor + '\'' +
        ", reserved=" + reserved +
        '}';
  }

  /**
   * Compares this Seat to another object for equality.
   *
   * @param o The object to compare.
   * @return {@code true} if the objects are equal, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Seat seat = (Seat) o;
    return Objects.equals(name, seat.name) && Objects.equals(reservedFor, seat.reservedFor);
  }

  /**
   * Returns the hash code for this Seat.
   *
   * @return The hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, reservedFor);
  }
}