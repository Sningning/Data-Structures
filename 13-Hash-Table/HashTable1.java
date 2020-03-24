import java.util.TreeMap;

/**
 * @Classname: HashTable
 * @Description:
 * @Author: Song Ningning
 * @Date: 2020-03-22 22:36
 */
public class HashTable1<K, V> {

    private TreeMap<K, V>[] hashtable;
    private int M;
    private int size;

    public HashTable1(int M) {

        this.M = M;
        this.size = 0;
        this.hashtable = new TreeMap[M];
        // 初始化数组每个位置的 TreeMap
        for (int i = 0; i < M; i++) {
            hashtable[i] = new TreeMap<>();
        }
    }

    public HashTable1() {
        this(97);
    }

    private int hash(K key) {
        // 取模 M，而数组长度也是 M，所以计算完的哈希值肯定小于数组长度，即一定能找到对应位置
        // M 一般取质数
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize() {
        return size;
    }

    public void add(K key, V value) {

        TreeMap<K, V> map = hashtable[hash(key)];
        if (map.containsKey(key)) {
            map.put(key, value);
        }
        else {
            map.put(key, value);
            size++;
        }
    }

    public V remove(K key) {

        TreeMap<K, V> map = hashtable[hash(key)];
        V ret = null;

        if (map.containsKey(key)) {
            ret = map.remove(key);
            size--;
        }

        return ret;
    }

    public void set(K key, V value) {

        TreeMap<K, V> map = hashtable[hash(key)];
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException(key + "doesn't exist!");
        }
        map.put(key, value);
    }

    public boolean contains(K key) {
        return hashtable[hash(key)].containsKey(key);
    }

    public V get(K key) {
        return hashtable[hash(key)].get(key);
    }



}
