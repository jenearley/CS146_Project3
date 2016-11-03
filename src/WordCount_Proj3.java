import java.io.IOException;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

public class WordCount_Proj3 {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out
                    .println("Usage: [-b | -a | -h] [-frequency | -num_unique] <filename>\n");
            System.out.println("-b - Use an Unbalanced BST");
            System.out.println("-a - Use an AVL Tree");
            System.out.println("-h - Use a Hashtable\n");
            System.out
                    .println("-frequency - Print all the word/frequency pairs, "
                            + "ordered by frequency, and then by the words in"
                            + "lexicographic order.\n");
            System.out
                    .println("-num_unique - Print the number of unique words in the document. "
                            + "This is the total number of distinct (different) words in the document. "
                            + "Words that appear more than once are only counted as a single word for "
                            + "this statistic");
            return;
        }

        try {

            switch (args[1]) {
                case "-frequency":
                    wordFrequencies(countWords(args[0], args[2]));
                    break;
                case "-num_unique":
                    uniqueWords(countWords(args[0], args[2]));
                    break;
                default:
                    System.out.println("Invalid second argument");
                    break;
            }
        } catch (IOException e) {
            System.out.println("ERROR: when parsing the file!!!");
            System.out.println(e.getMessage());
        }

    }

    public static DataCount<String>[] countWords(String dataStructure,
                                                 String filename) throws IOException {
        FileWordReader fileWordReader = new FileWordReader(filename);
        DataCounter<String> choice;
        String word;

        switch (dataStructure) {
            case "-b":
                choice = new BinarySearchTree<>();
                break;
            case "-a":
                choice = new AVLTree<>();
                break;
            case "-h":
                choice = new HashTable();
                break;
            default:
                choice = new BinarySearchTree<>();
                System.out
                        .println("Entered invalid data structure. BST by default.");
                break;
        }

        while ((word = fileWordReader.nextWord()) != null) {
            choice.incCount(word);
        }

        return choice.getCounts();
    }

    private static void uniqueWords(DataCount<String>[] data) {
        final long startTime = System.currentTimeMillis();

        System.out.println("Unique words: " + data.length);

        final long endTime = System.currentTimeMillis();

        final long totalTime = endTime - startTime;
        System.out.println("Total amount of time taken: " + totalTime);
    }

    private static void wordFrequencies(DataCount<String>[] data) {
        final long startTime = System.currentTimeMillis();

        sortByDescendingCount(data);
        System.out.println("Sorted by Frequency: ");
        printWordCount(data);

        sortAlphabetically(data);

        System.out.println("\nSorted Lexicographically:");
        printWordCount(data);

        final long endTime = System.currentTimeMillis();

        final long totalTime = endTime - startTime;
        System.out.println("Total amount of time taken: " + totalTime);
    }

    private static <E> void sortByDescendingCount(
            DataCount<E>[] counts) {
        for (int i = 1; i < counts.length; i++) {
            DataCount<E> x = counts[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (counts[j].count >= x.count) {
                    break;
                }
                counts[j + 1] = counts[j];
            }
            counts[j + 1] = x;
        }
    }

    private static <E> void sortAlphabetically(
            DataCount<E>[] counts) {
        Collection<String> alpha =
                new TreeSet<String>(Collator.getInstance());
        for(DataCount<E> dataCount:counts)
            alpha.add((String) dataCount.data);
    }

    private static void printWordCount(DataCount<String>[] data) {
        for (DataCount<String> dataCount : data) {
            System.out.format("%d %s\n", dataCount.count, dataCount.data);
        }
    }


}
