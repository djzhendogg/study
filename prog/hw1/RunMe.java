import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Run this code with provided arguments.
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
@SuppressWarnings("MagicNumber")
public final class RunMe {
    private RunMe() {
        // Utility class
    }

    public static void main(final String[] args) throws IOException {
        final byte[] password = parseArgs(args);
//        flag5(password);
//        flag7(password);
//        flag8(password);
//        flag10(password);
        flag14(password);
//        flag16(password);
//        flag17(password);
//        flag18(password);
//        flag19(password);
//        flag20(password);
    }



//    private static void flag3(final byte[] password) {
//        int result = 0;
//        for (int i = 0; i < 2024; i++) {
//            for (int j = 0; j < 2024; j++) {
//                for (int k = 0; k < 2024; k++) {
//                    for (int p = 0; p < 12; p++) {
//                        result ^= (i * 13) | (j + k * 7) & ~p;
//                        result ^= result << 1;
//
//                        System.out.printf("%15d %s\n", result, Integer.toBinaryString(result));
//                    }
//                }
//            }
//        }
//
//        print(3, result, password);
//    }
    private static void flag3(final byte[] password) {
        int result = 0;
        for (int i = 0; i < 2024; i++) {
            for (int j = 0; j < 2024; j++) {
                for (int k = 0; k < 2024; k++) {
                    for (int p = 0; p < 12; p++) {
                        result ^= (i * 13) | (j + k * 7) & ~p;
                        result ^= result << 1;
                    }
                }
            }
        }

        print(3, result, password);
    }


    private static void flag4(final byte[] password) {
        final long target = 607768613708938510L + getInt(password); //607768613578332251
        long a = 0b1000011011110011101001010101;
        long aStart = a << 32;
        System.out.println(Long.toBinaryString(target)); //100001101111001110100101010101111101111101111101100001011011
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            if ((aStart + (a ^ i)) == target) {
                print(4, aStart+i, password);
            }
        }
//        1000011011110011101001010101
    }
    /* package-private */ static final long PRIME = 1073741827;

    private static void flag5(final byte[] password) {
        final long n = 1_000_000_000_000_000L + getInt(password);

        BigInteger result = BigInteger.valueOf(0);
        result = result.add(count(n, 3));
        System.out.println(result);
        result = result.add(count(n, 5));
        result = result.add(count(n, 7));
        result = result.add(count(n, 2024));
        result = result.mod(BigInteger.valueOf(PRIME));
//        for (long i = 0; i < n; i++) {
//            result = (result + i / 3 + i / 5 + i / 7 + i / 2024) % PRIME;
//        }
        System.out.println(result);
        System.out.println(result.longValue());
        print(5, result.longValue(), password);
    }


    public static BigInteger count(long n, int del) {
        BigInteger m = BigInteger.valueOf(n - n % del);
        System.out.println(m.subtract(BigInteger.valueOf(1)));
        System.out.println(m.subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(del)));
        System.out.println(m.subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(del)).multiply(m));
        System.out.println(m.subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(del)).multiply(m).divide(BigInteger.valueOf(2)));

        BigInteger res = m.subtract(BigInteger.valueOf(1)).divide(BigInteger.valueOf(del)).multiply(m).divide(BigInteger.valueOf(2));
        BigInteger bigN = BigInteger.valueOf(n).subtract(BigInteger.valueOf(1));

        for (BigInteger i = m; i.compareTo(bigN) <= 0; i = i.add(BigInteger.valueOf(1))) {
            res = res.add(i.divide(BigInteger.valueOf(del)));
        }
        return res;
    }






    private static void flag7(final byte[] password) {
        // Count the number of occurrences of the most frequent noun at the following page:
        // https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html

        // The singular form of the most frequent noun
        final String singular = "";
        // The plural form of the most frequent noun
        final String plural = "";
        // The total number of occurrences
        final int total = 0;
        if (total != 0) {
            print(7, (singular + ":" + plural + ":" + total).hashCode(), password);
        }
    }


    private static void flag8(final byte[] password) throws IOException {
        // Count the number of red (#ff0021) pixes of this image:
        String url = "https://i0.wp.com/blog.nashtechglobal.com/wp-content/uploads/2024/04/Screenshot-from-2024-04-29-23-33-40.png";

        BufferedImage bufferedImage = ImageIO.read(new URL(url));
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        int counter = 0;
        int myRed = new Color(255,0,33).getRGB();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bufferedImage.getRGB(x, y) == myRed) {
                    counter++;
                }
            }
        }
        final int number = counter;
        if (number != 0) {
            print(8, number, password);
        }
    }


    private static final String PATTERN = "Reading a documentation can be surprisingly helpful!";
    private static final int SMALL_REPEAT_COUNT = 10_000_000;



    private static final long LARGE_REPEAT_SHIFT = 28;
    private static final long LARGE_REPEAT_COUNT = 1L << LARGE_REPEAT_SHIFT;
