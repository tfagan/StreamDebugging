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
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static net.nycjava.darkcorners2.streamdebugging.StreamLogger.debug;

public class Base {

    public static final String[] WORDS = "The quick brown fox jumped over the lazy dog".split(" ");
    public static final Predicate<String> NOT_XYZ = Pattern.compile("[XYZ]").asPredicate().negate();

    public static void main(String[] args) {
        // the base case
        String result = Arrays.stream(WORDS).
                map(String::toUpperCase).
                filter(NOT_XYZ).
                sorted().
                distinct().
                collect(Collectors.joining(" "));

        System.out.println(result);
    }
}
