import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Correlator {
    public static void main(String[] args) {

        DataCount<String>[] text1;
        DataCount<String>[] text2;

        if (args.length != 3) {
            System.out
                    .println("Usage: [-b | -a | -h] <filename 1> <filename 2>\n");
            System.out
                    .println("-b Use an unbalanced BST to compare text files");
            System.out.println("-a Use an AVL Tree to compare text files");
            System.out.println("-h Use a Hashtable to compare text files");
            return;
        }

        try {
            text1 = WordCount_Proj3.countWords(args[0], args[1]);
            text2 = WordCount_Proj3.countWords(args[0], args[2]);
        } catch (IOException e) {
            System.out.println("ERROR: when parsing the files!!!");
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("\nCompare Function: "
                + compareFunction(text1, text2));

    }

    private static int totalWordCount(DataCount<String>[] data) {
        int totalWords = 0;
        for (DataCount<String> dataCount : data) {
            totalWords += dataCount.count;
        }
        return totalWords;
    }

    private static Map<String, Double> frequency(DataCount<String>[] data) {
        double totalCount = (double) totalWordCount(data);
        Map<String, Double> frequencies = new HashMap<>();

        final long startTime = System.currentTimeMillis();

        for (DataCount<String> dataCount : data) {
            double frequency = (double) dataCount.count / totalCount;
            if (frequency < 0.01 && frequency > 0.0001) {
                System.out.format("%s - %f\n", dataCount.data, frequency);
                frequencies.put(dataCount.data, frequency);
            }
        }
        final long endTime = System.currentTimeMillis();

        final long totalTime = endTime - startTime;
        System.out.println("Total amount of time taken: " + totalTime);

        return frequencies;
    }

    private static double compareFunction(DataCount<String>[] text1,
                                          DataCount<String>[] text2) {
        double sum = 0;

        System.out.println("Frequencies for first file:");
        Map<String, Double> frequencies1 = frequency(text1);

        System.out.println("\nFrequencies for second file:");
        Map<String, Double> frequencies2 = frequency(text2);

        for (String key : frequencies1.keySet()) {
            if (frequencies2.containsKey(key)) {
                double difference = Math.abs(frequencies1.get(key)
                        - frequencies2.get(key));
                sum += difference * difference;
            }
        }
        return sum;
    }
}
