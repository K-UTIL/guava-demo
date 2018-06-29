package collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectionHelpersExplained {
    @Test
    public void testLoggingList() {
        LoggingList<String> strings = new LoggingList<>();
        strings.add("test1");
    }

    @Test
    public void testPeekingIterator() {
        ArrayList<String> strings = Lists.newArrayList("test1", "test2", "test3", "test4");
        PeekingIterator<String> stringPeekingIterator = Iterators.peekingIterator(strings.iterator());
        while (stringPeekingIterator.hasNext()) {
            if (stringPeekingIterator.peek().equals("test4")) break;
        }

    }

    @Test
    public void testAbstractIterator() {
        ArrayList<String> strings = Lists.newArrayList("test1", null, "test2", "test3");
        Iterator<String> stringIterator = CollectionHelpersExplained.skipNulls(strings.iterator());
        stringIterator.forEachRemaining(y -> System.out.print(y + " "));
    }

    public static <E> Iterator<E> skipNulls(final Iterator<E> in) {
        return new AbstractIterator<E>() {
            @Override
            protected E computeNext() {
                while (in.hasNext()) {
                    E next = in.next();
                    if (next != null) return next;
                }
                return endOfData();
            }
        };
    }

    @Test
    public void testSequential(){
        Iterator sequentialIterator = CollectionHelpersExplained.getSequentialIterator();
        for(int i = 0; i< 100; i++){
            System.out.printf("%d ",sequentialIterator.next());
        }
    }

    //有点像生成器
    public static Iterator getSequentialIterator(){
        return new AbstractSequentialIterator<BigInteger>(BigInteger.valueOf(0)) {
            BigInteger a = BigInteger.valueOf(0);
            BigInteger b = BigInteger.valueOf(1);

            @Override
            protected BigInteger computeNext(BigInteger previous) {
                BigInteger c = a.add(b);
                a = b;
                b = c;
                return b;
            }

        };
    }


}

class LoggingList<E> extends ForwardingList<E> {

    final ArrayList<E> delegate = new ArrayList<>();

    @Override
    protected List<E> delegate() {
        return delegate;
    }

    @Override
    public void add(int index, E element) {
        System.out.println(String.format("new add name %s at index %d", element, index));
        super.add(index, element);
    }

    @Override
    public boolean add(E element) {
        System.out.println(String.format("new add name %s ", element));
        return super.add(element);
    }
}