package com.calc.utils;

import org.openqa.selenium.InvalidArgumentException;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Class with static auxiliary methods.
 * Static methods are not recommended, but for helpers it's acceptable.
 */
public class OperationsHelper {

    public static Object[] operations = new Object[] {"addition", "subtraction", "multiplication", "division"};

    /**
     * Method performs math operations for given operand.
     * @param operation addition, subtraction, multiplication, division
     * @param value1    first value
     * @param value2    second value
     * @return  result
     * @throws InvalidArgumentException in case of invalid operation
     */
    public static int performOperation(String operation, int value1, int value2) throws InvalidArgumentException {
        switch (operation) {
            case "addition":
                return value1 + value2;
            case "subtraction":
                return value1 - value2;
            case "multiplication":
                return value1 * value2;
            case "division":
                return value1 / value2;
            default:
                throw new InvalidArgumentException(String.format("Invalid operation: %s", operation));
        }
    }

    /**
     * Choose the valid operation code for GET and POST requests.
     * For GET requests isGet flag should be True.
     * Actually the methods with flags are not recommended, but in this particular case
     * it's acceptable and makes the code shorter.
     * If the logic is complex, this approach should be avoided.
     *
     * @param operation the operand from the test data
     * @param isGet flag for switching from GET to POST operation codes
     * @return  the operation code
     * @throws InvalidArgumentException if the operation is invalid
     */
    public static String chooseRequestOperand(String operation, Boolean isGet) throws InvalidArgumentException {
        switch (operation.toLowerCase().trim()) {
            case "addition":
                return "add";
            case "division":
                return isGet ? "divide" : "div";
            case "multiplication":
                return isGet ? "multiply": "mul";
            case "subtraction":
                return isGet ? "subtract" : "sub";
            default:
                throw new InvalidArgumentException(String.format("Invalid operation: '%s'", operation));
        }
    }

    public static int randomValue(){
        // Max and min values are divided to 2 to avoid the Integer Overflow
        int min = Integer.MIN_VALUE / 2;
        int max = Integer.MAX_VALUE / 2;
        return current().nextInt(min, max);
    }
}
