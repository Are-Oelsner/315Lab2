import java.util.*;

class Main{
    public static void main(String[] args){
        AutoCorrect autoCorrect = new AutoCorrect("dictionary.txt");
        //AutoCorrectImproved autoCorrectImproved = new AutoCorrectImproved("dictionary.txt");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        for(String str : line.split(" ")){
        	System.out.println(autoCorrect.findClosest(str));
        }
    }
}