//
//    private static void flag10(final byte[] password) {
//        String repeated = "";
////        for (long i = 0; i < LARGE_REPEAT_COUNT; i++) {
////            repeated += PATTERN;
////        }
//        int hashCode = 0;
//        int charNumber = 0;
//        int limit = 268435456 * PATTERN.length();
//        for (int i = 0; i < limit; i++) {
//            hashCode = hashCode * 31 + PATTERN.charAt(charNumber);
//            charNumber++;
//            if (charNumber > 51) {
//                charNumber = 0;
//            }
//        }
//        String a = PATTERN;
//        a += PATTERN;
//        for (int i = 0; i < a.length(); i++) {
//            hashCode = 0 + PATTERN.charAt(charNumber);
//            charNumber++;
//            if (charNumber > 51) {
//                charNumber = 0;
//            }
//            System.out.println(hashCode);
//        }
//
//        print(10, 0, password);
//    }

    private static void flag10(final byte[] password) {
        int patternLen = PATTERN.length();
        int patternCounter = 0;
//        long powMinus = 1;
        int hashCode = 0;

        for (long i = 0; i < LARGE_REPEAT_COUNT; i++) {
            hashCode = 31 * hashCode + PATTERN.charAt(patternCounter);;
            patternCounter++;
            if (patternCounter >= patternLen) {
                patternCounter = 0;
            }

        }
        System.out.println(hashCode);
        print(10, hashCode, password);
    }


    private static void flag12(final byte[] password) {
        final BigInteger year = BigInteger.valueOf(-2024);
        final BigInteger term = BigInteger.valueOf(PRIME + Math.abs(getInt(password)) % PRIME);
        System.out.println(password);
        System.out.println(term);
        final long result = Stream.iterate(BigInteger.ZERO, i -> i.compareTo(BigInteger.valueOf(595033)) <= 0, BigInteger.ONE::add)
            .filter(i -> year.multiply(i).add(term).multiply(i).compareTo(BigInteger.ZERO) > 0)
            .mapToLong(i -> i.longValue() * password[i.intValue() % password.length])
            .sum();

        print(12, result, password);
    }



    private static final long MAX_DEPTH = 100_000_000L;
