1. merchant ids are received for transactions
   a. tx1: m1, tx2: m1, tx3: m2, tx4: m3
   b. res: m1, m2, m3


n - number of transactions
m - number of distinct merchants
TreeSet(MerchantSales) and HashMap(MerchantId to MerchantSales)
-> O(log m) for each insertion
-> O(m) for fetching top merchants


But, give an approach so that offers O(1) for each insertion and O(m * log m) for fetch
HashMap(MerchantId to MerchantSales)
-> each insertion is O(1)
-> sorting them later for top merchants is O(m * log m)
