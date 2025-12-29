import java.sql.*;
import java.util.Scanner;

class MovieTicketBooking {

    static Connection con;

    // Connect to DB
    static void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:C:/SqlLite/univ.db");
    }

    // Insert Movie
    static void insertMovie(Scanner sc) throws Exception {
        String sql = "INSERT INTO Movie VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Movie ID: ");
        ps.setInt(1, sc.nextInt());
        sc.nextLine();

        System.out.print("Movie Name: ");
        ps.setString(2, sc.nextLine());

        System.out.print("Show Time: ");
        ps.setString(3, sc.nextLine());

        System.out.print("Seats: ");
        ps.setInt(4, sc.nextInt());

        ps.executeUpdate();
        System.out.println("Movie inserted successfully.\n");
        ps.close();
    }

    // View all Movies
    static void viewMovie() throws Exception {
        String sql = "SELECT * FROM Movie";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("\nID  Name     Time     Seats");
        System.out.println("-----------------------------");

        while (rs.next()) {
            System.out.println(
                rs.getInt("ID") + "   " +
                rs.getString("Name") + "   " +
                rs.getString("Time") + "   " +
                rs.getInt("Seats")
            );
        }
        rs.close();
        ps.close();
    }

    // Search Movie
    static void searchMovie(Scanner sc) throws Exception {
        String sql = "SELECT * FROM Movie WHERE ID=?";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Enter Movie ID: ");
        ps.setInt(1, sc.nextInt());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println(
                rs.getInt(1) + " " +
                rs.getString(2) + " " +
                rs.getString(3) + " " +
                rs.getInt(4)
            );
        } else {
            System.out.println("Movie not found.");
        }

        rs.close();
        ps.close();
    }

    // Update Seats
    static void updateSeats(Scanner sc) throws Exception {
        String sql = "UPDATE Movie SET Seats=? WHERE ID=?";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("New Seats: ");
        ps.setInt(1, sc.nextInt());

        System.out.print("Movie ID: ");
        ps.setInt(2, sc.nextInt());

        ps.executeUpdate();
        System.out.println("Seats updated.\n");
        ps.close();
    }

    // Delete Movie
    static void deleteMovie(Scanner sc) throws Exception {
        String sql = "DELETE FROM Movie WHERE ID=?";
        PreparedStatement ps = con.prepareStatement(sql);

        System.out.print("Movie ID: ");
        ps.setInt(1, sc.nextInt());

        ps.executeUpdate();
        System.out.println("Movie deleted.\n");
        ps.close();
    }

    public static void main(String[] args) throws Exception {
        connect();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Movie Ticket Booking ---");
            System.out.println("1. Add Movie");
            System.out.println("2. View Movies");
            System.out.println("3. Search Movie");
            System.out.println("4. Update Seats");
            System.out.println("5. Delete Movie");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1: insertMovie(sc); break;
                case 2: viewMovie(); break;
                case 3: searchMovie(sc); break;
                case 4: updateSeats(sc); break;
                case 5: deleteMovie(sc); break;
                case 6: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice");
            }
        } while (choice != 6);

        con.close();
        sc.close();
    }
}
