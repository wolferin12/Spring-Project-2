import java.util.LinkedList;

public class SimpleHashMap {
    private static final int initialSize = 10; // Initial number of buckets in the hash table
    private LinkedList<String>[] data; // Array of linked lists to store values
    private int size; // Current size of the hash table

    // Constructor: Initializes the hash table with empty linked lists
    public SimpleHashMap() {
        data = new LinkedList[initialSize];
        for (int i = 0; i < initialSize; i++) {
            data[i] = new LinkedList<>(); // Each bucket is a linked list
        }
        size = initialSize;
    }

    // Hash function: Computes the bucket index based on the length of the key (excluding non-letter characters)
    private int dumbHash(String key) {
        return key.replaceAll("[^a-zA-Z]", "").length() % size;
    }

    // Inserts a key into the hash table
    public void put(String key) {
        int index = dumbHash(key); // Compute hash index
        if (!data[index].contains(key)) { // Avoid duplicate entries
            data[index].add(key);
        }
    }

    // Checks whether a key exists in the hash table
    public boolean contains(String key) {
        int index = dumbHash(key);
        return data[index].contains(key); // Check if key exists in the computed bucket
    }

    // Resizes the hash table to double its current size, rehashing all existing keys
    public void resize() {
        int newSize = size * 2; // Double the size
        LinkedList<String>[] newData = new LinkedList[newSize]; // Create new array of linked lists

        // Initialize new linked lists
        for (int i = 0; i < newSize; i++) {
            newData[i] = new LinkedList<>();
        }

        // Rehash all existing keys into the new table
        for (LinkedList<String> bin : data) {
            for (String key : bin) {
                int newIndex = key.replaceAll("[^a-zA-Z]", "").length() % newSize;
                newData[newIndex].add(key);
            }
        }

        // Update data structure with resized table
        data = newData;
        size = newSize;
    }

    // Prints the contents of the hash table
    public void printMap() {
        for (int i = 0; i < size; i++) {
            System.out.println("Bucket " + i + ": " + data[i]);
        }
    }

    public static void main(String[] args) {
        // Create a SimpleHashMap instance and add some keys
        SimpleHashMap map = new SimpleHashMap();
        map.put("Erin");
        map.put("Jack");
        map.put("Bill");
        map.put("Byron");

        // Print hash table contents
        map.printMap();

        // Check for the existence of keys
        System.out.println("Contains 'Jack': " + map.contains("Jack"));
        System.out.println("Contains 'John': " + map.contains("John")); // 'John' was not added

        // Resize the hash table and print updated contents
        map.resize();
        System.out.println("\nAfter resizing:");
        map.printMap();
    }
}
