We're building the backend for a food delivery platform — think Zomato or Swiggy. 

A customer opens the app, sees restaurants that deliver to them, builds a cart, pays, and a delivery partner picks the order up and brings it to them.
I want you to design the backend for five features. 

Start with the low-level design — classes, interfaces, relationships, how the objects collaborate. 
The five features are:
1. Restaurant & menu catalog — a restaurant onboards, publishes a menu with items, customizations and availability.
2. Discovery — given a customer's delivery address, list restaurants that can serve them right now, with filters and sorting.
3. Cart & pricing — add/remove items with customizations, apply a coupon, produce the final bill breakdown.
4. Order placement & lifecycle — pay, place, restaurant accepts/rejects, order moves through states to deliver; handle cancellation and refund.
5. Delivery partner assignment & tracking — pick a partner for the order, handle accept/reject, expose live status and ETA to the customer.



Cart -> cart_id, items[cart_item], total_price, final_price
itemId, quantity, unit_price, total_price

Coupons (% or ) constraint
Coupon1

Order -> cart_id, total_price, coupon, final_price

Customer -> name, phoneNumber, address (geoHash)
Restaurant -> name, address (geohash), List<Items> menus
Item -> name, ItemTypes, Variants
ItemType -> Vegan, Vegetarian, Non-Veg

Potato -> [vegan, veg]
itemType = Any(Veg)

Select restaurant_id, rating
from itemDetails i
join restaurant r
On r.id = i.restId
Where i.id = panneerId
And r.geoHash in (hash1, hash2)


Restaurant
Id, name, geoHash

ItemDetails
Id, name, restaurantId

ItemTypeDetails
Id, ItemType, ItemId

ItemVariants
Id, ItemId, variant, price
1, pizzaId, medium, 100
2, Sweets, 250gm, 100
3, butterPanner, 100g, 1000


User -> item in 5km radius sort by rating




—---------------------------------------------------------------

DeliveryPartner (1 lakh) -> LocationService  -> partnerId, geoHash, status
Every 10sec, only if geoHash changed

Now +10sec
Now+t+10sec

1lakh per 10sec -> 10k per sec

10k * 100 = 10lakh reqs/sec


1000 orders per sec
