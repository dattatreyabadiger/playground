import org.junit.jupiter.api.Test;
import sample_problems.Problems;

import static org.junit.jupiter.api.Assertions.*;

class ProblemsTest {

    private final Problems p = new Problems();

    @Test
    void testReverseString() {
        assertEquals("olleh", p.reverseString("hello"));
        assertEquals("", p.reverseString(""));
        assertEquals("a", p.reverseString("a"));
    }

    @Test
    void testIsPalindrome() {
        assertTrue(p.isPalindrome(121));
        assertFalse(p.isPalindrome(123));
        assertTrue(p.isPalindrome(0));
    }

    @Test
    void testFibonacci() {
        assertEquals(0, p.fibonacci(0));
        assertEquals(1, p.fibonacci(1));
        assertEquals(1, p.fibonacci(2));
        assertEquals(8, p.fibonacci(6));
        assertEquals(55, p.fibonacci(10));
    }

}
