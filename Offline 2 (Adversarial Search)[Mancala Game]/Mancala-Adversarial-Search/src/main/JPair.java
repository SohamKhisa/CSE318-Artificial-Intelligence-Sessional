package main;

public class JPair<K, V> {
    private K element0;
    private V element1;

    public static <K, V> JPair<K, V> createPair(K element0, V element1) {
        return new JPair<K, V>(element0, element1);
    }

    public JPair(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }

    public K getElement0() {
        return element0;
    }

    public V getElement1() {
        return element1;
    }

}