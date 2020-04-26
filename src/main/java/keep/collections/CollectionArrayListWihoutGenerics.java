package keep.collections;

import java.util.ArrayList;

class ItemX {}
class ItemY {}

public class CollectionArrayListWihoutGenerics {
    public static void main(String[] args) {
        ArrayList items = new ArrayList();
        // Add 3 ItemX
        for(int i=0; i<3; i++) {
            items.add(new ItemX());
        }
        // Add 3 ItemY is OK
        for(int i=0; i<3; i++) {
            items.add(new ItemY());
        }

        // Get all items from the ArrayList
        for(int i=0; i<items.size(); i++) {
            Object item = items.get(i);
            System.out.println(items.get(i));
        }
    }
}
