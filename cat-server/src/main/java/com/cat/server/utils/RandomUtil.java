package com.cat.server.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

import org.apache.commons.lang3.ArrayUtils;


public class RandomUtil {
//    private final static Logger logger = LoggerFactory.getLogger(RandomUtil.class);

    /**
     * [1,range]
     * @param range
     * @return
     */
    public final static int random(int range) {
        return ThreadLocalRandom.current().nextInt(range) + 1;
    }

    /**
     * [min,max]
     * 
     * @param min
     * @param max
     * @return
     */
    public static final int random(int min, int max) {
        if (min > max) {
            max = min ^ max;
            min = min ^ max;
            max = min ^ max;
        } else if (min == max) {
            return min;
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * [min,max]
     *
     * @param min
     * @param max
     * @return
     */
    public static final long random(long min, long max) {
        if (min > max) {
            max = min ^ max;
            min = min ^ max;
            max = min ^ max;
        } else if (min == max) {
            return min;
        }
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }

    /**
     * [1,100]
     * 
     * @return
     */
    public final static int random100() {
        return ThreadLocalRandom.current().nextInt(100) + 1;
    }

    /**
     * [1,1000]
     * 
     * @return
     */
    public final static int random1000() {
        return ThreadLocalRandom.current().nextInt(1000) + 1;
    }

    /**
     * [1,10000]
     * 
     * @return
     */
    public final static int random10000() {
        return ThreadLocalRandom.current().nextInt(10000) + 1;
    }

    /**
     * 随机判断该几率是否命中
     * 
     * @param probablility
     * @return
     */
    public final static boolean isHit100(int probablility) {
        if (probablility <= 0) {
            return false;
        } else if (probablility >= 100) {
            return true;
        }
        // [0,100)
        int value = ThreadLocalRandom.current().nextInt(100);
        return value < probablility;
    }

    /**
     * 随机判断该几率是否命中 <br>
     * 
     * @param probablility
     * @return
     */
    public final static boolean isHit1000(int probablility) {
        if (probablility <= 0) {
            return false;
        } else if (probablility >= 1000) {
            return true;
        }
        // [0,1000)
        return ThreadLocalRandom.current().nextInt(1000) < probablility;
    }

    /**
     * 随机判断该几率是否命中
     * 
     * @param probablility
     * @return
     */
    public final static boolean isHit10000(int probablility) {
        if (probablility <= 0) {
            return false;
        } else if (probablility >= 10000) {
            return true;
        }
        // [0,10000)
        return ThreadLocalRandom.current().nextInt(10000) < probablility;
    }

    /**
     * 随机判断该几率是否命中
     * 
     * @param probablility
     * @return
     */
    public final static boolean isHit10000(long probablility) {
        if (probablility <= 0) {
            return false;
        } else if (probablility >= 10000) {
            return true;
        }
        // [0,10000)
        return ThreadLocalRandom.current().nextInt(10000) < probablility;
    }

    /**
     * 从一组集合里 抽取1个对象<br>
     * 几率相同
     * 
     * @param sources
     * @return
     */
    public static <T> Optional<T> random(Collection<T> sources) {
        if (sources == null || sources.isEmpty()) {
            return Optional.empty();
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(sources.size());
        int index = 0;
        for (T tmp : sources) {
            if (index == randomIndex) {
                return Optional.ofNullable(tmp);
            }
            index++;
        }
        return Optional.empty();
    }

    /**
     * 从一组集合里 抽取1个对象<br>
     * 几率相同
     * 
     * @param sourceList
     * @return
     */
    public static <T> Optional<T> random(List<T> sourceList) {
        if (sourceList == null || sourceList.isEmpty()) {
            return Optional.empty();
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(sourceList.size());
        T result = sourceList.get(randomIndex);
        return Optional.ofNullable(result);
    }

    /**
     * 随机生成数组<br>
     * 数组元素在[min,max]中<br>
     * 若不可重复 且随机范围比数量小 生成的数组小于randomSize
     * 
     * @param min
     * @param max
     * @param randomSize
     * @param repeat     是否有重复的元素
     * @return
     */
    public static int[] randomArray(int min, int max, int randomSize, boolean repeat) {
        if (min > max || randomSize <= 0) {
            return new int[0];
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (repeat) {
            // 可重复抽取
            int[] result = new int[randomSize];
            for (int i = 0; i < randomSize; i++) {
                result[i] = random.nextInt(min, max + 1);
            }
            return result;
        }
        // 不重复抽取
        int poolSize = max - min + 1;
        if (poolSize < randomSize) {
            randomSize = poolSize;
        }
        int[] srcArray = new int[poolSize];
        for (int i = 0; i < poolSize; i++) {
            srcArray[i] = min + i;
        }
        ArrayUtils.shuffle(srcArray, random);
        int[] result = Arrays.copyOf(srcArray, randomSize);
        return result;
    }

    /**
     * 从一组集合里 抽取多个对象<br>
     * 几率相同
     * 
     * @param sourceList
     * @param resultSize 若数量大于来源 且不可重复 返回列表大小为较小值
     * @param repeat
     * @return
     */
    public static <T> List<T> randomList(List<T> sourceList, int resultSize, boolean repeat) {
        if (sourceList == null || sourceList.isEmpty() || resultSize <= 0) {
            return Collections.emptyList();
        }
        int[] randomIndexs = randomArray(0, sourceList.size() - 1, resultSize, repeat);
        List<T> result = new ArrayList<>(resultSize);
        for (int index : randomIndexs) {
            T tmp = sourceList.get(index);
            result.add(tmp);
        }
        return result;
    }

    /**
     * 从一组集合里 抽取多个对象<br>
     * 几率相同
     * 
     * @param sources
     * @param resultSize 若数量大于来源 且不可重复 返回列表大小为较小值
     * @param repeat
     * @return
     */
    public static <T> List<T> randomList(Collection<T> sources, int resultSize, boolean repeat) {
        if (sources == null || sources.isEmpty() || resultSize <= 0) {
            return Collections.emptyList();
        }
        int[] randomIndexs = randomArray(0, sources.size() - 1, resultSize, repeat);
        List<T> tmpList = null;
        if (sources instanceof List) {
            tmpList = (List<T>) sources;
        } else {
            tmpList = new ArrayList<>(sources);
        }
        List<T> result = new ArrayList<>(resultSize);
        for (int index : randomIndexs) {
            T tmp = tmpList.get(index);
            result.add(tmp);
        }
        return result;
    }

    /**
     * 从一组集合中 根据权重随机取出1个对象
     * 
     * @param sources
     * @param calcWeightFunction 计算列表元素权重的方法 权重<=0 不会被抽取
     * @return
     */
    public static <T> Optional<T> randomByWeight(Collection<T> sources, Function<T, Integer> calcWeightFunction) {
        if (sources == null || sources.isEmpty() || calcWeightFunction == null) {
            return Optional.empty();
        }
        // 塞到临时的列表中 避免权重或者来源列表中途发生变化
        List<Pair<Integer, T>> weightList = new ArrayList<>();
        int totalWeight = 0;
        for (T tmp : sources) {
            int weight = calcWeightFunction.apply(tmp);
            if (weight <= 0) {
                continue;
            }
            totalWeight += weight;
            Pair<Integer, T> tmpPair = Pair.of(weight, tmp);
            weightList.add(tmpPair);
        }
        if (totalWeight <= 0 || weightList.isEmpty()) {
            return Optional.empty();
        }
        // [0,total)
        int randomValue = ThreadLocalRandom.current().nextInt(totalWeight);
        for (Pair<Integer, T> tmpPair : weightList) {
            int weight = tmpPair.getKey();
            randomValue -= weight;
            if (randomValue >= 0) {
                continue;
            }
            T value = tmpPair.getValue();
            return Optional.ofNullable(value);
        }
        return Optional.empty();
    }

    /**
     * 从一组集合中 根据权重随机取出指定数量个对象
     * 
     * @param sources
     * @param resultSize         若数量大于来源 且不可重复 返回列表大小为较小值
     * @param repeat             是否会重复抽取
     * @param calcWeightFunction 计算列表元素权重的方法 权重<=0 不会被抽取
     * @return
     */
    public static <T> List<T> randomListByWeight(Collection<T> sources, int resultSize, boolean repeat, Function<T, Integer> calcWeightFunction) {
        if (sources == null || sources.isEmpty() || resultSize <= 0 || calcWeightFunction == null) {
            return Collections.emptyList();
        }
        // 塞到临时的列表中 避免权重或者来源列表中途发生变化
        List<Pair<Integer, T>> weightList = new ArrayList<>();
        int totalWeight = 0;
        for (T tmp : sources) {
            int weight = calcWeightFunction.apply(tmp);
            if (weight <= 0) {
                continue;
            }
            totalWeight += weight;
            Pair<Integer, T> tmpPair = Pair.of(weight, tmp);
            weightList.add(tmpPair);
        }
        if (totalWeight <= 0 || weightList.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> result = new ArrayList<>();
        if (!repeat && resultSize >= weightList.size()) {
            // 不可重复 且获取数量>=池
            for (Pair<Integer, T> tmpPair : weightList) {
                T tmp = tmpPair.getValue();
                result.add(tmp);
            }
            return result;
        }
        ThreadLocalRandom random = ThreadLocalRandom.current();
        outer: for (int i = 0; i < resultSize && totalWeight > 0; i++) {
            int randomValue = random.nextInt(totalWeight);
            inner: for (int j = 0; j < weightList.size(); j++) {
                Pair<Integer, T> tmpPair = weightList.get(j);
                int weight = tmpPair.getKey();
                if (weight <= 0) {
                    continue;
                }
                randomValue -= weight;
                if (randomValue >= 0) {
                    continue;
                }
                T value = tmpPair.getValue();
                result.add(value);
                if (!repeat) {
                    // 不可重复
                    totalWeight -= weight;
                    tmpPair.setKey(0);
                    if (totalWeight <= 0) {
                        break outer;
                    }
                }
                // 可重复
                break inner;
            }
        }
        return result;
    }

    /**
     * 根据Map里的权重随机出<T> 权重不能做key,因为有同权重的可能
     *
     * @param map <T, 权重>
     */
    public static <T> T randomMapIdByWeight(Map<T, Integer> map) {
        int total = 0;
        for (Integer num : map.values()) {
            total += num;
        }

        int random = random(1, total);

        int i = 0;
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            i += entry.getValue();
            if (i >= random) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 根据int[][]里的权重随机出值 int[0] 要随机的数 int[1] 权重
     * 
     * @param intArr 要随机的数,权重
     */
    public static int randomIntsByWeight(int[][] intArr) {
        int total = 0;
        for (int[] arr : intArr) {
            total += arr[1];
        }

        int random = random(1, total);

        int i = 0;
        for (int[] arr : intArr) {
            i += arr[1];
            if (i >= random) {
                return arr[0];
            }
        }
        return -1;
    }

}
