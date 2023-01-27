package map;

/**
 * @Classname: Map
 * @Description: 映射接口
 * @author: Sningning
 * @date: 2020-03-11 12:57
 */
public interface Map<K, V> {

    void add(K key, V value);
    V remove(K key);
    boolean contains(K key);
    V get(K key);
    void set(K key, V newValue);
    int getSize();
    boolean isEmpty();
}
