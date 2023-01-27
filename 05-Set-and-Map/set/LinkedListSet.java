package set;

/**
 * @Classname: LinkedListSet
 * @Description: 链表实现集合
 * @author: Sningning
 * @date: 2020-03-11 10:43
 */
public class LinkedListSet<E> implements Set<E> {

    private LinkedList<E> list;

    public LinkedListSet() {
        list = new LinkedList<>();
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    @Override
    public void add(E e) {
        if ( !list.contains(e) ) {
            list.addFirst(e);  // LinkedList 类中没有尾指针，因此从头部插入复杂度为 O(1)
        }
    }

    @Override
    public void remove(E e) {
        list.removeElement(e);
    }
}
