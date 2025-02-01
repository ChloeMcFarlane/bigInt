package ps4;

/* 
 * BigInt.java
 *
 * A class for objects that represent non-negative integers of 
 * up to 20 digits.
 */
import java.util.*;

//ACTION ITEMS
    //1. FIX ADD METHOD 

public class BigInt  {
    // the maximum number of digits in a BigInt -- and thus the length
    // of the digits array
    private static final int MAX_SIZE = 20;
    
    // the array of digits for this BigInt object
    private int[] digits;
    
    // the number of significant digits in this BigInt object
    private int numSigDigits;
    private boolean overflow;

    /*
     * Default, no-argument constructor -- creates a BigInt that 
     * represents the number 0.
     */

    public BigInt() {
        digits = new int[MAX_SIZE];
        numSigDigits = 1;  // 0 has one sig. digit--the rightmost 0!
        //4. ARE WE MEANT TO BE LEAVING ONE EXTRA SIGNIFICANT DIGIT.
	    this.overflow = false;
    }

    //helper method
    private boolean isValidDigit(int n){
 
        boolean isValid = true;
            if(n<0 || n>9){
                isValid = false;
                throw new IllegalArgumentException("Digit is either illegal or element is not a digit.");

            }

        return isValid;

    }

    // first custom constructor
    // first custom constructor
    public BigInt(int[] arr){
        digits = new int [MAX_SIZE];

        if (arr.length <= 0 || arr.length > 20){
            throw new IllegalArgumentException("Please enter int between 0 and 99,999,999,999,999,999,999");
        }

        for (int i = 0; i<=arr.length-1; i++){
            if((arr[i]!=0)||(arr[i]==0 && numSigDigits != 0)){
                if(isValidDigit(arr[i]) == true){

                digits[(MAX_SIZE)-(arr.length-i)] = arr[i];
                numSigDigits +=1;

                }

            }
        }

        this.overflow = false;
    }


    // Second custom constructor
    public BigInt(int n) {
        digits = new int[MAX_SIZE];
        int iterMod = 10;

        if(n<0){
            throw new IllegalArgumentException("Int must be positive.");
        }

        do{
            // extracts the placement of the individual digits using mod.
            digits[MAX_SIZE-1-numSigDigits] = (n%iterMod)/(iterMod/10);
            // subtracts the value of n
            n-=(n%iterMod);
            // increases the ammount n will be modded by.
            iterMod *= 10;
            // increases numSigDigits
            numSigDigits +=1;


        }while(n!=0);

        this.overflow = false;

    }

    //compare the called BigInt object to the parameter other and return -1, 0, or 1. 
    public int compareTo(BigInt other){
        int result = 0; 

        if (this.numSigDigits > other.numSigDigits){
            result = 1;

        }else if(this.numSigDigits < other.numSigDigits){
            result = -1;

        }else{
            for(int i = 0; i<= this.digits.length-1; i++)
                if(this.digits[i]>other.digits[i]){
                    result = 1;
                    break;

                }else if(this.digits[i]<other.digits[i]){
                    result = -1;
                    break;
                }
        }

        return result;


    }


    // determines if int will carry over.
    public boolean carryOver(int a, int b){
        boolean doesCarry = false;
        if ((a + b)>9){
            doesCarry= true;
        }

        return doesCarry;

    }

    // checks for overflow in digits
    public boolean overflowCheck(int n){
        boolean isOver = false;
        if(n>=20){
            isOver = true;
        }

        return isOver;
    } 

    //adds two BigInts
    public BigInt add(BigInt other){
        BigInt sum = new BigInt();
        sum.numSigDigits = 0;
        int maxLength = 0;
        boolean carry = false; 

        if(other == null){
            throw new IllegalArgumentException();
        }
        
        BigInt maxVal = new BigInt();
            if (this.compareTo(other)==1){
                maxLength = this.numSigDigits;
                maxVal = this;

            }else{
                maxLength = other.numSigDigits;
                maxVal = other;
            }

            for(int i = 19; i>maxVal.digits.length-(maxVal.numSigDigits+1); i--){
                if(overflowCheck(sum.numSigDigits)==false){
                    // utilize given instance variables instead of using this method. 
                    if(carryOver(this.digits[i], other.digits[i])==true || (carryOver(sum.digits[i], maxVal.digits[i]) == true)){
                        sum.digits[i]+=(this.digits[i]+other.digits[i]);
                        sum.digits[i] %= 10;
                        

                        //does carry 
                        sum.digits[i-1]+=1;
                        carry = true; 
                        sum.numSigDigits+=1;
                        

                    }else{
                        sum.digits[i] += (this.digits[i]+other.digits[i]);
                        sum.numSigDigits+=1;   
                        carry = false;             
                        ;
        
                }
            }else{
                throw new ArithmeticException("Overflow! Int too bigg!");
            }
        }
        if(carry == true){
            sum.numSigDigits++;

        }
        return sum;
        

    }






