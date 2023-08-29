import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Main
{
  public static void main(String args[])
  {
    //Primitives
    int int1 = 5;
    long long1 = 100;
    byte byte1 = (byte)0xFF;
    short short1 = (short)0xFFFF;
    boolean boolean1 = true;

    int maxInt = Integer.MAX_VALUE;
    System.out.println("Maximum integer is: " + maxInt);
    int minInt = Integer.MIN_VALUE;
    System.out.println("Mininum integer is: " + minInt);

    float float1 = 0.1f;
    float float2 = 0.2f;
    float float3 = float1 + float2;
    boolean booleanFloat = (float3 == 0.3f);  // true
    System.out.println("booleanFloat: " + booleanFloat);
    System.out.println("float3: " + float3);

    double double1 = 0.1;
    double double2 = 0.2;
    double double3 = double1 + double2;
    boolean booleanDouble = (double3 == 0.3);  // false!
    System.out.println("booleanDouble: " + booleanDouble);
    System.out.println("double3: " + double3);

    char char1 = 'a';
    char char2 = 'b';
    System.out.println("char1: " + char1);
    System.out.println(char1 + char2);

    String hello = "hello";
    String hel = "hel";
    String lo = "lo";
    System.out.println("\"hello\" == \"hel\" + \"lo\": " + ("hello" == ("hel" + "lo")));
    System.out.println("\"hello\" == hel + lo: " + ("hello" == (hel + lo)));  // false!
    System.out.println("hello == \"hel\" + \"lo\": " + (hello == ("hel" + "lo")));
    System.out.println("hello == hel + lo: " + (hello == (hel + lo)));  // false!
    System.out.println("\"hello\".equals(\"hel\" + \"lo\"): " + ("hello".equals("hel" + "lo")));
    System.out.println("\"hello\".equals(hel + lo): " + ("hello".equals(hel + lo)));
    System.out.println("hello.equals(\"hel\" + \"lo\"): " + (hello.equals("hel" + "lo")));
    System.out.println("hello.equals(hel + lo): " + (hello.equals(hel + lo)));

    int int2 = 20;
    String numberString1 = "30";
    System.out.println("int2 + numberString1: " + (int2 + numberString1));  // 2030
    System.out.println("int2 + Integer.fromString(numberString1): " + (int2 + Integer.valueOf(numberString1)));  // 50

    // Random
    // Create an instance of random
    Random rand = new Random();

    //Generate random Doubles
    Double DoubleRandom1 = rand.nextDouble();
    double doubleRandom2 = rand.nextDouble();
    System.out.println("DoubleRandom1: " + DoubleRandom1 + " doubleRandom2: " + doubleRandom2);

    //Generate random Longs
    Long LongRandom1 = rand.nextLong();
    long longRandom2 = rand.nextLong(100);
    System.out.println("LongRandom1: " + LongRandom1 + " longRandom2: " + longRandom2);

    //Generate random Integers
    Integer IntegerRandom2 = rand.nextInt(100);
    int intRandom1 = rand.nextInt();
    System.out.println("intRandom1: " + intRandom1 + " IntegerRandomRandom2: " + IntegerRandom2);

    //Generate random Boolean, float, other
    Boolean BoolRandom = rand.nextBoolean();
    Float FloatRandom = rand.nextFloat();
    System.out.println("boolRandom1: " + BoolRandom + " floatRandom: " + FloatRandom);

    // Conditionals
    // If-else statement
    int number1 = 7;
    int number2 = 10;
    if (number2 > number1) {
      // condition 1
      System.out.println(number2 + " is greater than " + number1);
    } else if (number2 == number1) {
      //condition 2
      System.out.println("The numbers are equal");
    } else {
      // alternative condition
      System.out.println(number2 + " is less than " + number1);
    }

    // Switch
    int month = 8;
    String monthString;
    switch (month) {
      case 1:  monthString = "January";
        break;
      case 2:  monthString = "February";
        break;
      case 3:  monthString = "March";
        break;
      case 4:  monthString = "April";
        break;
      case 5:  monthString = "May";
        break;
      case 6:  monthString = "June";
        break;
      case 7:  monthString = "July";
        break;
      case 8:  monthString = "August";
        break;
      case 9:  monthString = "September";
        break;
      case 10: monthString = "October";
        break;
      case 11: monthString = "November";
        break;
      case 12: monthString = "December";
        break;
      default: monthString = "Invalid month";
        break;
    }
    System.out.println(monthString);


    // Loops
    int counter = 0;
    while (counter < 5)
    {
        System.out.println("counter: " + counter);
        counter++;
        if (counter > 2)
        {
          break;
        }
    }

    int[] intArray1 = {3, 2, 5};

    for(int i = 0; i < intArray1.length; i++)
    {
        System.out.println("intArray[" + i + "]: " + intArray1[i]);

        //if (i >= 1)
        //{
        //  break;
        //}
    }

    //
    for(int currentInt : intArray1)
    {
        System.out.println("currentInt: " + currentInt);
    }

    System.out.println("intArray: " + intArray1);
    System.out.println("intArray: " + Arrays.toString(intArray1));

    int newInt = 5;
    newInt = returnInt(3);
    System.out.println("newInt: " + newInt);
  }



  // Modifier Modifier return-Type NameOfFunction(PARAMETERS) {//Logic}
  public static int returnInt(int intArg)
  {
      return intArg;
  }
}

// References:
// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html
