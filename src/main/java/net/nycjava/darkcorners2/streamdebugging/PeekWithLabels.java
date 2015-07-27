package net.nycjava.darkcorners2.streamdebugging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static net.nycjava.darkcorners2.streamdebugging.StreamLogger.debug;

public class PeekWithLabels {

    public static final String[] WORDS = "The quick brown fox jumped over the lazy dog".split(" ");
    public static final Predicate<String> NOT_XYZ = compile("[XYZ]").asPredicate().negate();

    public static void main(String[] args) {
        String result = stream(WORDS).
                peek(log("step 1: ")).
                map(String::toUpperCase).
                peek(log("step 2: ")).
                filter(NOT_XYZ).
                peek(log("step 3: ")).
                sorted().
                peek(log("step 4: ")).
                distinct().
                peek(log("step 5: ")).
                collect(joining(" "));

        out.println(result);
    }

    private static <T> Consumer<T> log(String prefix) {
        return o -> out.println(prefix + o);
    }
}
