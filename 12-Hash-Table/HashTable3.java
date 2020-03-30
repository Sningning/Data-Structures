import java.util.TreeMap;

/**
 * @Classname: HashTable
 * @Description:
 *
 * 更加合理的动态扩容
 *
 * @Author: Song Ningning
 * @Date: 2020-03-22 22:36
 */
public class HashTable3<K, V> {

    private final int[] CAPACITY
            = {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
            49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
            12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

    private static final int UPPERTOL = 10;     // 上界的因子：UPPERTOL = size (元素个数) / M (数组长度)
    private static final int LOWERTOL = 2;      // 下界的因子：LOWERTOL = size (元素个数) / M (数组长度)
    private int capacityIndex = 0;              // 设置初始值为 CAPACITY 中第一个元素

    private TreeMap<K, V>[] hashtable;
    private int M;
    private int size;

    public HashTable3() {

        this.M = CAPACITY[capacityIndex];
        this.size = 0;
        this.hashtable = new TreeMap[M];
        // 初始化数组每个位置的 TreeMap
        for (int i = 0; i < M; i++) {
            hashtable[i] = new TreeMap<>();
        }
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

            if (size >= UPPERTOL * M && capacityIndex + 1 < CAPACITY.length) {  // 尽量避免用除法
                capacityIndex++;
                resize(CAPACITY[capacityIndex]);
            }
        }
    }

    public V remove(K key) {

        TreeMap<K, V> map = hashtable[hash(key)];
        V ret = null;

        if (map.containsKey(key)) {
            ret = map.remove(key);
            size--;

            if (size < LOWERTOL * M && capacityIndex - 1 >= 0) {
                capacityIndex--;
                resize(CAPACITY[capacityIndex]);
            }
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

    private void resize(int newM) {

        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        // 初始化新的数组每个位置的 TreeMap
        for (int i = 0; i < newM; i++) {
            newHashTable[i] = new TreeMap<>();
        }

        this.M = newM;  // 保证后面再计算哈希值时，用的是新的 M

        for (TreeMap<K, V> map : hashtable) {  // 取出原来哈希表每个位置存储的 TreeMap
            // 将每个位置存储的 TreeMap 中每个 key 取出，重新计算新的哈希值，再放入 newHashTable
            for (K key : map.keySet()) {
                newHashTable[hash(key)].put(key, map.get(key));
            }
        }

        this.hashtable = newHashTable;
    }

}
