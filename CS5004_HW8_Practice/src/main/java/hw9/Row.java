package hw9;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a row of seats in a theater.
 * Extends {@link ArrayList} to store {@link Seat} objects.
 */
public class Row extends ArrayList<Seat> {

  private final static Integer MAX_SEATS = 20;
  private final static Integer MAX_ROW = 20;
  private final static Integer MIN_SEATS = 1;
  private final static Integer MIN_ROW = 1;

  private Integer rowNumber;
  private Integer numSeats;
  private Boolean wheelChairAccessible;

  /**
   * Constructs a Row with a specified row number, seats, and wheelchair accessibility.
   *
   * @param rowNumber The row number (must be between {@value #MIN_ROW} and {@value #MAX_ROW}).
   * @param seats A list of {@link Seat} objects representing the seats in the row.
   * @param wheelChairAccessible Indicates if the row is wheelchair accessible.
   * @throws IllegalArgumentException If the row number or number of seats is out of valid range.
   */
  public Row(Integer rowNumber, ArrayList<Seat> seats, Boolean wheelChairAccessible) throws IllegalArgumentException {
    validateRowNumber(rowNumber);
    this.importSeats(seats);
    this.rowNumber = rowNumber;
    this.wheelChairAccessible = wheelChairAccessible;
  }

  /**
   * Validates the row number.
   *
   * @param rowNumber The row number to validate.
   * @throws IllegalArgumentException If the row number is out of range.
   */
  private void validateRowNumber(Integer rowNumber) throws IllegalArgumentException {
    if(rowNumber < MIN_ROW || rowNumber > MAX_ROW)
      throw new IllegalArgumentException("Number of rows must fall between " + MIN_ROW + " and " + MAX_ROW);
  }

  /**
   * Validates the number of seats.
   *
   * @param numSeats The number of seats to validate.
   * @throws IllegalArgumentException If the number of seats is out of range.
   */
  private void validateNumSeats(Integer numSeats) throws IllegalArgumentException {
    if(numSeats < MIN_SEATS || numSeats > MAX_SEATS)
      throw new IllegalArgumentException("Number of seats must be between " + MIN_SEATS + " and " + MAX_SEATS);
  }

  /**
   * Helper method: Imports seats into the row.
   * @param seats The list of {@link Seat} objects to import.
   * @throws IllegalArgumentException If the number of seats is invalid.
   */
  private void importSeats(ArrayList<Seat> seats) throws IllegalArgumentException {
    validateNumSeats(seats.size());
    this.addAll(seats);
    numSeats = seats.size();
  }

  /**
   * Returns the row number.
   *
   * @return The row number.
   */
  public Integer getRowNumber() {
    return rowNumber;
  }

  /**
   * Returns the number of seats in the row.
   *
   * @return The number of seats.
   */
  public Integer getNumSeats() {
    return numSeats;
  }

  /**
   * Returns whether the row is wheelchair accessible.
   *
   * @return {@code true} if the row is wheelchair accessible, {@code false} otherwise.
   */
  public Boolean isWheelChairAccessible() {
    return wheelChairAccessible;
  }

  /**
   * Prints the row's seat reservation status.
   */
  public void printRow(){
    System.out.printf("%3s", this.getRowNumber());
    for(Seat seat : this){
      if(seat.isReserved()) {
        System.out.print(" X");
      } else {
        System.out.print(wheelChairAccessible? " =":" _");
      }
    }
    System.out.print("\n");
  }

  /**
   * Returns a string representation of the row.
   *
   * @return A string describing the row.
   */
  @Override
  public String toString() {
    return "Row{" +
        "rowNumber=" + rowNumber +
        ", numSeats=" + numSeats +
        ", wheelChairAccessible=" + wheelChairAccessible +
        "} " + super.toString();
  }

  /**
   * Compares this Row to another object for equality.
   *
   * @param o The object to compare.
   * @return {@code true} if the objects are equal, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Row seats = (Row) o;
    return Objects.equals(rowNumber, seats.rowNumber) && Objects.equals(numSeats,
        seats.numSeats) && Objects.equals(wheelChairAccessible, seats.wheelChairAccessible);
  }

  /**
   * Returns the hash code for this Row.
   *
   * @return The hash code.
   */
  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), rowNumber, numSeats, wheelChairAccessible);
  }
}