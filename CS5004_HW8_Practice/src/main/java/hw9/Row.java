package hw9;

import java.util.ArrayList;
import java.util.Objects;

public class Row extends ArrayList<Seat> {

  private final static Integer MAX_SEATS = 5;
  private final static Integer MIN_SEATS = 1;
  private final static Integer MIN_ROW = 1;
  private final static Integer MAX_ROW = 5;

  private Integer rowNumber;
  private Integer numSeats;
  private Boolean wheelChairAccessible;

  public Row(Integer rowNumber, Integer numSeats, Boolean wheelChairAccessible) throws IllegalArgumentException {
    validateRowNumber(rowNumber);
    validateNumSeats(numSeats);

    this.rowNumber = rowNumber;
    this.numSeats = numSeats;
    this.wheelChairAccessible = wheelChairAccessible;
  }

  private void validateRowNumber(Integer rowNumber) throws IllegalArgumentException {
    if(rowNumber < MIN_ROW || rowNumber > MAX_ROW)
      throw new IllegalArgumentException("Row cannot be less than " + MIN_ROW + " and greater than " + MAX_ROW);
  }

  private void validateNumSeats(Integer numSeats) throws IllegalArgumentException {
    if(numSeats < MIN_SEATS || numSeats > MAX_SEATS)
      throw new IllegalArgumentException("Number of seats must be between " + MIN_SEATS + " and " + MAX_SEATS);
  }

  public Integer getRowNumber() {
    return rowNumber;
  }

  public Integer getNumSeats() {
    return numSeats;
  }

  public Boolean getWheelChairAccessible() {
    return wheelChairAccessible;
  }

  @Override
  public String toString() {
    return "Row{" +
        "rowNumber=" + rowNumber +
        ", numSeats=" + numSeats +
        ", wheelChairAccessible=" + wheelChairAccessible +
        "} " + super.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Row seats = (Row) o;
    return Objects.equals(rowNumber, seats.rowNumber) && Objects.equals(numSeats,
        seats.numSeats) && Objects.equals(wheelChairAccessible, seats.wheelChairAccessible);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), rowNumber, numSeats, wheelChairAccessible);
  }
}
