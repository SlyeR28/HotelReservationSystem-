import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;



public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hoteldb";
    private static final String username = "root";
    private static final String password = "Rishabh@28";

    public static void main (String [] args ) throws ClassNotFoundException , SQLException{

        try{
          Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try{
            Connection connection = DriverManager.getConnection(url , username , password);
            while (true) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get  Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.println("Choose a Option: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        ReserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid  choice. try again.");
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }

    }
    private static void ReserveRoom(Connection connection , Scanner scanner){
        try{
            System.out.println("Enter The Guest Name: ");
            String GuestName = scanner.nextLine();
            System.out.println("Enter the Room Number: ");
            int RoomNumber = scanner.nextInt();
            System.out.println("Enter the Contact Number: ");
            String ContactNumber = scanner.nextLine();

            String Sql = "INSERT INTO reservations(guest_name, room_number , contact_number)" +
                    "VALUES( ' " + GuestName + " ' ," +RoomNumber + "  ' "   +ContactNumber +" ')";
            try (Statement statement = connection.createStatement()){
                int affectedrows =  statement.executeUpdate(Sql);

                if(affectedrows >0){
                    System.out.println("Reservation Successful!");
                }else{
                    System.out.println("Reservation failed!!!!");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
     private static void viewReservation(Connection connection) throws SQLException{
        String sql = "SELECT reservation_id , guest_name, room_number , contact_number , reservation_date FROM reservations";

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){

            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while(resultSet.next()){
                int reservationId  = resultSet.getInt("reservation_id");
                String GuestName =  resultSet.getString("guest_name");
                int RoomNumber = resultSet.getInt("room_number");
                String ContactNumber = resultSet.getString("contact_number");
                String ReservationDate = resultSet.getTimestamp("reservation_date").toString();

                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationId, GuestName, RoomNumber, ContactNumber, ReservationDate);
            }
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
        }
     }
     private static void getRoomNumber(Connection connection , Scanner scanner){

     }
}
