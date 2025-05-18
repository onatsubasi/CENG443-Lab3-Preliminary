import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    private static String fileName;
    public static void main(String[] args) throws IOException {
        fileName = args[0];
        int qNo = Integer.parseInt(args[1]);

        switch (qNo){
            case 1:
                answer1();
                break;
            case 2:
                answer2();
                break;
            case 3:
                answer3();
                break;
            case 4:
                answer4();
                break;
            case 5:
                answer5();
                break;
            case 6:
                answer6();
                break;
            case 7:
                answer7();
                break;
            default:
                System.out.println("Invalid question no.");
                break;
        }
    }

    private static void answer1() throws IOException {
        //1. What is the total quantity of products purchased by the customers whose their names
        //start with 'A'?
        int result = Files.lines(Path.of(fileName))
        .skip(1)
        .map(Entry::new)
        .filter(entry -> entry.name.startsWith("A"))
        .mapToInt(entry -> entry.breadQuantity + entry.eggQuantity + entry.milkQuantity + entry.potatoQuantity + entry.tomatoQuantity)
        .sum();
        System.out.println("result for question 1: " + result);
    }

    private static void answer2() throws IOException {
        //What was the price of most expensive product sold?
        double result = Files.lines(Path.of(fileName))
        .skip(1)
        .map(Entry::new)
        .flatMapToDouble(entry-> Stream.of(
            entry.breadQuantity > 0 ? entry.breadPrice : 0.0,
            entry.milkQuantity > 0 ? entry.milkPrice: 0.0,
            entry.eggQuantity > 0 ? entry.eggPrice: 0.0,
            entry.potatoQuantity > 0 ? entry.potatoPrice: 0.0,
            entry.tomatoQuantity > 0 ? entry.tomatoPrice: 0.0).mapToDouble(Double::doubleValue)
        )
        .max()
        .orElse(0.0);

        System.out.println("result for question 2: " + result);

    }
    private static void answer3() throws IOException {
        //3. What was the date of the highest paid purchase by a customer?
        String result = Files.lines(Path.of(fileName))
        .skip(1)
        .map(Entry::new)
        .max((entry1, entry2) -> Double.compare(entry1.breadQuantity * entry1.breadPrice
        + entry1.milkQuantity * entry1.milkPrice
        + entry1.eggQuantity * entry1.eggPrice
        + entry1.potatoQuantity * entry1.potatoPrice
        + entry1.tomatoQuantity * entry1.tomatoPrice,
        entry2.breadQuantity * entry2.breadPrice
        + entry2.milkQuantity * entry2.milkPrice
        + entry2.eggQuantity * entry2.eggPrice
        + entry2.potatoQuantity * entry2.potatoPrice
        + entry2.tomatoQuantity * entry2.tomatoPrice
         ))
        .map(entry -> entry.date)
        .orElse(null);

        System.out.println("result: " + result);

    }
    private static void answer4() throws IOException {
        //4. What was the most popular product before 2000, in terms of total number of purchases
        //whose include that item?
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            String mostPopularProduct = lines
                    .skip(1)
                    .map(Entry::new)
                    .filter(entry -> Integer.parseInt(entry.date.split("-")[0]) < 2000) // Filter entries before 2000
                    .flatMap(entry -> Stream.of(
                            entry.breadQuantity > 0 ? "Bread" : null,
                            entry.milkQuantity > 0 ? "Milk" : null,
                            entry.eggQuantity > 0 ? "Egg" : null,
                            entry.potatoQuantity > 0 ? "Potato" : null,
                            entry.tomatoQuantity > 0 ? "Tomato" : null))
                    .filter(Objects::nonNull) // Remove null values
                    .collect(Collectors.groupingBy(product -> product, Collectors.counting())) // Count occurrences
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue)) // Find the most popular product
                    .map(Map.Entry::getKey)
                    .orElse("No products found");

            System.out.println("Most popular product before 2000: " + mostPopularProduct);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
    private static void answer5() throws IOException {
        // What was the least popular product after and including 2000, in terms of total number
        //of items purchased?

        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            String leastPopularProduct = lines
                    .skip(1)
                    .map(Entry::new)
                    .filter(entry -> Integer.parseInt(entry.date.split("-")[0]) >= 2000) // Filter entries before 2000
                    .flatMap(entry -> Stream.of(
                            entry.breadQuantity > 0 ? "Bread" : null,
                            entry.milkQuantity > 0 ? "Milk" : null,
                            entry.eggQuantity > 0 ? "Egg" : null,
                            entry.potatoQuantity > 0 ? "Potato" : null,
                            entry.tomatoQuantity > 0 ? "Tomato" : null))
                    .filter(Objects::nonNull) // Remove null values
                    .collect(Collectors.groupingBy(product -> product, Collectors.counting())) // Count occurrences
                    .entrySet()
                    .stream()
                    .min(Comparator.comparingLong(Map.Entry::getValue)) // Find the most popular product
                    .map(Map.Entry::getKey)
                    .orElse("No products found");

            System.out.println("Least popular product after 2000: " + leastPopularProduct);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private static void answer6() throws IOException {
        // Find the product with the smallest average customer age
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            String productWithSmallestAvgAge = lines
                    .skip(1)
                    .map(Entry::new)
                    .flatMap(entry -> Stream.of(
                            entry.breadQuantity > 0 ? Map.entry("Bread", entry.age) : null,
                            entry.milkQuantity > 0 ? Map.entry("Milk", entry.age) : null,
                            entry.eggQuantity > 0 ? Map.entry("Egg", entry.age) : null,
                            entry.potatoQuantity > 0 ? Map.entry("Potato", entry.age) : null,
                            entry.tomatoQuantity > 0 ? Map.entry("Tomato", entry.age) : null))
                    .filter(Objects::nonNull) // Remove null values
                    .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingInt(Map.Entry::getValue))) // Group by product and calculate average age
                    .entrySet()
                    .stream()
                    .min(Comparator.comparingDouble(Map.Entry::getValue)) // Find the product with the smallest average age
                    .map(Map.Entry::getKey)
                    .orElse("No products found");

            System.out.println("Product with the smallest average customer age: " + productWithSmallestAvgAge);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    private static void answer7() throws IOException {
        // Find the most inflated product based on the oldest and newest purchase prices
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            String mostInflatedProduct = lines
                    .skip(1)
                    .map(Entry::new)
                    .flatMap(entry -> Stream.of(
                            Map.entry("Bread", new AbstractMap.SimpleEntry<>(entry.date, entry.breadPrice)),
                            Map.entry("Milk", new AbstractMap.SimpleEntry<>(entry.date, entry.milkPrice)),
                            Map.entry("Egg", new AbstractMap.SimpleEntry<>(entry.date, entry.eggPrice)),
                            Map.entry("Potato", new AbstractMap.SimpleEntry<>(entry.date, entry.potatoPrice)),
                            Map.entry("Tomato", new AbstractMap.SimpleEntry<>(entry.date, entry.tomatoPrice))
                    ))
                    .collect(Collectors.groupingBy(
                            Map.Entry::getKey,
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    prices -> {
                                        if (prices.isEmpty()) return 0.0;
                                        
                                        // Find oldest and newest prices
                                        AbstractMap.SimpleEntry<String, Double> oldest = prices.stream()
                                                .map(Map.Entry::getValue)
                                                .min(Comparator.comparing(AbstractMap.SimpleEntry::getKey))
                                                .orElse(null);
                                        AbstractMap.SimpleEntry<String, Double> newest = prices.stream()
                                                .map(Map.Entry::getValue)
                                                .max(Comparator.comparing(AbstractMap.SimpleEntry::getKey))
                                                .orElse(null);
                                        
                                        if (oldest == null || newest == null || oldest.getValue() == 0.0) return 0.0;
                                        
                                        // Calculate inflation percentage
                                        return ((newest.getValue() - oldest.getValue()) / oldest.getValue()) * 100;
                                    }
                            )
                    ))
                    .entrySet()
                    .stream()
                    .max(Comparator.comparingDouble(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse("No products found");

            System.out.println("Most inflated product: " + mostInflatedProduct);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }





}
