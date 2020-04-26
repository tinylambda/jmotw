package keep.collections;

import java.util.ArrayList;

class Item {}

public class CollectionArrayList {
    public static void main(String[] args) {
        // Initialize a ArrayList to hold Item
        ArrayList<Item> items = new ArrayList<Item>();
        // Put some items to this ArrayList
        for(int i=0; i<3; i++) {
            items.add(new Item());
        }

        // Get items from ArrayList
        for(int i=0; i<items.size(); i++) {
            System.out.println(items.get(i));
        }
    }
}
