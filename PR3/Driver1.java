package PR3;

public class Driver1 {
    public static void main(String[] args) {
        // Sample input cards, including a duplicate ("Ace of Spades")
        Card[] input = {
            new Card("Ace", "Spades"),
            new Card("King", "Hearts"),
            new Card("Queen", "Hearts"),
            new Card("Ace", "Spades"),   // duplicate
            new Card("Jack", "Clubs")
        };

        Card[] cards = new Card[input.length];
        int count = 0;

        for (Card newCard : input) {
            boolean isDuplicate = false;

            // check against all cards already added
            for (int i = 0; i < count; i++) {
                if (cards[i].equals(newCard)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                System.out.println("Duplicate found: " + newCard);
            } else {
                cards[count] = newCard;
                count++;
            }
        }
    }
}