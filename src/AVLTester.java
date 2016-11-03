/**
 * Created by jen0e on 3/9/2016.
 */
public class AVLTester {
    public static void main(String[] args) {
        AVLTree<String> tree = new AVLTree<>();
        tree.incCount("the");
        tree.incCount("especially");
        tree.incCount("the");
        tree.incCount("the");
        tree.incCount("a");

        System.out.println(tree.getSize());

        DataCount<String>[] counts = tree.getCounts();

        for(int i = 0; i < counts.length; i++){
            System.out.println(counts[i].data + ": " + counts[i].count);
        }

    }
}
