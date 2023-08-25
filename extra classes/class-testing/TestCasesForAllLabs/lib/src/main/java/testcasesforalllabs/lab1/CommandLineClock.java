package testcasesforalllabs.lab1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandLineClock {
  public static void main(String[] args) {
    printClock();
  }

  public static void printClock() {
    int ticksBetweenSeconds = 0;
    int previousSecond = -1;
    
    while (true)
    {
      LocalDateTime now = LocalDateTime.now();
      if (now.getSecond() != previousSecond)
      {
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        String minuteString;
        String secondString;

        if (minute < 10)
        {
          minuteString = ":0" + minute;
        }
        else 
        {
          minuteString = ":" + minute;
        }
        
        if (second < 10)
        {
          secondString = ":0" + second;
        }
        else 
        {
          secondString = ":" + second;
        }

        String manuallyFormattedTime = hour + minuteString + secondString;
        String autoFormattedTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        if(!autoFormattedTime.equals(manuallyFormattedTime))  // should never happen, just checking
        {
          System.out.println("error: auto formatted time is '" + autoFormattedTime + "' and " +
            "manually formatted time is '" + manuallyFormattedTime + "'.");
          break;
        }

        System.out.println(manuallyFormattedTime + " " + ((float)ticksBetweenSeconds / 1_000_000f) + " MHz");
        previousSecond = second;
        ticksBetweenSeconds = 0;
      }
      else
      {
        ticksBetweenSeconds++;
      }
    }
  }
}