    //should create and return a new BigInt object for the product of the integers 
    //represented by the called object and other. 
    public BigInt mul(BigInt other){
        if(other == null){
            throw new IllegalArgumentException();
        }
        int pow = 0;

        BigInt result = new BigInt();
        int carry = 0;

        for (int i = MAX_SIZE -1; i > MAX_SIZE-(1 + other.numSigDigits); i --){
            int mult = other.digits[i];
            int[] prod = new int[MAX_SIZE]; 

            for(int j = MAX_SIZE -1; j > MAX_SIZE-(numSigDigits +1); j--){
                prod[j-pow] = (carry + digits[j] * mult) % 10;
                carry = (carry + digits[j] * mult) / 10;
            }
            BigInt adder =new BigInt(prod);
            result = result.add(adder);
            pow ++;
        }
        if (carry > 0){
            throw new ArithmeticException();
        }
        return result;

    }




    public String toString(){
        String s ="";

        for(int i = MAX_SIZE-numSigDigits; i<=MAX_SIZE-1 ; i++){
            s+= digits[i];

        }

        return s;

    }



    //accessor method called getNumSigDigits() that returns the value of the numnumSigDigits
    public int getNumSigDigits(){
        return numSigDigits;
    }
    


    public static void main(String [] args) {
        System.out.println("Unit tests for the BigInt class.");
        System.out.println();

        /* 
         * You should uncomment and run each test--one at a time--
         * after you build the corresponding methods of the class.
         */
	
        System.out.println("Test 1: result should be 7");
        int[] a1 = { 1,2,3,4,5,6,7 };
        BigInt b1 = new BigInt(a1);
        System.out.println(b1.getNumSigDigits());
        System.out.println();
        
        System.out.println("Test 2: result should be 1234567");
        b1 = new BigInt(a1);
        System.out.println(b1);
        System.out.println();

        System.out.println("Test 2.5: result should be 1234567 and 7");
        BigInt val = new BigInt(1234567);
        System.out.println(val);
        System.out.println(val.getNumSigDigits());
        System.out.println();

        System.out.println("Test 2.75: result should be 199999998");
        BigInt val1 = new BigInt(99999999);
        BigInt val2 = new BigInt(99999999);
        BigInt sum = val1.add(val2);
        System.out.println(sum);
        System.out.println();
        
        System.out.println("Test 3: result should be 0");
        int[] a2 = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
        BigInt b2 = new BigInt(a2);
        System.out.println(b2.numSigDigits);
        System.out.println();
        
        System.out.println("Test 4: should throw an IllegalArgumentException");
        try {
            int[] a3 = { 0,0,0,0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
            BigInt b3 = new BigInt(a3);
            System.out.println(b3);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }


        System.out.println("Test 5: result should be 1234567");
        b1 = new BigInt(1234567);
        System.out.println(b1);
        System.out.println();

        System.out.println("Test 6: result should be 0");
        b2 = new BigInt(0);
        System.out.println(b2);
        System.out.println();

        System.out.println("Test 7: should throw an IllegalArgumentException");
        try {
            BigInt b3 = new BigInt(-4);
            System.out.println("Test failed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Test passed.");
        } catch (Exception e) {
            System.out.println("Test failed: threw wrong type of exception.");
        }
        System.out.println();

        System.out.println("Test 8: result should be 0");
        b1 = new BigInt(12375);
        b2 = new BigInt(12375);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 9: result should be -1");
        b2 = new BigInt(12378);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 10: result should be 1");
        System.out.println(b2.compareTo(b1));
        System.out.println();

        System.out.println("Test 11: result should be 0");
        b1 = new BigInt(0);
        b2 = new BigInt(0);
        System.out.println(b1.compareTo(b2));
        System.out.println();

        System.out.println("Test 12: result should be\n123456789123456789");
        int[] a4 = { 3,6,1,8,2,7,3,6,0,3,6,1,8,2,7,3,6 };
        int[] a5 = { 8,7,2,7,4,0,5,3,0,8,7,2,7,4,0,5,3 };
        BigInt b4 = new BigInt(a4);
        BigInt b5 = new BigInt(a5);
        BigInt sumTest = b4.add(b5);
        System.out.println(sumTest);
        System.out.println();

        System.out.println("Test 13: result should be\n123456789123456789");
        System.out.println(b5.add(b4));
        System.out.println();

        System.out.println("Test 14: result should be\n3141592653598");
        b1 = new BigInt(0);
        int[] a6 = { 3,1,4,1,5,9,2,6,5,3,5,9,8 };
        b2 = new BigInt(a6);
        System.out.println(b1.add(b2));
        System.out.println();

        System.out.println("Test 15: result should be\n10000000000000000000");
        int[] a19 = { 9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9 };    // 19 nines!
        b1 = new BigInt(a19);
        b2 = new BigInt(1);
        System.out.println(b1.add(b2));
        System.out.println();

         
    }
}
