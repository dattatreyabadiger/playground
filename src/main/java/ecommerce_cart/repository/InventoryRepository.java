package ecommerce_cart.repository;

import ecommerce_cart.model.InventoryItemDto;

import java.util.Map;

public interface InventoryRepository {

    InventoryItemDto getItem(int itemId, int requiredQuantity);

    boolean reduceQuantities(Map<Integer, Integer> cartItems);
}
