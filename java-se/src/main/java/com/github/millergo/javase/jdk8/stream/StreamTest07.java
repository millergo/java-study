package com.github.millergo.javase.jdk8.stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Spliterator source analysis
 * <p>
 * 1. Spliterator 用于对"数据源"中的元素进行遍历和分区。
 * 2. Spliterator 可以单独遍历元素(tryAdvance())，也可以批量遍历元素(forEachRemaining())。
 * 3. Spliterator 也可以将它的一些元素(使用tryssplit)划分为另一个Spliterator，用于可能的并行操作。每个Spliterator仅对单个批量计算有用。
 * 4. Spliterator 还可以报告其具备结构、源和元素的一组特征(characteristics)，
 * 这些特征包含:ORDERED, DISTINCT, SORTED, SIZED, NONNULL, IMMUTABLE, CONCURRENT, and SUBSIZED.
 * 5. 当数据源绑定一个 spliterator 分割器之后，如果正在对数据源进行遍历时引发了一个修改操作，比如并发时，那么可能会
 * 触发 ConcurrentModificationException 异常。Spliterator 的批量遍历方法(forEachRemaining()) 可以优化遍历，
 * 并在遍历完所有元素后检查结构干扰，而不是检查每个元素然后立即失败。
 *
 * </p>
 *
 * @author Miller Shan
 * @version 1.0
 * @see java.util.Spliterator
 * @see java.util.Spliterators
 * @since 2023/10/25 14:25:06
 */
public class StreamTest07 {
    private final List<String> myFamily = Arrays.asList("Miller", "Vicky", "Mila");

    /**
     * 当 Stream 的 close() 方法被调用的时候 onClose() 方法被调用
     */
    @Disabled
    @Test
    void testCloseMethod() {
        // 通过 try with 语法自动调用close方法
        try (Stream<String> myFamilyStream = myFamily.stream()) {
            myFamilyStream.onClose(() -> {
                System.out.println("Stream closed first.");
                throw new NullPointerException();
            }).onClose(() -> {
                System.out.println("Stream closed second.");
                // 第一个异常之后的所有异常都会被添加到第一个异常的压制中，Suppressed。
                throw new RuntimeException();
            }).onClose(() -> {
                System.out.println("Stream closed third.");
                throw new IllegalArgumentException();
            }).forEach(System.out::println);
        }
    }

    @DisplayName("Spliterator source analysis.")
    @Test
    void testSpliterator() {
        myFamily.stream().forEachOrdered(System.out::println);
        TaggedArray<String> stringTaggedArray = new TaggedArray<>(new String[]{"Miller", "Vicky", "Mila"}, new Object[]{"Male", "Female", "Female"});
        Spliterator<String> spliterator = stringTaggedArray.spliterator();
        spliterator.forEachRemaining(System.out::println);

    }

}

/**
 * Spliterator 源码中JavaDoc的例子
 * @see Spliterator
 */
class TaggedArray<T> {
    private final Object[] elements;    // immutable after construction

    TaggedArray(T[] data, Object[] tags) {
        int size = data.length;
        if (tags.length != size) throw new IllegalArgumentException();
        this.elements = new Object[2 * size];
        for (int i = 0, j = 0; i < size; ++i) {
            elements[j++] = data[i];
            elements[j++] = tags[i];
        }
    }

    public Spliterator<T> spliterator() {
        System.out.println("spliterator invoked.");
        return new TaggedArraySpliterator<>(elements, 0, elements.length);
    }

    static class TaggedArraySpliterator<T> implements Spliterator<T> {
        private final Object[] array;
        private int origin; // current index, advanced on split or traversal
        private final int fence; // one past the greatest index

        TaggedArraySpliterator(Object[] array, int origin, int fence) {
            this.array = array;
            this.origin = origin;
            this.fence = fence;
        }

        /**
         * 遍历源中的每一个元素，并对元素进行操作
         */
        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            System.out.println("forEachRemaining invoked.");
            for (; origin < fence; origin += 2) action.accept((T) array[origin]);
        }

        /**
         * 尝试获取到下一个元素
         */
        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            System.out.println("tryAdvance invoked.");
            if (origin < fence) {
                action.accept((T) array[origin]);
                origin += 2;
                return true;
            } else // cannot advance
                return false;
        }

        /**
         * 尝试对源中的数据进行对半分割，这样可以保证源中的元素被分割成相等的两份，
         * 然后通过底层的多线程对数据进行处理，提高效率。
         */
        @Override
        public Spliterator<T> trySplit() {
            System.out.println("trySplit invoked.");
            int lo = origin; // divide range in half
            int mid = ((lo + fence) >>> 1) & ~1; // force midpoint to be even
            if (lo < mid) { // split out left half
                origin = mid; // reset this Spliterator's origin
                return new TaggedArraySpliterator<>(array, lo, mid);
            } else       // too small to split
                return null;
        }

        /**
         * 估算大小，每次取中间
         */
        @Override
        public long estimateSize() {
            System.out.println("estimateSize invoked.");
            return (long) ((fence - origin) / 2);
        }

        /**
         * 声明分割器具备的特性
         */
        @Override
        public int characteristics() {
            System.out.println("characteristics invoked.");
            return ORDERED | SIZED | IMMUTABLE | SUBSIZED;
        }
    }
}
