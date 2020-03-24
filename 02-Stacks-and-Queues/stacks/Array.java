package stacks;

/**
 * @Classname: Array
 * @Description: 动态数组
 * @author: Song Ningning
 * @date: 2020-03-02 21:22
 */
public class Array<E> {

    private E[] data;
    private int size;

    // 传入数组的容量 capacity 构造Array
    public Array(int capacity ){
        this.data = (E[]) new Object[capacity];
    }

    // 空参构造器，默认数组容量 capacity = 10
    public Array(){
        this(10);
    }

    // 获取数组中元素个数
    public int getSize() {
        return size;
    }

    // 获取数组容量
    public int getCapacity() {
        return data.length;
    }

    // 返回数组是否为空
    public boolean isEmpty(){
        return size == 0;
    }

    // 向所有元素后添加一个新元素
    public void addLast(E e){
        add(size, e);
    }

    // 在所有元素前添加一个新元素
    public void addFirst(E e){
        add(0, e);
    }

    // 在第 index 个位置插入一个新元素 e
    public void add( int index, E e ){
        if ( index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size.");

        if ( size == data.length )
            resize(2 * data.length);

        for (int i = size - 1; i >= index; i--)
            data[i + 1] = data[i];

        data[index] = e;
        size ++;
    }

    // 获取 index 索引位置的元素
    public E get(int index) {
        if ( index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Require index >= 0 and index < size.");
        return data[index];
    }

    // 获取最后一个元素
    public E getLast(){
        return get(size - 1);
    }

    // 获取第一个元素
    public E getFirst(){
        return get(0);
    }

    // 修改 index 索引位置的为 e
    public void set(int index, E e){
        if ( index < 0 || index >= size) // 这里将在最后添加也视为非法
            throw new IllegalArgumentException("Get failed. Require index >= 0 and index < size.");
        data[index] = e;
    }

    // 查询数组中是否存在元素 e
    public boolean contains(E e){
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e))
                return true;
        }
        return false;
    }

    // 查询数组中元素 e 的索引位置，如果不存在，返回 -1
    public int find(E e){
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e))
                return i;
        }
        return -1;
    }

    // 从数组中删除 index 位置的元素，返回被删除的元素
    public E remove(int index){
        if ( index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Require index >= 0 and index < size.");
        E ret = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size --;
        data[size] = null; // loitering objects != memory leek

        if (size == data.length / 4 && data.length / 2 != 0)
            resize(data.length / 2);
        return ret;
    }

    // 从数组中删除第一个元素，返回被删除的元素
    public E removeFirst(){
        return remove(0);
    }

    // 从数组中删除最后一个元素，返回被删除的元素
    public E removeLast(){
        return remove(size - 1);
    }

    // 从数组中删除第一个元素 e
    public void removeElement(E e){
        int index = find(e);
        if (index != -1)
            remove(index);
    }

    // 从数组中删除所有元素 e
    public void removeAllElement(E e){
        int index;
        while (true) {
            index = find(e);
            if (index == -1)
                break;
            remove(index);
        }
    }

    // 改变数组长度
    private void resize(int newCapacity){
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d, capacity = %d\n", size, data.length));
        res.append("[");
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if(i != size - 1)
                res.append(", ");
        }
        res.append("]");
        return res.toString();
    }

}
