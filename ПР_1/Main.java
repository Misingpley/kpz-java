package ua.duikt.holoshchuk.pr1;

import org.apache.commons.lang3.StringUtils;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {

    public static String reverseText(String input) {
        return StringUtils.reverse(input);
    }

    public static void printGreeting() {
        System.out.println("Привіт зі збірки! Виконав студент групи ТЦР-31 Голощук Владислав.");
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        String jvmVersion = System.getProperty("java.version");
        String jvmVendor = System.getProperty("java.vendor");
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");

        System.out.println("JVM: " + jvmVersion + " (" + jvmVendor + ")");
        System.out.println("OS: " + osName + " (" + osArch + ")");

        String sample = "Java";
        String reversed = reverseText(sample);
        String capitalized = StringUtils.capitalize("конструювання програмного забезпечення");

        System.out.println("Reversed (Commons Lang): " + reversed);
        System.out.println("Capitalized (Commons Lang): " + capitalized);
        printGreeting();
    }
}
