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

public class Collect {

    public static final String[] WORDS = "The quick brown fox jumped over the lazy dog".split(" ");
    public static final Predicate<String> NOT_XYZ = compile("[XYZ]").asPredicate().negate();

    public static void main(String[] args) {
        // collect intermediate results into lists
        // won't work with infinite streams!

        List<String> result1 = stream(WORDS).collect(toList());
        List<String> result2 = result1.stream().map(String::toUpperCase).collect(toList());
        List<String> result3 = result2.stream().filter(NOT_XYZ).collect(toList());
        List<String> result4 = result3.stream().sorted().collect(toList());
        List<String> result5 = result4.stream().distinct().collect(toList());
        String result6 = result5.stream().collect(joining(" "));

        out.println(result1);
        out.println(result2);
        out.println(result3);
        out.println(result4);
        out.println(result5);
        out.println(result6);
    }
}
