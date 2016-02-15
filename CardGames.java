import java.io.*;
import java.util.Random;
import java.util.Scanner;

class Card {
  private int num;
  private int suit;

  private static int DIAMONDS = 1; //private only accessed in class
  private static int CLUBS = 2;//static for shared in all instances
  private static int HEARTS = 3;
  private static int SPADES = 4;

  private static int ace = 1;
  private static int two = 2;
  private static int three = 3;
  private static int four = 4;
  private static int five = 5;
  private static int six = 6;
  private static int seven = 7;
  private static int eight = 8;
  private static int nine = 9;
  private static int ten = 10;
  private static int jack = 11;
  private static int queen = 12;
  private static int king= 13;

  public Card (int num, int suit) {
    if (suit == 0 && num == 0) { //card is undefined
      this.num = 0;
      this.suit = 0;
    }
    if (suit >= 1 && suit <= 4 && num>= 1 && num<= 13) {
      this.num = num;
      this.suit = suit;

    }
    else
      System.out.println("Error. Card not valid");
  }
  public int getSuit() {
    return suit; //Card1.getSuit VS Card.getSuit(Card1)
  }
  public int getNum() {
    return num;
  }
  public String getCardName() {
    String cardName = "";

    if (num == 1)
      cardName = "Ace";
    if (num == 2)
      cardName = "Two";
    if (num == 3)
      cardName = "Three";
    if (num == 4)
      cardName = "Four";
    if (num == 5)
      cardName = "Five";
    if (num == 6)
      cardName = "Six";
    if (num == 7)
      cardName = "Seven";
    if (num == 8)
      cardName = "Eight";
    if (num == 9)
      cardName = "Nine";
    if (num == 10)
      cardName = "Ten";
    if (num == 11)
      cardName = "Jack";
    if (num == 12)
      cardName = "Queen";
    if (num == 13)
      cardName = "King";

    if (suit == 1)
      cardName = cardName + " of Diamonds";
    if (suit == 2)
      cardName = cardName + " of Clubs";
    if (suit == 3)
      cardName = cardName + " of Hearts";
    if (suit == 4)
      cardName = cardName + " of Spades";

    return cardName;

  }
}

class Deck {
  private Card[][] cards;

  public Deck() {

    cards = new Card[13][4];

    for (int num = 1; num<=13; num++) {
      for (int suit = 1; suit<=4; suit++) {
          cards[num-1][suit-1] = new Card(num,suit);
      }
    }
  }
  public Card getCard(int num, int suit) {
      return cards[num-1][suit-1];

  }
  public void removeCard(int num, int suit) {
    cards[num-1][suit-1] = null;
  }
}


public class CardGames {

  public static void main(String[] args) {
    BlackJack.playBlackJack();


  }
}

class BlackJack {
    private static Deck blackJackDeck = new Deck();
    public static int numOfPlayers;
    public static int[] playerScores = new int[6]; //MAX PLAYERS 4
    public static int computerScore;

    public static boolean computerMoves() {
      System.out.println("CPU");
      Random rand = new Random();

      int randomNum;
      int randomSuit;
      Card computerCard1, computerCard2, extraComputerCard;
      computerScore = 0;

      do {
        randomNum = rand.nextInt((13 - 1) + 1) + 1;
        randomSuit = rand.nextInt((4 - 1) + 1) + 1;
        computerCard1 = blackJackDeck.getCard(randomNum, randomSuit);
        //selects random card; will repeat until card is valid

      } while (blackJackDeck.getCard(randomNum,randomSuit) == null);
      blackJackDeck.removeCard(randomNum,randomSuit); //sets deck[][] to null

      do {
        randomNum = rand.nextInt((13 - 1) + 1) + 1;
        randomSuit = rand.nextInt((4 - 1) + 1) + 1;
        computerCard2 = blackJackDeck.getCard(randomNum, randomSuit);
      } while (blackJackDeck.getCard(randomNum,randomSuit) == null);
      blackJackDeck.removeCard(randomNum,randomSuit);

      System.out.println(computerCard1.getCardName());
      System.out.println(computerCard2.getCardName());

      int card1Num = computerCard1.getNum();
      int card2Num = computerCard2.getNum();

      if (card1Num == 1) {
        if (card2Num == 1) {
          computerScore = 11 + 1;
        }
        else if (card2Num >=10) {
          computerScore = 11 + 10;
        } else
          computerScore = 11 + card2Num;
      } else if (card2Num == 1) {
        if (card1Num == 1) {
          computerScore = 11 + 1;
        } else if (card2Num >=10) {
          computerScore = 11 + 10;
        }
        else
          computerScore = 11 + card1Num;
      } else if (card1Num >= 10) {
        computerScore += 10;
        if (card2Num >= 10)
          computerScore += 10;
        else
          computerScore += card2Num;
      } else if (card2Num >= 10) {
        computerScore += 10;

        if(card1Num >=10) {
          computerScore+=10;
        }
        else
          computerScore += card1Num;
      } else {
        computerScore = card1Num + card2Num;
      }

      while (computerScore < 17) {
        do {
          randomNum = rand.nextInt((13 - 1) + 1) + 1;
          randomSuit = rand.nextInt((4 - 1) + 1) + 1;

          extraComputerCard = blackJackDeck.getCard(randomNum, randomSuit);
        } while (blackJackDeck.getCard(randomNum,randomSuit) == null);

        System.out.println("CPU picks another card...");
        System.out.println(extraComputerCard.getCardName());

        blackJackDeck.removeCard(randomNum,randomSuit);

        int extraCardNum = extraComputerCard.getNum();
        //doesn't take into account if cards 1-2 are aces
        if (extraCardNum == 1) {
          if (computerScore <= 10) {
            computerScore += 11; //only add 11 if not busted else add 1
          } else {
            computerScore += 1;
          }
        } else if (extraComputerCard.getNum() >= 10) {
          computerScore += 10;
        } else {
          computerScore += extraComputerCard.getNum();
        }

        if (computerScore > 21) {
          if (card1Num == 1 || card2Num == 1) {
            computerScore -= 10; //change value from 11 to 1
          }
        }

      }


      if (computerScore > 21) {
        System.out.printf("BUSTED. Computer score is %d\n", computerScore);
        return false;
      } else {
        System.out.println("Computer score is " + computerScore);
        return true;
      }
    }

