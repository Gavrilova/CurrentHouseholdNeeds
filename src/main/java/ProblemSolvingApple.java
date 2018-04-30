import static java.lang.Character.getNumericValue;

/**
 * Created by irinagavrilova on 3/7/18.
 * <p>
 * Задачка от Эпла сегодня:
 * # # Print the sum of numbers in a string. Consecutive digits should be considered a whole number
 * # # input = 'hkasvfgsdvk12kbdkshfvksd ,bh2mnhjf'
 * # # output = 14 (12+2)
 * Java
 * Apple
 * interview questions
 * fourteen
 * Идея: выделяем числа из строки isDigit и далее складываем.
 */

public class ProblemSolvingApple {

  public static void main(String[] args) {
    String tuple = "hkasvfgsdvk12kbdkshfvksd ,bh2mnhjf";
    int sum = 0;
    char[] charArray = tuple.toCharArray();
    int i = 0;
    while (i < charArray.length) {
      //System.out.println(charArray[i]);
      if (Character.isDigit(charArray[i])) {
        //System.out.println(Character.isDigit(charArray[i]));
        int j = i;
        String subString = "";
        while (Character.isDigit(charArray[j])) {
          subString = subString + charArray[j];
          j++;
        }
        //System.out.println("subString = " + subString);
        sum = sum + Integer.parseInt(subString);
        i = i + j;
      } else i++;
    }
    System.out.println(sum);
  }
}
