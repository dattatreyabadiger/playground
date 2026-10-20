HLD: Pantry service

Function Requirements:
1. Admin should be able to add products(category, quantity) per office location per floor
2. Employees in the same floor should be notified of product additions (via app only)
3. Employee can view products per category in the floor
4. Categories : DAIRY, BEVERAGES, FRUITS, MISC

Out of scope
1. Email notifications


Non-Functional requirements:
1. 100 office location and multiple floors (10)
2. 10k employees per office
3. Product updates: 2-3 time week


API/Interfaces
1. Admin POST /api/v1/products {name, category, quantity, floor_id, office_location}
2. GET /api/v1/products/{floor_id} (auth - validate if employee has access) {product_id, name, category}
3. no need - GET /api/v1/products/updates -> (cause: push updates via applePush or Firebase)
4. Event (product updates - {productid, name, floor_id, office_location})


Data Entities
1. products (product_id, name, category)
2. inventory (product_id, floor_id, office_location, quantity)
3. employees (emp_id, name, floor_id, office_location)


------------------------------------------------------------------------------------------------


Missing things
FR:
1. Did not consider how products quantity will decrease

NFR:
1. thundering herd effect
2. Notification delivery - at-least-once delivery with idempotent consumers (dedup by event_id), so Kafka/CDC retries don't cause duplicate push notifications to employees

API:
1. keep Admin POST /api/v1/products {name, category, product_id} (no deletion of product)
2. add new PUT /api/v1/inventory/{office_id}/{floor_id}/{product_id}

Diagram:
1. Solve Dual write problem - CDC is better
2. Add redis layer, since there will be thundering herd effect upon notifications
3. Employees will be connected via app