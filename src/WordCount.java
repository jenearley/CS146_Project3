import java.io.IOException;

public class WordCount {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: [-b | -a | -h] [-is | -qs | -ms] <filename>\n");
            System.out.println("-b - Use an Unbalanced BST");
            System.out.println("-a - Use an AVL Tree");
            System.out.println("-h - Use a Hashtable");
            System.out.println("-is - Use Insertion Sort");
            System.out.println("-qs - Use Quick Sort");
            System.out.println("-ms - Use Merge Sort\n");
        }
        countWords(args[0], args[1], args[2]);
    }

    public static void countWords(String dataStructure, String sortMethod, String file) throws IOException {
        FileWordReader reader = new FileWordReader(file);
        DataCounter<String> counter;

        switch (dataStructure) {
            case "-b":
                counter = new BinarySearchTree<>();
                break;
            case "-a":
                counter = new AVLTree<>();
                break;
            case "-h":
                counter = new HashTable();
                break;
            default:
                counter = new BinarySearchTree<>();
                System.out
                        .println("Entered invalid data structure. BST by default.");
                break;
        }

        try {
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        } catch (IOException e) {
            System.err.println("Error processing " + file + e);
            System.exit(1);
        }

        DataCount<String>[] counts = counter.getCounts();
        if(sortMethod.equals("-is")){
            insertionSort(counts);
        } else if(sortMethod.equals("-qs")){
            quickSort(counts, 0, counts.length - 1);
        } else if(sortMethod.equals("-ms")){
            mergeSort(counts, 0, counts.length - 1);
        } else {
            System.out.println("Second argument invalid");
        }

        for (DataCount<String> c : counts)
            System.out.println(c.count + " \t" + c.data);
    }


    private static <E extends Comparable<? super E>> void insertionSort(DataCount<E>[] counts) {
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

    /*
    used outside source in order to implement mergesort
    reference: http://stackoverflow.com/questions/12656587/merge-sort-getting-stack-overflow-error
     */
    private static <E extends Comparable<? super E>> void mergeSort(DataCount<E>[] counts, int left, int right){
        if(right - left <= 1){ return;} //stops when arrays are all of size 1
        int mid = (left + right) / 2;
        mergeSort(counts, left, mid);
        mergeSort(counts, mid + 1, right);
        merge(counts, left, mid, right);
    }


    private static void merge(DataCount[] counts, int left, int mid, int right){
        int i; //index for first half of array
        int j; //index for second half of array
        DataCount[] temp = new DataCount[counts.length];

        for(i = mid + 1; i > left; i--){
            temp[i-1] = counts[i-1];
        }
        for(j = mid; j < right; j++){
            temp[right + mid - j] = counts[j+1];
        }
        //goes through original array
        for(int k = left; k <= right; k++){
            if(temp[j].count < temp[i].count){
                counts[k] = temp[j--];
            } else {
                counts[k] = temp[i++];
            }
        }
    }
    /*
    used outside source in order to implement quicksort
    reference: http://www.algolist.net/Algorithms/Sorting/Quicksort
     */
    private static<E> void quickSort(DataCount<E>[] counts, int left, int right){
        int index = partition(counts, left, right);
        if(left < index - 1){
            quickSort(counts, left, index - 1);
        }
        if(index < right){
            quickSort(counts, index, right);
        }
    }

    private static int partition(DataCount[] counts, int left, int right){
        int l = left;
        int r = right;
        DataCount pivot = counts[(left + right) / 2];
        DataCount temp;

        while(l <= r){
            while(counts[l].count < pivot.count){
                l++;
            }
            while(counts[r].count > pivot.count){
                r--;
            }
            if(l <= r){
                temp = counts[l];
                counts[l] = counts[r];
                counts[r] = temp;
                l++;
                r--;
            }
        }
        return r;
    }


}
