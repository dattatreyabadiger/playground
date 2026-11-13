Implement Mobile Application Search which suggests you the applications in your mobile based on the input text


1. build search function based on prefix
   2. f, fa -> facebook ; not by book
   3. ic, ba -> icici bank
   4. ba -> icici bank, hdfc bank

Approach 1:
TrieNode (keywords)
Map<Keyword, Application Name>

Approach 2:
Add every prefix in Map -> good for 1hr interview session;
1. Add Application name
2. Search by prefix