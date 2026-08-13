import java.util.Random;
import java.util.Scanner;

public class RPSLS {

    enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    static int winner(Move a, Move b) {
        if (a == b) return 0;

        return switch (a) {
            case SCISSORS -> (b == Move.PAPER || b == Move.LIZARD) ? 1 : -1;
            case PAPER    -> (b == Move.ROCK || b == Move.SPOCK) ? 1 : -1;
            case ROCK     -> (b == Move.LIZARD || b == Move.SCISSORS) ? 1 : -1;
            case LIZARD   -> (b == Move.SPOCK || b == Move.PAPER) ? 1 : -1;
            case SPOCK    -> (b == Move.SCISSORS || b == Move.ROCK) ? 1 : -1;
        };
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        Move[] moves = Move.values();

        int playerScore = 0;
        int computerScore = 0;

        for (int round = 1; round <= 5; round++) {
            System.out.print("Round " + round + " - Enter your move (ROCK, PAPER, SCISSORS, LIZARD, SPOCK): ");
            String input = sc.next().trim().toUpperCase();
            Move playerMove = Move.valueOf(input);

            Move computerMove = moves[rand.nextInt(moves.length)];

            int result = winner(playerMove, computerMove);

            System.out.println("You played: " + playerMove + " | Computer played: " + computerMove);

            if (result == 1) {
                System.out.println("You win this round!");
                playerScore++;
            } else if (result == -1) {
                System.out.println("Computer wins this round!");
                computerScore++;
            } else {
                System.out.println("This round is a tie!");
            }

            System.out.println();
        }

        System.out.println("Final Score - You: " + playerScore + " | Computer: " + computerScore);

        if (playerScore > computerScore) {
            System.out.println("You win " + playerScore + "-" + computerScore);
        } else if (computerScore > playerScore) {
            System.out.println("Computer wins " + computerScore + "-" + playerScore);
        } else {
            System.out.println("It's an overall tie " + playerScore + "-" + computerScore);
        }

        sc.close();
    }
}