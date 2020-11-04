# 跳表的一个实现 BasicSkipList

图源：《Redis设计与实现》黄健宏

## 目前实现以下函数功能
|  函数名   | 作用  |
|  ----  | ----  |
| find  | 根据 value查找节点，未找到节点则返回 null |
| insert  | 插入节点 并返回插入的节点 |
| delete  | 根据 value 删除节点 并返回被删除的节点，未找到节点则返回 null |
| findFirstInRange  | 返回存在于合法区间内的第一个节点，不满足上述任一条件，返回null |
| findLastInRange  | 返回存在于合法的区间内的最后一个节点，不满足上述任一条件，返回null |

## 跳跃表介绍

跳跃表（skiplist）是一种有序数据结构，它通过在每个数据节点中创建一组指向其他节点的指针，从而达到快速访问节点的目的。跳跃表支持平均O(logN)、最坏O(N)复杂度的节点查找，其中N为所有数据节点中创建的指针的最大值。在Redis中使用跳跃表作为有序集合键的底层实现之一（另外还会使用ziplist，压缩链表）。Redis中实现的跳跃表结构如下图所示。

![image-20201104193910422](http://qn.scq725.cn/20201104193919.png)

## 跳跃表定义

在Redis中跳跃表主要使用了三个数据结构来表示：redis.h/zskiplistNode、zskiplist、zskiplistLevel。

### zskiplistNode & zskiplistLevel

```C
typedef struct zskiplistNode {

    robj *obj;

    double score;

    struct zskiplistNode *backward;

    struct zskiplistLevel {

        struct zskiplistNode *forward;

        unsigned int span;
    } level[];
    
} zskiplistNode;
```

zskiplistNode用于表示一个跳跃表节点，其中obj是基本Redis对象，也就是节点的值，score用于排序，默认是一个double值，backward总是指向前一个节点（按照实际score顺序，而非level指针顺序），level是一个zskiplistLevel数组，其中包含一个forward指针，指向该层次上的下一个节点（区别于backward），span代表跨度，仅用以计算节点的实际排位。

### zskiplist

```c
typedef struct zskiplist {

    struct zskiplistNode *header, *tail;

    unsigned long length;

    int level;

} zskiplist;
```

zskiplist是用来保存跳跃表节点的，其中header指针指向一个开始节点，这个节点本身不保存任何数据信息却拥有最大的指针数。tail指针指向尾节点。遍历操作便是从此开始。length表示为该跳跃表的长度，level表示该跳跃表中不含header节点的所有节点最大层次，因此每次进行插入操作时都要更新一次，删除操作不更新。

## 跳跃表特性

 1）每个跳跃表节点的指针层高都是1至32之间的随机数，按照幂等原则，不生成过高的层次，生成2层的概率为1/2，3层为1/4，4层为1/8...... ；

2）同一个跳跃表中，分值score可以相同，但是值必须是唯一的（Redis中跳跃表主要是用来实现zset，其中元素唯一）；

3）跳跃表中节点按照分值大小排序，分值相同按照值大小排序。

