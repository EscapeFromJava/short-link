package com.reske.shortlink.util;

import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.IntStream;

@UtilityClass
public class SequenceGeneratorUtil {

    private static final String LINE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new Random();

    public static String generate() {
        return IntStream.range(0, 6)
                .mapToObj(i -> LINE.charAt(RANDOM.nextInt(LINE.length())))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

}
