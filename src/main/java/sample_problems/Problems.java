package sample_problems;

public class Problems {

    // Example: reverse a string
    public String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    // Example: check if a number is palindrome
    public boolean isPalindrome(int x) {
        String s = String.valueOf(x);
        return s.equals(new StringBuilder(s).reverse().toString());
    }

    // Example: fibonacci nth number
    public int fibonacci(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int tmp = a + b;
            a = b;
            b = tmp;
        }
        return b;
    }
}
