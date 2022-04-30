package project2csc4101;

public class Convertor {
   
    public static StringBuilder IEEE754(double input){
        
        StringBuilder buildString = new StringBuilder();
        double gettingRidOfNegative = Math.abs(input);
        int wholeNum = (int) gettingRidOfNegative;
        double decimal = gettingRidOfNegative - wholeNum;
        
        if (wholeNum == 0) {
            buildString.append(posOrNeg(input)).append(" | ").append(mantissaSpecial(gettingRidOfNegative));
        } else {
            buildString.append(posOrNeg(input)).append(" | ").append(mantissa(wholeNum, decimal));
        }
        return buildString;
    }
    public static String posOrNeg(double input) {
        if(input < 0) {
            return "1";
        } else if (input > 0) {
            return  "0";
        } else {
            return "Error";  
        }
    }
    public static StringBuilder mantissa(int wholeNum, double decimal) {
        
        StringBuilder mantissa = new StringBuilder();
        String wholeNumBinary = Binary(wholeNum);
        String decimalBinary = mantissaBinary(decimal);
        
        int findingTheOne = wholeNumBinary.indexOf("1");
        String firstHalf = wholeNumBinary.substring(findingTheOne + 1, wholeNumBinary.length());
        String totalMantissa = firstHalf + decimalBinary;
        if (totalMantissa.length() > 23) {
            int num = totalMantissa.length() - 23;
            int theDifference = totalMantissa.length() - num;
            totalMantissa = totalMantissa.substring(0, theDifference);
        }
        int exponentNum = wholeNumBinary.length() - (findingTheOne + 1);
        int exponent = 127 + (exponentNum);
        String exponentStr = Binary(exponent);
        if(exponentStr.length() < 8) {
            exponentStr = 0 + exponentStr;
        }
        mantissa.append(exponentStr).append(" | ").append(totalMantissa);
        return mantissa;
    }
    public static StringBuilder mantissaSpecial(double input) {
        
        StringBuilder mantissaSpecial = new StringBuilder();
        int exponentNum = 0;
        double answer = 0;
        while (answer < 1) {
            exponentNum--;
            answer = input / Math.pow(2, exponentNum);
        }
        int exponent = 127 - Math.abs(exponentNum);
        String exponentStr = Binary(exponent);
        if(exponentStr.length() < 8) {
            exponentStr = 0 + exponentStr;
        }
        String decimalStr = mantissaBinary(answer);
        mantissaSpecial.append(exponentStr).append(" | ").append(decimalStr);
        return mantissaSpecial;
    }    
    public static String Binary(int beforeBinary) {
        String binary = "";
        while(beforeBinary > 0){
            int remainder = beforeBinary%2;
            beforeBinary = beforeBinary / 2;
            binary = remainder + binary;
        }
        return binary;
    }
   public static String mantissaBinary(double decimal) {
        String binary = "";
        int totalLength = 23;
        double newNum;
        if (decimal >= 1){
            newNum = decimal - 1;
        } else {
            newNum = decimal;
        }
        while(totalLength > 0){
            double answer = newNum * 2;
            if (answer > 1) {
                binary = binary + "1";
                newNum = answer -1;
            } else {
                binary = binary + "0";
                newNum = answer;
            }
            totalLength--;
        }
       return binary;
   }
}