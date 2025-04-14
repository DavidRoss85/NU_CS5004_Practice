package hw9;

public class ReservationsService {


  public void begin(Theater theater) {
    System.out.println("Reservation Service starting...");
    System.out.println("\n");
    displaySeatAssignments(theater);
  }

  private void displaySeatAssignments(Theater theater) {
    theater.printSeats();
  }
}
