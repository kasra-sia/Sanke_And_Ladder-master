package ir.sharif.math.bp99_1.snake_and_ladder.model;


import java.util.*;

public class Dice {
   private List<Integer> numbers ;
   private Player player;
   private Map<Integer,Integer> numberChance;
   private int currentNumber;
    private int previousNumber;



    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */
    public Dice() {
        numbers = new LinkedList<>();
        this.numbers.add(1);
        this.numbers.add(2);
        this.numbers.add(3);
        this.numbers.add(4);
        this.numbers.add(5);
        this.numbers.add(6);

        numberChance = new HashMap<>();
        this.numberChance.put(1,1);
        this.numberChance.put(2,1);
        this.numberChance.put(3,1);
        this.numberChance.put(4,1);
        this.numberChance.put(5,1);
        this.numberChance.put(6,1);
    }

    public Player getPlayer() { return player;}

    public void setPlayer(Player player) { this.player = player;}

    public int getCurrentNumber() { return currentNumber; }

    public int getPreviousNumber() {
        return previousNumber;
    }

    public void setPreviousNumber(int previousNumber) {
        this.previousNumber = previousNumber;
    }

    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public int roll() {
        Random random = new Random();
        int index = random.nextInt(numbers.size());
       currentNumber = numbers.get(index);
       if (currentNumber == 2){
           numbers.clear();
           this.numbers.add(1);
           this.numbers.add(2);
           this.numbers.add(3);
           this.numbers.add(4);
           this.numbers.add(5);
           this.numbers.add(6);

           numberChance.clear();
           this.numberChance.put(1,1);
           this.numberChance.put(2,1);
           this.numberChance.put(3,1);
           this.numberChance.put(4,1);
           this.numberChance.put(5,1);
           this.numberChance.put(6,1);
       }



//
//       if (previousNumber==currentNumber)
//           addChance(currentNumber,1);
//       else if (previousNumber!=0)previousNumber=0;
//       else if (previousNumber==0)previousNumber=currentNumber;



//        numbers.get(index)
        return numbers.get(index);
    }

    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negative(it can be zero)
     */
    public void addChance(int number, int chance) {
        if (number > 0) {
            int count = 1;
            for (int a : numbers) {
                if (a == number) count++;
            }
            if (count <= 8) {
                int temp = numberChance.get(number);
                if (chance + temp > 0)
                    numberChance.replace(number, chance + temp);

                else numberChance.replace(number, 0);

                for (int i = 0; i < numbers.size(); i++) {
                    if (numbers.get(i) == number) numbers.remove(i);
                }
                for (int i = 0; i < numberChance.get(number); i++) {
                    numbers.add(number);
                }

            }

        }
    }
    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public String getDetails() {


        return "chance of One : "+numberChance.get(1)+"\nchance of Two : "+numberChance.get(2)+"\nchance of Three : "+numberChance.get(3)
                +"\nchance of Four : "+numberChance.get(4)+"\nchance of Five : "+numberChance.get(5)+"\nchance of Six : "+numberChance.get(6);
    }

    public String toKasraString() {
        String s = "";
        for (Map.Entry entry : numberChance.entrySet())
            s += entry.getKey() + " " + entry.getValue() + " ";
        return s;
    }
}
