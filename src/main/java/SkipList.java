/**
 * SkipList interface
 *
 * Author:whenleo 2020/11/2
 */
interface SkipList {
    Object find(Object v);

    Object insert(Object v);

    Object delete(Object v);

    Object findFirstInRange(double start, double end);

    Object findLastInRange(double start, double end);
}
