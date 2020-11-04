import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author:whenleo 2020/11/3
 */
@Data
@AllArgsConstructor
public class  BasicSkipListLevel<T extends Comparable> {
    int span;
    BasicSkipListNode<T> forward = new BasicSkipListNode<T>();
}




