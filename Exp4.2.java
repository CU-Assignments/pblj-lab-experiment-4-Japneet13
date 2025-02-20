import java.util.*;

class Card {
    String suit;
    String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return Objects.equals(suit, card.suit) && Objects.equals(rank, card.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }
}

class CardCollectionSystem {
    private final Map<String, Set<Card>> cardCollection = new HashMap<>();

    public void addCard(String suit, String rank) {
        cardCollection.putIfAbsent(suit, new HashSet<>());
        Card newCard = new Card(suit, rank);
        if (!cardCollection.get(suit).add(newCard)) {
            System.out.println("Error: Card \"" + newCard + "\" already exists.");
        } else {
            System.out.println("Card added: " + newCard);
        }
    }

    public void findCardsBySuit(String suit) {
        if (cardCollection.containsKey(suit) && !cardCollection.get(suit).isEmpty()) {
            for (Card card : cardCollection.get(suit)) {
                System.out.println(card);
            }
        } else {
            System.out.println("No cards found for " + suit + ".");
        }
    }

    public void displayAllCards() {
        if (cardCollection.isEmpty()) {
            System.out.println("No cards found.");
            return;
        }
        for (Set<Card> cards : cardCollection.values()) {
            for (Card card : cards) {
                System.out.println(card);
            }
        }
    }

    public void removeCard(String suit, String rank) {
        if (cardCollection.containsKey(suit)) {
            Card cardToRemove = new Card(suit, rank);
            if (cardCollection.get(suit).remove(cardToRemove)) {
                System.out.println("Card removed: " + cardToRemove);
                if (cardCollection.get(suit).isEmpty()) {
                    cardCollection.remove(suit);
                }
            } else {
                System.out.println("Error: Card \"" + cardToRemove + "\" not found.");
            }
        } else {
            System.out.println("Error: No cards of suit " + suit + " found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardCollectionSystem ccs = new CardCollectionSystem();

        while (true) {
            System.out.println("\nCard Collection System");
            System.out.println("1. Add Card");
            System.out.println("2. Find Cards by Suit");
            System.out.println("3. Display All Cards");
            System.out.println("4. Remove Card");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Suit: ");
                    String suit = scanner.nextLine();
                    System.out.print("Enter Rank: ");
                    String rank = scanner.nextLine();
                    ccs.addCard(suit, rank);
                    break;
                case 2:
                    System.out.print("Enter Suit to Search: ");
                    String searchSuit = scanner.nextLine();
                    ccs.findCardsBySuit(searchSuit);
                    break;
                case 3:
                    ccs.displayAllCards();
                    break;
                case 4:
                    System.out.print("Enter Suit of Card to Remove: ");
                    String removeSuit = scanner.nextLine();
                    System.out.print("Enter Rank of Card to Remove: ");
                    String removeRank = scanner.nextLine();
                    ccs.removeCard(removeSuit, removeRank);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
