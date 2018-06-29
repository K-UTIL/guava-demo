package collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.*;

public class UtilityClasses {

    @Test
    public void testConstructors() {

        ArrayList<String> list = Lists.newArrayList("test1", "test2", "test3");
        System.out.println(Arrays.toString(list.toArray()));
        Maps.newHashMap();
    }

    @Test
    public void testIterable() {

        ArrayList<String> strings1 = Lists.newArrayList("test1", "test2", "test3", "test4", "test5");
        ArrayList<String> strings2 = Lists.newArrayList("test1", "test2", "test3");
        Iterator<String> iterator = strings1.iterator();
        Iterables.concat(strings1).forEach(System.out::println);
        //        Iterators.concat(iterator,strings2.iterator()).forEachRemaining(System.out::println);
//        System.out.println(Iterators.frequency(iterator,"test1"));
//        Iterables.concat(strings2.iterator())
        Iterators.partition(iterator, 3).forEachRemaining(t -> System.out.println(Arrays.toString(t.toArray())));
        System.out.println(Iterables.toString(strings2));
        System.out.println(Iterables.contains(strings2, strings1));
        FluentIterable.from(strings1)
                .filter(t -> t.contains("4"))
                .forEach(System.out::println);
        strings1.contains("4");
        Lists.newArrayList(strings1);
        Sets.newCopyOnWriteArraySet(strings1);
    }

    @Test
    public void testImmutableSet() {
        Set<String> immutableSet = ImmutableSet.of("test1", "test2", "test3");
        immutableSet.add("test4");
    }

    @Test
    public void testFactories() {
        ArrayList<String> strings = Lists.newArrayList("test1", "test2", "test3", "test4");
        ImmutableMap<Integer, String> integerStringImmutableMap = Maps.uniqueIndex(strings, input -> Integer.parseInt(input.substring(input.length() - 1, input.length())));
        integerStringImmutableMap.forEach((k,v)-> System.out.println(String.format("(%d -> %s)",k,v)));

        MapDifference<Integer, String> difference = Maps.difference(integerStringImmutableMap, integerStringImmutableMap);
        difference.entriesInCommon().forEach((k,v)-> System.out.println(String.format("(%d -> %s)",k,v)) );
    }

    @Test
    public void testBiMap(){
        Map<String, Integer> hashMap = new HashMap<String, Integer>() {{
            put("one", 1);
            put("two", 2);
            put("three", 3);
        }};
        BiMap<String, Integer> stringIntegerBiMap = Maps.synchronizedBiMap(HashBiMap.create(hashMap));
        System.out.println( stringIntegerBiMap.inverse().get(1));
    }

    @Test
    public void testMultiset(){
        TreeMultiset<String> strings = TreeMultiset.<String>create(Lists.newArrayList("test1", "test2", "test1"));
        System.out.println(strings.count("test1"));
    }

    @Test
    public void testMultimap(){
        TreeMultimap<Integer, String> integerStringTreeMultimap = TreeMultimap.create();
    }

}
