package testcasesforalllabs.lab1;

import java.util.Random;

public class FlipNCoins {
  public static void main(String[] args) {
    flipNHeads(1);
  }
  
  public static void flipNHeads(int n) {
    Random random = new Random();
    int flips = 0;
    int heads = 0;
    
    while (heads < n) {
      flips++;
      int flip = random.nextInt(2);
      switch (flip)
      {
        case 0:
        {
          heads++;
          System.out.println("heads");
          break;
        }
        case 1:
        {
          heads = 0;
          System.out.println("tails");
          break;
        }
        default:  // should never be hit, just checking
        {
          System.out.println("error, number was: " + flip);
          break;
        }
      }
    }

    // It took FLIP flips to flip N heads"
    System.out.println("It took " + flips + " " + Pluralize.pluralize("flip", flips) +
      " to flip " + heads + " " + Pluralize.pluralize("heads", heads) + ".");
  }
}
