package org.parallelasynchronous;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.parallelStream()
                .map(Main::transform)
                .forEachOrdered(e -> printIt(e));
    }

    public static void printIt(int num) {
        System.out.println("P: " + num + "--" + Thread.currentThread());
    }

    public static int transform(int num) {
        System.out.println("t: " + num + "--" + Thread.currentThread());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return num;
    }

    public static void streamUse(Stream<Integer> stream) {
        stream.parallel()
                .map(e -> transform(e))
                .forEach(System.out::println);
    }

    public static void functionalStyle(List<Integer> list) {
        System.out.println(
                list.stream()
                        .filter(e -> e % 2 == 0)
                        .mapToInt(e -> e * 2)
                        .sum()
        );
    }

    public static void imperativeStyle(List<Integer> list){
        int tot = 0;
        for (int i : list){
            if(i % 2 == 0){
                tot += i * 2;
            }
        }
        System.out.println(tot);
    }
}