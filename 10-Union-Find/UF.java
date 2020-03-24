/**
 * @Classname: UF
 * @Description: 并查集接口
 * @author: Song Ningning
 * @date: 2020-03-18 10:05
 */
public interface UF {

    int getSize();
    boolean isConnected(int p, int q);
    void unionElements(int p, int q);
}
