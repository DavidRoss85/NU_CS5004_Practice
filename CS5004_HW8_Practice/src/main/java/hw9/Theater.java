package hw9;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a theater that contains rows of seats, some of which are wheelchair accessible.
 */
public class Theater {

  private static final Integer MIN_WHEELCHAIR_ROWS = 1;

  private String theaterName;
  private ArrayList<Row> rowList = new ArrayList<>();
  private ArrayList<Integer> wheelChairRowsList = new ArrayList<>();

  /**
   * Constructs a Theater with a name and a list of rows.
   *
   * @param theaterName The name of the theater.
   * @param rows A list of {@link Row} objects representing rows of seats in the theater.
   * @throws IllegalArgumentException If the theater name is invalid or the row list does not meet the required constraints.
   */
  public Theater(String theaterName, ArrayList<Row> rows) throws IllegalArgumentException {
    validateTheaterName(theaterName);
    validateRowList(rows);
    this.theaterName = theaterName;
    this.rowList = rows;
  }

  /**
   * Validates the theater name.
   *
   * @param theaterName The name of the theater.
   * @throws IllegalArgumentException If the name is null or empty.
   */
  private void validateTheaterName(String theaterName) throws IllegalArgumentException {
    if (theaterName == null || theaterName.isEmpty())
      throw new IllegalArgumentException("Invalid theater name");
  }

  /**
   * Validates the row list ensuring no duplicates and at least one wheelchair-accessible row.
   *
   * @param rowList A list of {@link Row} objects.
   * @throws IllegalArgumentException If row constraints are not met.
   */
  private void validateRowList(ArrayList<Row> rowList) throws IllegalArgumentException {
    if (rowList == null || rowList.isEmpty())
      throw new IllegalArgumentException("A theater should have at least one row");

    ArrayList<Integer> importedRowNums = new ArrayList<>();
    for (Row row : rowList) {
      // Ensure no duplicate rows:
      if (importedRowNums.contains(row.getRowNumber())) {
        throw new IllegalArgumentException("Duplicate row number in row list");
      }
      importedRowNums.add(row.getRowNumber());
      updateWheelChairRowsList(row);
    }

    if (wheelChairRowsList.size() < MIN_WHEELCHAIR_ROWS)
      throw new IllegalArgumentException("A theater must include at least " + MIN_WHEELCHAIR_ROWS + " wheelchair-accessible rows");
  }

  /**
   * Updates the list of wheelchair-accessible rows.
   *
   * @param row The row to check for accessibility.
   */
  private void updateWheelChairRowsList(Row row) {
    if (row.isWheelChairAccessible()) {
      wheelChairRowsList.add(row.getRowNumber());
    }
  }

  /**
   * Returns a list of wheelchair-accessible rows.
   *
   * @return A list of row numbers that are wheelchair accessible.
   */
  public ArrayList<Integer> getWheelChairRowsList() {
    return wheelChairRowsList;
  }

  /**
   * Returns the theater name.
   *
   * @return The name of the theater.
   */
  public String getTheaterName() {
    return theaterName;
  }

  /**
   * Returns the list of rows in the theater.
   *
   * @return A list of {@link Row} objects.
   */
  public ArrayList<Row> getRowList() {
    return rowList;
  }

  /**
   * Determines if a row is wheelchair accessible.
   *
   * @param rowNumber The row number to check.
   * @return {@code true} if the row is wheelchair accessible, {@code false} otherwise.
   */
  public Boolean isRowWheelChairAccessible(Integer rowNumber) {
    return wheelChairRowsList.contains(rowNumber);
  }

  /**
   * Prints the seat layout of the theater.
   */
  public void printSeats() {
    for (Row row : getRowList()) {
      row.printRow();
    }
  }

  @Override
  public String toString() {
    return "Theater{" +
        "wheelChairRowsList=" + wheelChairRowsList +
        ", theaterName='" + theaterName + '\'' +
        ", rowList=" + rowList +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Theater theater = (Theater) o;
    return Objects.equals(wheelChairRowsList, theater.wheelChairRowsList)
        && Objects.equals(theaterName, theater.theaterName) && Objects.equals(rowList, theater.rowList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wheelChairRowsList, theaterName, rowList);
  }
}