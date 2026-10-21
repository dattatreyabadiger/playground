package lru_cache_test;

import lru_cache.LruCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LruCacheTest {

    @Test
    public void LruCacheTest() {
        LruCache cache = new LruCache(2);
        assertNull(cache.get(1));

        cache.put(1, "std");
        assertEquals("std", cache.get(1));

        assertTrue(cache.remove(1));
        assertFalse(cache.remove(1));

        cache.put(1, "std");
        cache.put(1, "stds");
        assertEquals("stds", cache.get(1));

        cache.put(2, "stdss");
        cache.put(3, "stdsss");
        assertNull(cache.get(1));
    }
}
