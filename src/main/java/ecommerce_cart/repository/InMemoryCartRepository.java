package ecommerce_cart.repository;

import ecommerce_cart.model.CartDto;
import ecommerce_cart.model.CartItemDto;
import ecommerce_cart.model.CartStatusEnum;
import ecommerce_cart.model.InventoryItemDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryCartRepository implements CartRepository {

    private final Map<Integer, CartDto> cartMap;
    private final AtomicInteger cartIDGenerator;

    public InMemoryCartRepository() {
        this.cartMap = new HashMap<>();
        this.cartIDGenerator = new AtomicInteger();
    }

    @Override
    public CartDto getCart(int cartId) {
        return cartMap.get(cartId);
    }

    @Override
    public int createCart() {
        int cartId = cartIDGenerator.incrementAndGet();
        CartDto cartDto = new CartDto(cartId);
        cartMap.put(cartId, cartDto);
        return cartId;
    }

    @Override
    public void updateCartStatus(int cartId, CartStatusEnum newCartStatus) {
        CartDto cart = getCart(cartId);
        cart.setStatus(newCartStatus);
    }

    @Override
    public List<CartItemDto> getItems(int cartId) {
        return new ArrayList<>(getCart(cartId).getItems()
                                              .values());
    }

    @Override
    public void addItemToCart(int cartId, CartItemDto newItem) {
        CartDto cart = getCart(cartId);
        CartItemDto existingItems = cart.getItems().get(newItem.getId());
        if (existingItems == null) {
            cart.getItems().put(newItem.getId(), newItem);
            return;
        }

        // increment the count by 1 for existing item in cart
        existingItems.setQuantity(existingItems.getQuantity() + 1);
    }

    @Override
    public void removeItemFromCart(int cartId, int itemId) {
        CartDto cart = getCart(cartId);
        CartItemDto existingItem = cart.getItems().get(itemId);
        if (existingItem == null)
            return;

        cart.getItems()
            .remove(existingItem.getId());
    }

    @Override
    public void updateItemQuantity(int cartId, CartItemDto item, int quantity) {
        CartDto cart = getCart(cartId);
        CartItemDto existingItem = cart.getItems().get(item.getId());
        if (existingItem == null) {
            cart.getItems().put(item.getId(), item);
            return;
        }

        existingItem.setQuantity(quantity);
    }
}