    public static boolean humanMoves(int playerNumber) {
      Random rand = new Random();
      System.out.println("Player " + playerNumber);
      int randomNum = rand.nextInt((13 - 1) + 1) + 1;
      int randomSuit = rand.nextInt((4 - 1) + 1) + 1;
      Card humanCard1, humanCard2, extraHumanCard;
      int humanScore = 0;

      do {
        randomNum = rand.nextInt((13 - 1) + 1) + 1;
        randomSuit = rand.nextInt((4 - 1) + 1) + 1;
        humanCard1 = blackJackDeck.getCard(randomNum, randomSuit);

      } while (blackJackDeck.getCard(randomNum,randomSuit) == null);
      blackJackDeck.removeCard(randomNum,randomSuit);

      do {
        randomNum = rand.nextInt((13 - 1) + 1) + 1;
        randomSuit = rand.nextInt((4 - 1) + 1) + 1;

        humanCard2 = blackJackDeck.getCard(randomNum, randomSuit);
      } while (blackJackDeck.getCard(randomNum,randomSuit) == null);
      blackJackDeck.removeCard(randomNum,randomSuit);

      System.out.println(humanCard1.getCardName());
      System.out.println(humanCard2.getCardName());

      Scanner scan = new Scanner(System.in);
      if (humanCard1.getNum() == 1) {
        int num;
        do {
          System.out.println("Change ace to 1 or 11?");
          num = scan.nextInt();

          if (num != 1 && num != 11)
            System.out.println("Invalid input");
        } while (num != 1 && num !=11);
        scan.nextLine(); // gets rid of new line character
        humanScore += num;
      } else if (humanCard1.getNum() >= 10) {
        humanScore = humanScore + 10;
      } else {
        humanScore += humanCard1.getNum();
      }
      if (humanCard2.getNum() == 1) {
          int num;
          do {
            System.out.println("Change ace to 1 or 11?");
            num = scan.nextInt();

            if (num != 1 && num != 11)
              System.out.println("Invalid input. Please enter 1 or 11.");
          } while (num != 1 && num !=11);

          scan.nextLine(); //gets rid of new line character
          humanScore += num;
      } else if (humanCard2.getNum() >= 10) {
        humanScore += 10;
      }
      else {
        humanScore += humanCard2.getNum();
      }


      boolean valid = true;

      String userInput = "";


      do {
        System.out.println("Would you like another card (Y) or (N)");
        userInput = scan.nextLine();
        valid = (userInput.equals("Y") || userInput.equals("N"));

        if (valid == false) {
          System.out.println("Invalid input. Please enter Yes or No");
        } else if (userInput.equals("N")) {
          break;
        } else if (userInput.equals("Y")) {
          do {
            randomNum = rand.nextInt((13 - 1) + 1) + 1;
            randomSuit = rand.nextInt((4 - 1) + 1) + 1;

            extraHumanCard = blackJackDeck.getCard(randomNum, randomSuit);
          } while (blackJackDeck.getCard(randomNum,randomSuit) == null);
          blackJackDeck.removeCard(randomNum,randomSuit);

          System.out.println(extraHumanCard.getCardName());

          if (extraHumanCard.getNum() >= 10) {
            humanScore += 10;
          }
          else {
            humanScore += extraHumanCard.getNum();
          }

          if (humanScore > 21) {
            System.out.printf("BUSTED. Player %d score is %d\n", playerNumber,humanScore);
            playerScores[playerNumber-1] = 0;
            return false;
          }
          else
            valid = false; //repeat loop for more user input
        }
      } while (valid == false);


      System.out.printf("Player %d score is %d\n\n", playerNumber,humanScore );
      playerScores[playerNumber-1] = humanScore;

      return true;
    }

    public static void playBlackJack() {

      System.out.println("\nBLACKJACK GAME\n");
      Scanner scan = new Scanner(System.in);

      System.out.println("How many players?");
      numOfPlayers = scan.nextInt();
      int i = 0;
      boolean noHumanWins = true;
      while (i < numOfPlayers) {
        if (BlackJack.humanMoves(i+1) == true)
          noHumanWins = false;
          System.out.println(playerScores[i] + "\n");
        i++;

      }
      if (noHumanWins == false) { //at least one human alive
        if (BlackJack.computerMoves() == true) {
          for (int j = 0; j< numOfPlayers; j++) {
            if (computerScore >= playerScores[j]) {
              System.out.printf("CPU beats Player %d\n", j+1);
            }
            else
              System.out.printf("Player %d beats CPU\n", j+1);

          }
        }  else {
          System.out.println("CPU BUSTED. ALL HUMANS WIN");
        }
      } else
        System.out.println("No human wins. CPU wins by default");

    }

}
