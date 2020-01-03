public class LeapYear{
    public static void main(String[] args){
        /** Method testing whether a year is a leap year or not */
        int year = 2000;

        if(year % 400 == 0){
            System.out.println(year + " is a leap year");
        }else if((year % 4 == 0) && (year % 100 != 0)){
            System.out.println(year + " is a leap year");
        }else{
            System.out.println(year + " is not a leap year");
        }
    }
}
