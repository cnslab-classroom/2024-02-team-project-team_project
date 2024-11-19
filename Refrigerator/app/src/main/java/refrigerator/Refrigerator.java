package refrigerator;
import java.util.*;

public class Refrigerator {
    private TreeSet<Food> expirySet;
    private int length;

    public Refrigerator() {
        expirySet = new TreeSet<>();
        length = 0;
    }

    public synchronized void push(Food f) {
        for(Food f_in_r : expirySet)
        {
            if (f_in_r.equals(f))
            {
                f_in_r.setAmount(f_in_r.getAmount() + 1);
                return;
            }
        }
        expirySet.add(f);
        length++;
    }

    public synchronized Food pop(int index) {
        Iterator<Food> it = expirySet.iterator();
        Food target = null;
        for (int i = 0; i <= index; i++) {
            target = it.next();
        }
        if (target != null) {
            expirySet.remove(target);
            length--;
        }
        return target;
    }

    public synchronized Food pop(String food_name, int n) {
        Food target = null;
        for (Food f : expirySet) {
            if (f.getName().equals(food_name)) {
                target = f;
                break;
            }
        }
        if (target == null) return null;
        
        if (target.getAmount() <= n)
        {
            expirySet.remove(target);
            length--;
        }
        else
        {
            target.setAmount(target.getAmount() - n);
        }

        return target;
    }

    public synchronized void minusExpiry() {
        for (Food f : expirySet) {
            f.setExpiry(f.getExpiry() - 1);
        }
    }

    public synchronized void overExpiry(List<Integer> v) {
        v.clear();
        int index = 0;
        for (Food f : expirySet) {
            if (f.getExpiry() <= 0) {
                v.add(index);
            }
            index++;
        }
    }

    public synchronized Food get(int index) {
        Iterator<Food> it = expirySet.iterator();
        for (int i = 0; i < index; i++) {
            it.next();

        }
        return it.next();
    }

    public synchronized int getLength() {
        return length;
    }

    public synchronized void print() {
        System.out.println("\nRefrigerator status");
        System.out.println("Has " + length + " foods");
        System.out.println("Food list");
        for (Food f : expirySet) {
            System.out.println(f);
        }
        System.out.println();
    }
}