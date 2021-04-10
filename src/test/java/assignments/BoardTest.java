package assignments;

import org.junit.Assert;
import org.junit.Test;

import java.security.InvalidParameterException;

//@RunWith(Parameterized.class)
public class BoardTest {
    private int[][] input;
    private int[][] expected;
    Board one = new Board(new int[][]{{8, 1, 3}, {4, 7, 2}, {0, 6, 5}});
    Board oneExpectedResult = new Board(new int[][]{{8, 1, 3}, {6, 7, 2}, {0, 4, 5}});
    Board two = new Board(new int[][]{{8, 1, 3}, {4, 5, 2}, {7, 6, 0}});
    Board twoExpectedResult = new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 5, 0}});
    Board three = new Board(new int[][]{{8, 1, 3}, {4, 5, 2}, {7, 6, 0}});
    Board threeExpectedResult = new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 5, 0}});
    Board four = new Board(new int[][]{{8, 1, 3}, {0, 5, 2}, {7, 6, 4}});
    Board fourExpectedResult = new Board(new int[][]{{8, 1, 3}, {0, 6, 2}, {7, 5, 4}});
    Board five = new Board(new int[][]{{8, 9, 3}, {1, 5, 2}, {7, 6, 4}});
    Board fiveExpectedResult = new Board(new int[][]{{8, 9, 3}, {1, 5, 2}, {7, 6, 4}});

//    @Parameterized.Parameters
//    public static Collection data() {
//        return Arrays.asList(new Object[][]{
//                {new Board(new int[][]{{8, 1, 3}, {4, 7, 2}, {0, 6, 5}}), new Board(new int[][]{{8, 1, 3}, {6, 7, 2}, {0, 4, 5}})},
//                {new Board(new int[][]{{8, 1, 3}, {4, 5, 2}, {7, 6, 0}}), new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 5, 0}})},
//                {new Board(new int[][]{{8, 1, 3}, {4, 5, 2}, {7, 6, 0}}), new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 5, 0}})},
//                {new Board(new int[][]{{8, 1, 3}, {0, 5, 2}, {7, 6, 4}}), new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 5, 4}})},
//                {new Board(new int[][]{{8, 9, 3}, {1, 5, 2}, {7, 6, 4}}), new Board(new int[][]{{8, 9, 3}, {1, 5, 2}, {7, 6, 4}})}
//        });
//    }
//    Board inputBoard = new Board(input);
//    Board expectedBoard = new Board(expected);

    //    public BoardTest(int[][] input, int[][] expected) {
//        expectedBoard = new Board(expected);
//        this.input = input;
//        this.expected = expected;
//    }
    @Test
    public void testTwin() {
        Assert.assertEquals(oneExpectedResult, one.twin());
        Assert.assertEquals(twoExpectedResult, two.twin());
        Assert.assertEquals(threeExpectedResult, three.twin());
        Assert.assertEquals(fourExpectedResult, four.twin());
        //Assert.assertEquals(fiveExpectedResult, five.twin());
        //Assert.assertThrows(new InvalidParameterException, five.twin());
    }

    @Test(expected = InvalidParameterException.class)
    public void twinThrowsException() {
        five.twin();
    }

    // are twins of neighbors of the initial node same as the neighbors of twin of the initial node?
    @Test
    public void twinOfNeighborsOfInitialNodeSameAsNeighborsOfTwins() {

    }
}