//    private static final long DEPTH = 0;
//    private static final long RESULT = 0;
//    private static void flag13(final byte[] password) {
//        try {
//            System.out.println("came in");
//            flag13(password, 0, 0);
//        } catch (final StackOverflowError e) {
//            System.out.println("Stack overflow :((");
//        }
//    }

    private static void flag13(final byte[] password) {
        long res = 0;

        for (long i = 0; i < MAX_DEPTH; i++) {
            res = (res ^ PRIME) | (res << 2) + i * 17;
        }
        print(13, res, password);
    }


    private static void flag14(final byte[] password) {
        final Instant today = Instant.parse("2024-09-10T12:00:00Z");
        final BigInteger hours = BigInteger.valueOf(Duration.between(Instant.EPOCH, today).toHours());

        final long result = Stream.iterate(BigInteger.ZERO, i -> i.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) <= 0, BigInteger.ONE::add)
            .map(hours::multiply)
            .reduce(BigInteger.ZERO, BigInteger::add)
            .longValue();

        print(14, result, password);
    }

    private static void flag15(final byte[] password) {
        print(15, 3519786268141933778L + password[3], password);
    }

    private static void flag16(final byte[] password) {
        byte[] a = {
            (byte) (password[0] + password[3]),
            (byte) (password[1] + password[4]),
            (byte) (password[2] + password[5])
        };

//        for (long i = 20; i >= 0; i--) {
//            flag16Update(a);
//            System.out.println(new String(a));
//        }

        print(16, flag16Result(a), password);
    }

    /* package-private */ static void flag16Update(byte[] a) {
        a[0] ^= a[1];
        a[1] += a[2] | a[0];
        a[2] *= a[0];
    }

    /* package-private */ static int flag16Result(byte[] a) {
        return (a[0] + " " + a[1] + " " + a[2]).hashCode();
    }

    private static void flag17(final byte[] password) {
        final int n = Math.abs(getInt(password) % 2024) + 2024;
        print(17, calc17(n), password);
    }

    /**
     * Write me
     * <pre>
     *     // i - int
     *    0: iconst_0               //Кладет на стек 0. (инит счетчика)
     *    1: istore_1               //Снимает значение с вершины стека и записывает в локальную переменную под индексом 1 (val_1 = 0)
     *    2: iload_1                //Записывает на вершину стека значение из val_1
     *    3: bipush        25       //Кладет значение 25 в тип байт на стек
     *    5: idiv                   //(val_1/25)
     *    6: iload_0                //Записывает на вершину стека значение из переменной под индексом 0 (n)
     *    7: isub                   //Вычитание (n - val_1/25)
     *    8: ifge          17       //если  n - val_1/25 ≥ 0 переходит на 17
     *   11: iinc          1, 1     //Увеличить локальную переменную (int) на константу - val_1++
     *   14: goto          2        // безусловный переход на 2
     *   17: iload_1                //Записывает на вершину стека значение из val_1
     *   18: ireturn                //Возвращает значение со стека из метода
     * </pre>
     */

    private static int calc17(int n) {
        int count;
        for (count = 0; count / 25 - n < 0; count++) {}
        System.out.println(count);
        return count;
    }

    private static void flag18(final byte[] password) {
        final int n = 2024 + getInt(password) % 2024;
        // Find the number of factors of n! modulo PRIME
        long a = countFactors(n);
        System.out.println(a);
        final long factors = a % PRIME;
        System.out.println(factors);
        if (factors != 0) {
            print(18, factors, password);
        }
    }
    static void sieve(int n, int prime[])
    {
        // Initialize all numbers as prime
        for (int i = 1; i <= n; i++)
            prime[i] = 1;

        // Mark composites
        prime[1] = 0;
        for (int i = 2; i * i <= n; i++) {
            if (prime[i] == 1) {
                for (int j = i * i; j <= n; j += i)
                    prime[j] = 0;
            }
        }
    }

    // Returns the highest exponent of p in n!
    static int expFactor(int n, int p)
    {
        int x = p;
        int exponent = 0;
        while ((n / x) > 0) {
            exponent += n / x;
            x *= p;
        }
        return exponent;
    }

    // Returns the no of factors in n!
    static long countFactors(int n)
    {
        // ans stores the no of factors in n!
        long ans = 1;

        // Find all primes upto n
        int prime[] = new int[n + 1];
        sieve(n, prime);

        // Multiply exponent (of primes) added with 1
        for (int p = 1; p <= n; p++) {

            // if p is a prime then p is also a
            // prime factor of n!
            if (prime[p] == 1)
                ans = (ans*((expFactor(n, p) + 1) % PRIME)) % PRIME;
        }

        return ans;
    }


    private static void flag19(final byte[] password) {
        // Let n = 2024 * 10**24 + getInt(password).
        // Consider the sequence of numbers (n + i) ** 2.
        // Instead of each number, we write the number that is obtained from it by discarding the last 24 digits.
        // How many of the first numbers of the resulting sequence will form an arithmetic progression?
        final long result = 0;
        if (result != 0) {
            print(19, result, password);
        }
    }

    private static void flag20(final byte[] password) {
        final Collection<Long> longs = new Random(getInt(password)).longs(1_000_000)
            .map(n -> n % 1000)
            .boxed()
            .collect(Collectors.toCollection(LinkedList::new));

        // Calculate the number of objects (recursively) accessible by "longs" reference.
        final int result = 1_000_000;
        if (result != 0) {
            print(20, result, password);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------
    // You may ignore all code below this line.
    // It is not required to get any of the flags.
    // ---------------------------------------------------------------------------------------------------------------

    private static void print(final int no, long result, final byte[] password) {
        System.out.format("flag %d: https://www.kgeorgiy.info/courses/prog-intro/hw1/%s%n", no, flag(result, password));
    }

    /* packge-private */ static String flag(long result, byte[] password) {
        final byte[] flag = password.clone();
        for (int i = 0; i < 6; i++) {
            flag[i] ^= result;
            result >>>= 8;
        }

        return flag(SALT, flag);
    }

    /* package-private */ static String flag(final byte[] salt, final byte[] data) {
        DIGEST.update(salt);
        DIGEST.update(data);
        DIGEST.update(salt);
        final byte[] digest = DIGEST.digest();

        return IntStream.range(0, 6)
            .map(i -> (((digest[i * 2] & 255) << 8) + (digest[i * 2 + 1] & 255)) % KEYWORDS.size())
            .mapToObj(KEYWORDS::get)
            .collect(Collectors.joining("-"));
    }

    /* package-private */ static byte[] parseArgs(final String[] args) {
        if (args.length != 6) {
            throw error("Expected 6 command line arguments, found: %d", args.length);
        }

        final byte[] bytes = new byte[args.length];
        for (int i = 0; i < args.length; i++) {
            final Byte value = VALUES.get(args[i].toLowerCase(Locale.US));
            if (value == null) {
                throw error("Expected keyword, found: %s", args[i]);
            }
            bytes[i] = value;
        }
        return bytes;
    }

    private static AssertionError error(final String format, final Object... args) {
        System.err.format(format, args);
        System.err.println();
        System.exit(1);
        throw new AssertionError();
    }

    /* package-private */ static int getInt(byte[] password) {
        return IntStream.range(0, password.length)
            .map(i -> password[i])
            .reduce((a, b) -> a * KEYWORDS.size() + b)
            .getAsInt();
    }

    private static final MessageDigest DIGEST;
    static {
        try {
            DIGEST = MessageDigest.getInstance("SHA-256");
        } catch (final NoSuchAlgorithmException e) {
            throw new AssertionError("Cannot create SHA-256 digest", e);
        }
    }

    private static final byte[] SALT = "raceipkebrAdLenEzSenickTejtainulhoodrec6".getBytes(StandardCharsets.US_ASCII);

    private static final List<String> KEYWORDS = List.of(
        "abstract",
        "assert",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "new",
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "strictfp",
        "super",
        "for",
        "goto",
        "if",
        "implements",
        "import",
        "instanceof",
        "int",
        "interface",
        "long",
        "native",
        "continue",
        "default",
        "do",
        "double",
        "else",
        "enum",
        "extends",
        "final",
        "finally",
        "float",
        "switch",
        "synchronized",
        "this",
        "throw",
        "throws",
        "transient",
        "try",
        "void",
        "volatile",
        "while",
        "record",
        "Error",
        "AssertionError",
        "OutOfMemoryError",
        "StackOverflowError",
        "ArrayIndexOutOfBoundsException",
        "ArrayStoreException",
        "AutoCloseable",
        "Character",
        "CharSequence",
        "ClassCastException",
        "Comparable",
        "Exception",
        "IllegalArgumentException",
        "IllegalStateException",
        "IndexOutOfBoundsException",
        "Integer",
        "Iterable",
        "Math",
        "Module",
        "NegativeArraySizeException",
        "NullPointerException",
        "Number",
        "NumberFormatException",
        "Object",
        "Override",
        "RuntimeException",
        "StrictMath",
        "String",
        "StringBuilder",
        "StringIndexOutOfBoundsException",
        "SuppressWarnings",
        "System",
        "Thread",
        "Throwable",
        "ArithmeticException",
        "ClassLoader",
        "ClassNotFoundException",
        "Cloneable",
        "Deprecated",
        "FunctionalInterface",
        "InterruptedException",
        "Process",
        "ProcessBuilder",
        "Runnable",
        "SafeVarargs",
        "StackTraceElement",
        "Runtime",
        "ThreadLocal",
        "UnsupportedOperationException"
    );

    private static final Map<String, Byte> VALUES = IntStream.range(0, KEYWORDS.size())
        .boxed()
        .collect(Collectors.toMap(index -> KEYWORDS.get(index).toLowerCase(Locale.US), Integer::byteValue));
}
