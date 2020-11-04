import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * Author:whenleo 2020/11/2
 *
 */
public class BasicSkipListTest {


    BasicSkipList<BasicSkipListNode<Integer>> basicSkipList = new BasicSkipList<BasicSkipListNode<Integer>>();

    @Before
    public void init() {


        basicSkipList.insert(new BasicSkipListNode<Integer>(1, 1));
        basicSkipList.insert(new BasicSkipListNode<Integer>(2, 2));
        basicSkipList.insert(new BasicSkipListNode<Integer>(3, 3));
        basicSkipList.insert(new BasicSkipListNode<Integer>(4, 4));
        basicSkipList.insert(new BasicSkipListNode<Integer>(5, 5));
        basicSkipList.insert(new BasicSkipListNode<Integer>(7, 7));
        basicSkipList.insert(new BasicSkipListNode<Integer>(8, 8));
        basicSkipList.insert(new BasicSkipListNode<Integer>(9, 9));
        basicSkipList.insert(new BasicSkipListNode<Integer>(10, 10));
        basicSkipList.insert(new BasicSkipListNode<Integer>(11, 11));
        basicSkipList.insert(new BasicSkipListNode<Integer>(12, 12));
        basicSkipList.insert(new BasicSkipListNode<Integer>(13, 13));
        basicSkipList.insert(new BasicSkipListNode<Integer>(14, 14));
        basicSkipList.insert(new BasicSkipListNode<Integer>(15, 15));
    }

    @Test
    public void find() {
        assertNotNull(basicSkipList.find(11));
        assertEquals(basicSkipList.find(11).getValue(),11);
        assertNull(basicSkipList.find(6));
    }

    @Test
    public void insert() {
        basicSkipList.insert(new BasicSkipListNode<Integer>(6, 6));
        assertNotNull(basicSkipList.find(6));
        assertEquals(basicSkipList.find(6).getValue(), 6);

    }

    @Test
    public void delete() {
        basicSkipList.insert(new BasicSkipListNode<Integer>(6, 6));
        assertNotNull(basicSkipList.find(11));
        assertNotNull(basicSkipList.find(6));
        assertEquals(basicSkipList.find(6).getValue(), 6);
        basicSkipList.delete(6);
        assertEquals(basicSkipList.find(11).getValue(), 11);
        assertNull(basicSkipList.find(6));
    }

    @Test
    public void findInRange() {
        assertEquals(basicSkipList.findFirstInRange(6, 7).getValue(), 7);
        assertEquals(basicSkipList.findFirstInRange(1, 5).getValue(), 2);
        assertEquals(basicSkipList.findFirstInRange(1, 2).getValue(), 2);
        assertNull(basicSkipList.findFirstInRange(15, 16));
        assertEquals(basicSkipList.findFirstInRange(14, 15).getValue(), 15);
        assertNull(basicSkipList.findFirstInRange(0, 1));
        assertNull(basicSkipList.findFirstInRange(-1, 0));

        assertEquals(basicSkipList.findLastInRange(5, 6).getValue(), 5);
        assertEquals(basicSkipList.findLastInRange(1, 5).getValue(), 4);
        assertEquals(basicSkipList.findLastInRange(1, 2).getValue(), 1);
        assertEquals(basicSkipList.findLastInRange(14, 15).getValue(), 14);
        assertNull(basicSkipList.findLastInRange(0, 1));
        assertNull(basicSkipList.findLastInRange(15, 16));
        assertNull(basicSkipList.findLastInRange(-1, 0));
    }

    @Test
    public void random() {
        for (int i = 0; i < 10000; i++) {
//            System.out.println(Math.random());
            //System.out.println(basicSkipList.randomLevel());
        }


    }

}