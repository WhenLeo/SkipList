/*
 * implement SkipList
 *
 * Author:whenleo 2020/11/2
 */

public class BasicSkipList<T extends Comparable> implements SkipList {

    final static double LEVEL_P = 0.5;

    final static int MAX_LEVEL = 32;

    BasicSkipListNode<T> header;

    BasicSkipListNode<T> tail;

    long length;

    int level;

    public BasicSkipList() {

        this.length = 0;
        this.level = 1;
        header = new BasicSkipListNode<T>(null, 0);
        BasicSkipListLevel<T>[] level = new BasicSkipListLevel[MAX_LEVEL];

        for (int i = 0; i < MAX_LEVEL; i++) {
            level[i] = new BasicSkipListLevel<>(0, null);
        }

        header.setLevels(level);

        tail = null;
    }

    /*
     *
     * 根据 value查找节点，未找到节点则返回 null
     * v---value
     *
     **/
    public BasicSkipListNode<T> find(Object v) {

        T value = (T) v;

        BasicSkipListNode<T> p = header;

        //按从高到低层次遍历整个表
        for (int i = level - 1; i >= 0; i--) {

            while (p.getLevels()[i].forward != null && p.getLevels()[i].forward.getValue().compareTo(value) < 0) {
                p = p.getLevels()[i].forward;
            }

            if (p.getLevels()[i].forward != null && p.getLevels()[i].forward.getValue().compareTo(value) == 0) {
                return p.getLevels()[i].forward;
            }
        }

        return null;
    }

    /*
     *
     * 插入节点 并返回插入的节点
     * v---节点包含value 和 score
     *
     **/
    public BasicSkipListNode<T> insert(Object v) {

        BasicSkipListNode<T> newNode = new BasicSkipListNode<>();
        newNode.setValue((T) ((BasicSkipListNode<?>) v).getValue());
        newNode.setScore(((BasicSkipListNode<?>) v).getScore());

        BasicSkipListNode<T>[] update = new BasicSkipListNode[MAX_LEVEL];

        assert (newNode.getScore() != null);

        BasicSkipListNode<T> p = header;

        //分层获取插入位置 存储在update数组中

        for (int i = this.level - 1; i >= 0; i--) {

            while (p.getLevels()[i].forward != null && (p.getLevels()[i].forward.compareTo(newNode)) < 0) {
                p = p.getLevels()[i].forward;
            }

            update[i] = p;
        }

        int level = randomLevel();

        //判断是否需要更新level
        if (level > this.level) {
            for (int i = this.level; i < level; i++) {
                update[i] = header;
            }
            this.level = level;
        }

        //根据update数组分层插入新节点
        BasicSkipListLevel<T>[] newNodeLevel = new BasicSkipListLevel[level];

        for (int i = 0; i < level; i++) {
            newNodeLevel[i] = new BasicSkipListLevel<T>(1, update[i].levels[i].forward);
            update[i].levels[i].setForward(newNode);
        }

        //设置新插入节点属性
        newNode.setLevels(newNodeLevel);
        newNode.setBackward(update[0].equals(this.header) ? null : update[0]);

        if (newNode.getLevels()[0].forward != null)
            newNode.getLevels()[0].forward.setBackward(newNode);
        else
            this.tail = newNode;

        length++;
        return newNode;
    }

    /*
     *
     * 根据 value 删除节点 并返回被删除的节点，未找到节点则返回 null
     * v---value
     *
     *
     **/
    public BasicSkipListNode<T> delete(Object v) {

        T value = (T) v;

        BasicSkipListNode<T> p = header;

        BasicSkipListNode<T> target = null;

        //按从高到低层次遍历整个表
        for (int i = level - 1; i >= 0; i--) {

            while (p.getLevels()[i].forward != null && p.getLevels()[i].forward.getValue().compareTo(value) < 0) {
                p = p.getLevels()[i].forward;
            }

            if (p.getLevels()[i].forward != null && p.getLevels()[i].forward.getValue().compareTo(value) == 0) {
                if (target != null)
                    target = p.getLevels()[i].forward;

                p.getLevels()[i].forward = p.getLevels()[i].forward.getLevels()[i].forward;
            }
        }

        return target;
    }

    /*
     *
     * 返回存在于合法的(start, end]内的第一个节点，不满足上述任一条件，返回null
     *
     **/
    public BasicSkipListNode<T> findFirstInRange(double start, double end) {

        if (!isInRange(start, end)) return null;

        BasicSkipListNode<T> p = header;

        for (int i = level - 1; i >= 0; i--) {
            while (p.getLevels()[i].forward != null &&
                    p.getLevels()[i].forward.getScore().compareTo(start) <= 0) {
                p = p.getLevels()[i].forward;
            }
        }

        p = p.getLevels()[0].forward;

        return p;
    }

    /*
     *
     * 返回存在于合法的[start, end)内的最后一个节点，不满足上述任一条件，返回null
     *
     **/
    public BasicSkipListNode<T> findLastInRange(double start, double end) {

        if (!isInRange(start, end)) return null;

        BasicSkipListNode<T> p = header;

        for (int i = level - 1; i >= 0; i--) {
            while (p.getLevels()[i].forward != null &&
                    p.getLevels()[i].forward.getScore().compareTo(end) < 0) {
                p = p.getLevels()[i].forward;
            }
        }

        return p;
    }

    /*
     *
     *  根据幂等法则随机生成节点元素的层次
     *
     **/
    private int randomLevel() {

        int level = 1;

        while ((Math.random() - LEVEL_P) < 0.0000001 && level < MAX_LEVEL) {
            level++;
        }

        return level;
    }


    /*
     *
     *  判断该跳表中是否有(start, end)这个范围
     *
     **/
    private boolean isInRange(double start, double end) {

        //判断范围是否正常
        if (start > end) return false;

        //判断最大值
        BasicSkipListNode<T> p = tail;
        if (p == null || start >= tail.getScore()) return false;

        //判断最小值
        p = header.getLevels()[0].forward;
        if (p == null || end <= p.getScore()) return false;

        return true;
    }
}
