package net.nycjava.darkcorners2.streamdebugging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.out;
import static java.lang.reflect.Proxy.newProxyInstance;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class StreamLogger<T> implements InvocationHandler {

    public static final List<String> REWRAP_METHOD_NAMES = Arrays.asList("sorted", "distinct");

    public static <T> Stream<T> debug(Stream<T> stream) {
        return debug(stream, 1);
    }

    public static <T> Stream<T> debug(Stream<T> stream, int stage) {
        return (Stream<T>) newProxyInstance(
                StreamLogger.class.getClassLoader(),
                new Class[]{Stream.class},
                new StreamLogger<>(stream, stage)
        );
    }

    private final Stream<T> stream;
    private final int stage;

    public StreamLogger(Stream<T> stream, int stage) {
        this.stream = stream;
        this.stage = stage;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        if (method.getReturnType().equals(Stream.class)) {

            // special handling for sorted
            if (REWRAP_METHOD_NAMES.contains(method.getName())) {
                List<Object> intermediateResult = ((Stream<Object>) method.invoke(stream, args)).collect(toList());
                out.println(String.format(
                        "stage %d: %s -> %s",
                        stage,
                        method.getName(),
                        intermediateResult.stream().map(Object::toString).collect(joining(","))
                ));
                return debug((Stream<T>) intermediateResult.stream(), stage + 1);
            }

            if (method.getParameterCount() == 1 &&
                    method.getParameterTypes()[0].isAnnotationPresent(FunctionalInterface.class)) {
                Object wrapper = newProxyInstance(
                        StreamLogger.class.getClassLoader(),
                        new Class[]{method.getParameterTypes()[0]},
                        (object2, method2, args2) -> {
                            Object result = method2.invoke(args[0], args2);
                            out.println(String.format(
                                    "stage %d: %s(%s) -> %s",
                                    stage,
                                    method.getName(),
                                    stream(args2).map(Object::toString).collect(joining(",")),
                                    result));
                            return result;
                        }
                );

                Object[] wrappedArgs = copyOf(args, args.length);
                wrappedArgs[0] = wrapper;
                return debug((Stream<T>) method.invoke(stream, wrappedArgs), stage + 1);
            }

            return debug((Stream<T>) method.invoke(stream, args), stage + 1);
        } else {
            return method.invoke(stream, args);
        }
    }
}
