import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * BasicSkipListNode
 *
 * Author:whenleo 2020/11/2
 */
@Data
public class BasicSkipListNode<T extends Comparable> implements Comparable<BasicSkipListNode<T>> {

    T value;

    Double score;

    BasicSkipListNode<T> backward;

    BasicSkipListLevel[] levels;

    BasicSkipListNode() {
        this.value = null;
        this.score = null;
    }

    BasicSkipListNode(T value, double score) {
        this.value = value;
        this.score = score;
    }

    public int compareTo(BasicSkipListNode<T> o) {

        assert (this.value != null);
        assert (this.score != null);

        if (this.score.compareTo(o.getScore()) == 0) {
            return this.value.compareTo(o.getValue());
        }

        return this.score.compareTo(o.getScore());
    }




}
