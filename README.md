# 1. The project file layout
After unzip the tar file, you should see the following directory and files. 

```text
WordAutoCompletion/
├── LICENSE
├── pom.xml
├── README.md
├── src
│ ├── main
│ │ └── java
│ │     └── org
│ │         └── pengfei
│ └── test
│     └── java
│         └── org
│             └── pengfei
└── docs
```

- All the code source are located at **src/main/java/org/pengfei**
- All the unit test are located at **src/test/java/org/pengfei**
- The java docs in HTML format are located at **docs/**. Open the docs/index.html in a browser to check the docs.


# 2. Import the project and run examples

You can import the project as a maven project by using the pom.xml file. To run the example, please goto 
**src/main/java/org/pengfei/completions/main**, then run the main method. You should see the example that you described
in your challenge requirements

# 3. Solution discussion

## 3.1 Algorithmic complexity and optimization

### 3.1.1 Bad solution
The simplest solution of this challenge is to convert each words to a list of character. Then check the given prefix
is a subset of the list. For example, suppose the word is river, and prefix is ri, we will have 
"river"=>['r','i','v','e','r'], "ri"=>['r','i']. 
The downside of this solution is the complexity in time. Suppose we have **N words**, and the average **word length is K**
**The O(N) in time is N\*K**

### 3.1.2 Good solution
To resolve this problem with a linear complexity in time, we need to use a data structure called **Trie**. Each node
can store a character, and has a list of child which is the next character of a word. For example, if we have two
words ["rice","river"], You will have the following trie structure.
```text
     r
     |
     i
    / \
   c   v
   |   |
   e   e
       |
       r
```

With the trie data structure, the method that checks if a given string is a prefix of words in the keyword list 
is **linear** in time complexity. The worst case scenario, we have to goto the deepest node of the trie 
(the max length of the word in the list).

In my implementation **getNFirstWordsStartWith**, I check first if the prefix exists in the words list. If it does not
exist, return null directly. If it does exist, I will get the node that represent the last character of the prefix and 
find all children that is a word. So my implementation is linear in time complexity.

# 4. Additional Questions

## 4.1 Handle larger keywords list
**Question:** What would you change if the list of keywords was much larger (300 Gb) ? Please explain and describe the 
concepts that would allow to handle this if you decide to use specific tools (frameworks, database)

## 4.1.1 Solution without any framework
### Summery
- Partition the larger keyword file into small keyword files 
- Use a thread pool to treat each partitioned file with a dedicated thread.

### Details
We can use file system to simulate the trie structure that can partition the 
300 GB file into small files. For example, suppose we want to have 3 level partition, we will have the following directories
in the file system:
```text
                           keywords
                      / /     |     \  \
                     a b ...  h ...  y  z
                 /   |   \
               a ..  h    z
                     
```
If we have a keyword "river" it will be located at **keywords/r/i/words.txt**. In another word, the file **words.txt**
under **keywords/r/i** only contains the keywords that starts with "ri". If the keywords in the origin 300GB file are
evenly distributed, after the partition, we will have 676 keyword files, and each keywords file will become 300(GB)/(26*26)=445 (MB). 

Create a Thread pool that contains 676 thread. Each thread uses the trie structure to store the partitioned keywords file  

## 4.1.2 Solution with framework
### Summery
- Use spark to transform the keywords file into a dataframe with columns={1st_index,2nd_index,rest_of_word}
- Store the dataframe as parquet format and partitioned the parquet file by using column (1st_index,2nd_index)
- Search prefix is optimized by leveraging **spark predicate pushdown**

### Details
Step 1: Transform words list into dataframe. For example the word "river,rice" will return the following dataframe

|1st_index|2nd_index|rest_of_word|
|-----|-----|------------------|
|r|i|ver|
|r|i|ce|

Step 2: Store the dataframe as parquet format and partitioned the parquet file by using column (1st_index,2nd_index)
```scala
data.orderBy($"1st_index",$"2nd_index",$"rest_of_word").write.partitionBy("1st_index","2nd_index").parquet("/tmp")
```
The parquet file will be partitioned, you will have the following result
```text
tmp
├── 1st_index=a
│ ├── 2nd_index=a
│ │ └── a03rfssfsd0r454fdd.parquet

```

Step 3: Search 
For example, if we search ri, the spark executor will only read the parquet file under **/tmp/1st_index=r/2nd_index=i**
As we order the dataframe. We can also leverage the pushdown of a single parquet file. Because parquet file divide data
into row group and the footer of the parquet file contains min max of each row group. So we have double predicate 
push-down optimization

## 4.2 Search words that contains substring
**Question:** What would you change if the requirements were to match any portion of the keywords 
(for example, given the string “pro”, the program could suggest the keyword “reprobe”) 

**Answer:** 
- Step1: Search the first letter "P" in the Trie structure, and record all the result node and their path in **root_map**. 
  If **root_map** is empty, return null

- Step2: Use each node in the **root_map** as **starting node** to search if prefix "pro" exists in the Trie, if yes, use
the method **getNFirstChildrenPathByAlphaOrder** to find all possible completion. If no, return null

- Step3: Concatenate the word completion and the path of each node and put it in a list. Return the list 



