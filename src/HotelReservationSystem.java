import java.sql.*;
import java.util.Scanner;


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
                scanner.nextLine();
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
            scanner.nextLine();
            System.out.println("Enter the Room Number: ");
            int RoomNumber = scanner.nextInt();
            System.out.println("Enter the Contact Number: ");
            String ContactNumber = scanner.next();

            String Sql = "INSERT INTO reservations(guest_name , room_number, contact_number)" +
                    "VALUES (' " +GuestName + " ' " +RoomNumber + " ,  ' " +ContactNumber + " ')";
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
        try{
            System.out.println("Enter the  Reservation  ID: ");
            int ReservationId =  scanner.nextInt();
            System.out.println("Enter Guest Name: ");
            String GuestName = scanner.nextLine();

            String sql = "SELECT * FROM reservations " +
                    "WHERE reservation_id = " +ReservationId +
                    "AND GuestName = ' " +GuestName+ " ' ";
                try(
                     Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql)){

                    if(resultSet.next()){
                        int RoomNumber = resultSet.getInt("RoomNumber");
                        System.out.println("Room Number for Reservation ID:  " +ReservationId +
                                "And Guest " +GuestName + "is : "+RoomNumber);
                    }else {
                        System.out.println("Reservation not found  for the given ID and guest name.");
                    }
                }
             }catch (SQLException e ){
             e.printStackTrace();
        }
     }
       private static void updateReservation(Connection connection , Scanner scanner){
        try{
            System.out.println("Enter the Reservation ID to update: ");
            int ReservationID = scanner.nextInt();
            scanner.nextLine();

            if(!reservationExists(connection , ReservationID)){
                System.out.println("Reservation not found for Given Id");
                return;
            }
            System.out.println("Enter the New GuestName: ");
            String  newGuestName = scanner.nextLine();
            System.out.println("Enter new Room Number");
            int newRoomNumber = scanner.nextInt();
            System.out.println("Enter new contact Number");
            String newContactNumber = scanner.next();

            String sql =  "UPDATE reservations SET guest_name =  ' " +newGuestName + "' , " +
                    "room_number = " +newRoomNumber + " , " +
                    "WHERE reservation_ ID =  " + ReservationID;
            try(Statement statement = connection.createStatement()){
                int affectedrows = statement.executeUpdate(sql);

                if(affectedrows>0){
                    System.out.println("Reservation updated Successfully!");
                }else {
                    System.out.println("Reservation update failed. ");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
       }
        private static void  deleteReservation(Connection connection , Scanner scanner){
        try{
            System.out.println("Enter Reservation ID  to delete: ");
            int ReservationID = scanner.nextInt();

            if(!reservationExists(connection , ReservationID)){
                System.out.println("Reservation not found for given ID: ");
                return;
            }
            String sql = "DELETE * FROM reservations  WHERE reservation_id = " + ReservationID;

            try(Statement statement = connection.createStatement()){
                int affectedrows = statement.executeUpdate(sql);

                if(affectedrows>0){
                    System.out.println("Reservation Deleted successfully!");
                }else {
                    System.out.println("Reservation Deletion Failed!!! ");
                }
                }
            }catch (SQLException e){
             e.printStackTrace();
        }
        }
        private static Boolean reservationExists(Connection connection , int ReservationID ){
          try{
              String sql = "SELECT  reservation_id  FROM reservations  WHERE reservation_id = " + ReservationID;
              try(Statement statement = connection.createStatement();
                  ResultSet resultSet = statement.executeQuery(sql)){

                  return resultSet.next();

              }
          }catch (SQLException e){
              e.printStackTrace();
              return false;
          }
        }
        private static void exit()throws InterruptedException {
            System.out.println("Exiting System: ");
            int i = 5;
            while(i != 0){
                System.out.println(".");
                Thread.sleep(1000);
                i--;
            }
            System.out.println();
            System.out.println("ThankYou for Using Hotel Reservation System!!!");

        }
}

