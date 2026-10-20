package ecommerce_cart.repository;

import ecommerce_cart.model.InventoryItemDto;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
public class InMemoryInventoryRepository implements InventoryRepository {

    private final Map<Integer, InventoryItemDto> inventory;

    public InMemoryInventoryRepository() {
        this.inventory = Map.of(1, new InventoryItemDto(1, "item1", 1000, BigDecimal.valueOf(100)),
                                2, new InventoryItemDto(2, "item2", 1500, BigDecimal.valueOf(150)));
    }

    @Override
    public InventoryItemDto getItem(int itemId, int requiredQuantity) {
        InventoryItemDto item = inventory.get(itemId);
        if (item == null || item.getQuantity() < requiredQuantity) {
            log.warn("Item {} not available in inventory for required quantity", itemId);
            return null;
        }
        return item;
    }

    public boolean reduceQuantities(Map<Integer, Integer> cartItems) {
        // verify if stock exists
        for (var cartEntry : cartItems.entrySet()) {
            InventoryItemDto inventoryItem = inventory.get(cartEntry.getKey());
            if (inventoryItem == null || inventoryItem.getQuantity() < cartEntry.getValue())
                return false;
        }

        // deduct stock
        for (var cartEntry : cartItems.entrySet()) {
            InventoryItemDto inventoryItem = inventory.get(cartEntry.getKey());
            inventoryItem.setQuantity(inventoryItem.getQuantity() - cartEntry.getValue());
        }
        return true;
    }

}

