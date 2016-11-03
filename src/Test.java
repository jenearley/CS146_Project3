/**
 * Created by jen0e on 3/10/2016.
 */
public class Test {
    public static void main(String[] args){
        HashTable table = new HashTable();
        table.incCount("the");
        table.incCount("hello");
        DataCount<String>[] c = table.getCounts();
        System.out.println(table.getSize());
        for(int i = 0; i < c.length; i++){
            System.out.println(c[i].data + ": " + c[i].count);
        }
    }
}
