# 跳表的一个实现 BasicSkipList
## 目前实现以下函数功能
|  函数名   | 作用  |
|  ----  | ----  |
| find  | 根据 value查找节点，未找到节点则返回 null |
| insert  | 插入节点 并返回插入的节点 |
| delete  | 根据 value 删除节点 并返回被删除的节点，未找到节点则返回 null |
| findFirstInRange  | 返回存在于合法区间内的第一个节点，不满足上述任一条件，返回null |
| findLastInRange  | 返回存在于合法的区间内的最后一个节点，不满足上述任一条件，返回null |