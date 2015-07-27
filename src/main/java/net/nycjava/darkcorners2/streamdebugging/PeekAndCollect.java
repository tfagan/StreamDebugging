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

public class PeekAndCollect {

    public static final String[] WORDS = "The quick brown fox jumped over the lazy dog".split(" ");
    public static final Predicate<String> NOT_XYZ = compile("[XYZ]").asPredicate().negate();

    public static void main(String[] args) {
        // print out the output at the end
        List<String> debug1 = new ArrayList<>();
        List<String> debug2 = new ArrayList<>();
        List<String> debug3 = new ArrayList<>();
        List<String> debug4 = new ArrayList<>();
        List<String> debug5 = new ArrayList<>();

        String result = stream(WORDS).
                peek(debug1::add).
                map(String::toUpperCase).
                peek(debug2::add).
                filter(NOT_XYZ).
                peek(debug3::add).
                sorted().
                peek(debug4::add).
                distinct().
                peek(debug5::add).
                collect(joining(" "));

        out.println(debug1);
        out.println(debug2);
        out.println(debug3);
        out.println(debug4);
        out.println(debug5);

        out.println(result);
    }
}
