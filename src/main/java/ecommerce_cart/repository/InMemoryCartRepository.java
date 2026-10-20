package ecommerce_cart.repository;

import ecommerce_cart.model.CartDto;
import ecommerce_cart.model.CartStatusEnum;
import ecommerce_cart.model.ItemDto;

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
    public List<ItemDto> getItems(int cartId) {
        return new ArrayList<>(getCart(cartId).getItems()
                                              .values());
    }

    @Override
    public void addItemToCart(int cartId, ItemDto newItem) {
        CartDto cart = getCart(cartId);
        ItemDto existingItems = cart.getItems().get(newItem.getId());
        if (existingItems == null)
            cart.getItems().put(newItem.getId(), newItem);
        else
            existingItems.setQuantity(existingItems.getQuantity() + newItem.getQuantity());
    }

    @Override
    public void removeItemFromCart(int cartId, ItemDto removeItem) {
        CartDto cart = getCart(cartId);
        ItemDto existingItems = cart.getItems().get(removeItem.getId());
        if (existingItems == null)
            return;

        existingItems.setQuantity(existingItems.getQuantity() - removeItem.getQuantity());
    }
}
