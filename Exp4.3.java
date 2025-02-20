import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

class TicketBookingSystem {
    private final boolean[] seats;
    private final ReentrantLock lock = new ReentrantLock();

    public TicketBookingSystem() {
        seats = new boolean[20]; 
    }

    public void bookSeat(String user, int seatNumber, boolean isVIP) {
        if (seatNumber < 1 || seatNumber > seats.length) {
            System.out.println(user + ": Invalid seat number!");
            return;
        }

        lock.lock(); 
        try {
            if (!seats[seatNumber - 1]) {
                seats[seatNumber - 1] = true;
                System.out.println(user + " booked seat " + seatNumber);
            } else {
                System.out.println(user + ": Seat " + seatNumber + " is already booked!");
            }
        } finally {
            lock.unlock();
        }
    }
}

class UserThread extends Thread {
    private final TicketBookingSystem system;
    private final String userName;
    private final int seatNumber;
    private final boolean isVIP;

    public UserThread(TicketBookingSystem system, String userName, int seatNumber, boolean isVIP) {
        this.system = system;
        this.userName = userName;
        this.seatNumber = seatNumber;
        this.isVIP = isVIP;
    }

    public boolean isVIP() {
        return isVIP;
    }

    @Override
    public void run() {
        system.bookSeat(userName, seatNumber, isVIP);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicketBookingSystem system = new TicketBookingSystem();
        
        System.out.print("Enter the number of users: ");
        int numUsers = scanner.nextInt();
        scanner.nextLine(); 
        
        UserThread[] users = new UserThread[numUsers];
        
        for (int i = 0; i < numUsers; i++) {
            System.out.print("Enter username: ");
            String userName = scanner.nextLine();
            
            System.out.print("Enter seat number to book: ");
            int seatNumber = scanner.nextInt();
            
            System.out.print("Is the user VIP? (true/false): ");
            boolean isVIP = scanner.nextBoolean();
            scanner.nextLine(); 
            
            users[i] = new UserThread(system, userName, seatNumber, isVIP);
        }
        
        Arrays.sort(users, Comparator.comparing(UserThread::isVIP).reversed());
        
        for (Thread user : users) {
            user.start();
        }

        for (Thread user : users) {
            try {
                user.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
