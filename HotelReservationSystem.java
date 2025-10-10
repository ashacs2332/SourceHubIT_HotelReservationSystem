package learn;

import java.util.ArrayList;
import java.util.Scanner;

class Customer {
    private String name;
    private String email;
    private String phone;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }
}

class Room {
    private int roomNumber;
    private String roomType;
    private boolean isBooked;

    public Room(int roomNumber, String roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        isBooked = true;
    }

    public void releaseRoom() {
        isBooked = false;
    }

   
    public String toString() {
        return "Room No: " + roomNumber + " | Type: " + roomType + " | Status: " + (isBooked ? "Booked" : "Available");
    }
}

class Reservation {
    private Customer customer;
    private Room room;
    private String checkInDate;
    private String checkOutDate;

    public Reservation(Customer customer, Room room, String checkInDate, String checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

  
    public String toString() {
        return "Customer: " + customer.getName() + " | Room: " + room.getRoomNumber() +
                " (" + room.getRoomType() + ") | Check-In: " + checkInDate + " | Check-Out: " + checkOutDate;
    }
}

public class HotelReservationSystem {
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Reservation> reservations = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeRooms();
        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. View All Reservations");
            System.out.println("4. Check-In");
            System.out.println("5. Check-Out");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> searchAvailableRooms();
                case 2 -> bookRoom();
                case 3 -> viewAllReservations();
                case 4 -> checkIn();
                case 5 -> checkOut();
                case 6 -> System.out.println("Thank you for using Hotel Reservation System!");
                default -> System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Single"));
        rooms.add(new Room(102, "Single"));
        rooms.add(new Room(201, "Double"));
        rooms.add(new Room(202, "Double"));
        rooms.add(new Room(301, "Deluxe"));
        rooms.add(new Room(302, "Deluxe"));
    }

    private static void searchAvailableRooms() {
        System.out.print("Enter Room Type to Search (Single/Double/Deluxe): ");
        String type = sc.nextLine();
        boolean found = false;

        System.out.println("\n--- Available " + type + " Rooms ---");
        for (Room room : rooms) {
            if (room.getRoomType().equalsIgnoreCase(type) && !room.isBooked()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available " + type + " rooms at the moment!");
        }
    }

    private static void bookRoom() {
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();
        Customer customer = new Customer(name, email, phone);

        searchAvailableRooms();

        System.out.print("Enter Room Number to Book: ");
        int roomNumber = sc.nextInt();
        sc.nextLine();

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && !room.isBooked()) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not available or invalid room number.");
            return;
        }

        System.out.print("Enter Check-In Date (dd-mm-yyyy): ");
        String checkIn = sc.nextLine();
        System.out.print("Enter Check-Out Date (dd-mm-yyyy): ");
        String checkOut = sc.nextLine();

        selectedRoom.bookRoom();
        reservations.add(new Reservation(customer, selectedRoom, checkIn, checkOut));

        System.out.println("\n Room Booked Successfully!");
    }

    private static void viewAllReservations() {
        System.out.println("\n--- ALL RESERVATIONS ---");
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation res : reservations) {
                System.out.println(res);
            }
        }
    }

    private static void checkIn() {
        System.out.print("Enter Room Number for Check-In: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Reservation res : reservations) {
            if (res.getRoom().getRoomNumber() == roomNo) {
                System.out.println(" " + res + " — Checked In Successfully!");
                return;
            }
        }
        System.out.println("No booking found for this room.");
    }

    private static void checkOut() {
        System.out.print("Enter Room Number for Check-Out: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Reservation res : reservations) {
            if (res.getRoom().getRoomNumber() == roomNo) {
                res.getRoom().releaseRoom();
                reservations.remove(res);
                System.out.println("Check-Out Completed Successfully!");
                return;
            }
        }
        System.out.println("No booking found for this room.");
    }
}
