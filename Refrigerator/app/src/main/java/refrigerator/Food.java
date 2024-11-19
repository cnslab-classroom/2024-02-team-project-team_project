package refrigerator;

import java.util.Objects;

public class Food implements Comparable<Food> {
    private String name;
    private int expiry;
    private int amount;

    public Food(String name, int expiry) {
        this.name = name;
        this.expiry = expiry;
    }

    public synchronized void setExpiry(int newExpiry) {
        this.expiry = newExpiry;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized int getExpiry() {
        return expiry;
    }

    public synchronized int getAmount()
    {
        return amount;
    }

    public synchronized void setAmount(int n)
    {
        this.amount += n;
    }   

    @Override
    public int compareTo(Food other) {
        return Integer.compare(this.expiry, other.expiry);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Food food = (Food) obj;
        return expiry == food.expiry && Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, expiry);
    }

    @Override
    public String toString() {
        return "Name: " + name + " Expiry: " + expiry;
    }

}
