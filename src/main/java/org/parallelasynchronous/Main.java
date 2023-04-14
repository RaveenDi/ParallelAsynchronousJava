package org.parallelasynchronous;

import java.awt.desktop.SystemEventListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        create2().thenApply(data -> data*2)
                .exceptionally(Main::handleException)
                .thenAccept(System.out::println);
    }

    public static int handleException(Throwable throwable) {
        System.out.println("Error:  " + throwable);
        throw new RuntimeException("It is beyond all hope");
    }

    public static void waysToGetFuture(CompletableFuture<Integer> future) {
        create2().thenApply(data -> data * 2)
                .thenAccept(System.out::println);
    }

    public static CompletableFuture<Integer> create2() {
        return CompletableFuture.supplyAsync(() -> compute());
    }

    public static int compute() {
        throw new RuntimeException("Something wrong");
    }

    public static CompletableFuture<Integer> create() {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.supplyAsync(() -> 2);
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