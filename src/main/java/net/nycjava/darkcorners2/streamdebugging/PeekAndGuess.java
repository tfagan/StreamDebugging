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

public class PeekAndGuess {

    public static final String[] WORDS = "The quick brown fox jumped over the lazy dog".split(" ");
    public static final Predicate<String> NOT_XYZ = compile("[XYZ]").asPredicate().negate();

    public static void main(String[] args) {
        // peek is your friend
        String result = stream(WORDS).
                peek(out::println).
                map(String::toUpperCase).
                peek(out::println).
                filter(NOT_XYZ).
                peek(out::println).
                sorted().
                peek(out::println).
                distinct().
                peek(out::println).
                collect(joining(" "));

        out.println(result);
    }
}
