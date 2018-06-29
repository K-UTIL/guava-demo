package graphs;

import com.google.common.collect.ImmutableList;
import com.google.common.graph.*;
import org.junit.Test;

import java.util.Set;

/**
 * // TODO: 2018/6/28  
 * // FIXME: 2018/6/28 
 */
public class GraphsExplained {
    @Test
    public void testIndex(){
        MutableValueGraph<Integer, Integer> valueGraph = ValueGraphBuilder.directed().build();
        valueGraph.addNode(1);
        valueGraph.addNode(2);
        valueGraph.putEdgeValue(1,2,10086);
        System.out.println(valueGraph.toString());

        ImmutableValueGraph<Integer, Integer> integerIntegerImmutableValueGraph = ImmutableValueGraph.copyOf(valueGraph);
        System.out.println(integerIntegerImmutableValueGraph);
    }

    @Test
    public void testAbstract(){
//        new AbstractGraph<Integer>()
    }
}
