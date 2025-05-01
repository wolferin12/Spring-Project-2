import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SimpleHashMap {
    private static final int initialSize = 1000; // Increased initial size
    private LinkedList<String>[] data;
    private int size;

    // Constructor: Initializes the hash table with empty linked lists
    public SimpleHashMap() {
        data = new LinkedList[initialSize];
        for (int i = 0; i < initialSize; i++) {
            data[i] = new LinkedList<>();
        }
        size = initialSize;
    }

    // Hash function: Computes the bucket index based on key length (excluding non-letter characters)
    private int dumbHash(String key) {
        return key.replaceAll("[^a-zA-Z]", "").length() % size;
    }

    // Inserts a key into the hash table
    public void put(String key) {
        int index = dumbHash(key);
        if (!data[index].contains(key)) {
            data[index].add(key);
        }
    }

    // Checks whether a key exists in the hash table
    public boolean contains(String key) {
        int index = dumbHash(key);
        return data[index].contains(key);
    }

    // Resizes the hash table to double its current size, rehashing all existing keys
    public void resize() {
        int newSize = size * 2;
        LinkedList<String>[] newData = new LinkedList[newSize];

        for (int i = 0; i < newSize; i++) {
            newData[i] = new LinkedList<>();
        }

        // Rehash existing keys
        for (LinkedList<String> bin : data) {
            for (String key : bin) {
                int newIndex = key.replaceAll("[^a-zA-Z]", "").length() % newSize;
                newData[newIndex].add(key);
            }
        }

        data = newData;
        size = newSize;
    }

    // Prints summary statistics of the hash table (avoids excessive output)
    public void printSummary() {
        int emptyBuckets = 0;
        int maxBucketSize = 0;
        int totalEntries = 0;

        for (LinkedList<String> bucket : data) {
            totalEntries += bucket.size();
            if (bucket.size() == 0) emptyBuckets++;
            if (bucket.size() > maxBucketSize) maxBucketSize = bucket.size();
        }

        System.out.println("\nHashMap Summary:");
        System.out.println("Total entries: " + totalEntries);
        System.out.println("Total buckets: " + size);
        System.out.println("Empty buckets: " + emptyBuckets);
        System.out.println("Max bucket size: " + maxBucketSize);
    }

    public static void main(String[] args) {
        SimpleHashMap map = new SimpleHashMap();
        Random random = new Random();

        // Generate a large dataset of random names
        int totalKeys = 1000;
        long startTime = System.nanoTime();

        for (int i = 1; i <= totalKeys; i++) {
            map.put("Name" + random.nextInt(1000));
        }

        long endTime = System.nanoTime();
        long duration = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
        System.out.println("Inserted " + totalKeys + " keys in " + duration + " milliseconds.");

        // Print hash table summary
        map.printSummary();

        // Resize the hash table and measure time
        startTime = System.nanoTime();
        map.resize();
        endTime = System.nanoTime();
        duration = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
        System.out.println("Resized hash table in " + duration + " milliseconds.");

        // Print updated summary after resizing
        map.printSummary();
    }
}
