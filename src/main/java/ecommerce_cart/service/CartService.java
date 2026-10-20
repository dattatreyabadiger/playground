package ecommerce_cart.service;

import ecommerce_cart.model.CartDto;
import ecommerce_cart.model.CartItemDto;
import ecommerce_cart.model.CartStatusEnum;
import ecommerce_cart.model.InventoryItemDto;
import ecommerce_cart.repository.CartRepository;
import ecommerce_cart.repository.InventoryRepository;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public int createCart() {
        return cartRepository.createCart();
    }

    @Override
    public List<CartItemDto> getCartItems(int cartId) {
        return cartRepository.getItems(cartId);
    }

    @Override
    public boolean addItem(int cartId, int itemId) {
        InventoryItemDto item = inventoryRepository.getItem(itemId, 1);
        if (item == null)
            return false;

        if (isCartUnavailable(cartId))
            return false;

        CartItemDto cartItem = createNewCartItem(item);
        cartRepository.addItemToCart(cartId, cartItem);
        return true;
    }

    @Override
    public boolean updateItemQuantity(int cartId, int itemId, int quantity) {
        InventoryItemDto item = inventoryRepository.getItem(itemId, quantity);
        if (item == null || quantity == 0)
            return false;

        if (isCartUnavailable(cartId))
            return false;

        CartItemDto cartItem = createNewCartItem(item);
        cartRepository.updateItemQuantity(cartId, cartItem, quantity);
        return true;
    }

    @Override
    public boolean removeItem(int cartId, int itemId) {
        if (isCartUnavailable(cartId))
            return false;

        cartRepository.removeItemFromCart(cartId, itemId);
        return true;
    }

    @Override
    public boolean checkoutCart(int cartId) {
        Map<Integer, Integer> cartItems = getCartItems(cartId)
                .stream()
                .collect(Collectors.toMap(CartItemDto::getId,
                                          CartItemDto::getQuantity));

        if (!inventoryRepository.reduceQuantities(cartItems))
            return false;

        cartRepository.updateCartStatus(cartId, CartStatusEnum.CHECKED_OUT);
        return true;
    }

    @Override
    public BigDecimal getTotalPrice(int cartId) {
        return cartRepository.getCart(cartId)
                             .getTotalCartPrice();
    }

    private boolean isCartUnavailable(int cartId) {
        CartDto cart = cartRepository.getCart(cartId);
        if (cart == null)
            return false;

        return cart.getStatus() != CartStatusEnum.IN_PROGRESS;
    }

    private CartItemDto createNewCartItem(InventoryItemDto item) {
        return new CartItemDto(item.getId(),
                               item.getName(),
                               1,
                               item.getPrice());
    }
}
