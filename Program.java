import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

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
        Path path = Paths.get(fileName);
        int result = Files.lines(path)
        .skip(1)
        .map(Entry::new)
        .filter(entry-> entry.name.startsWith("A"))
        .reduce(0,(sum,entry)-> {
            return sum + entry.breadQuantity + entry.milkQuantity + entry.eggQuantity + entry.potatoQuantity + entry.tomatoQuantity;
            }, Integer::sum
            );
        
        System.out.println(result);
    }

    private static void answer2() throws IOException {
        //What was the price of most expensive product sold?
        double result = Files.lines(Path.of(fileName))
        .skip(1)
        .map(Entry::new)
        .reduce(0.0, (price, entry) -> {
            Double priceA = entry.breadQuantity > 0 ? entry.breadPrice : 0.0;
            Double priceB = entry.milkQuantity > 0 ? entry.milkPrice : 0.0;
            Double priceC = entry.eggQuantity > 0 ? entry.eggPrice : 0.0;
            Double priceD = entry.potatoQuantity > 0 ? entry.potatoPrice : 0.0;
            Double priceE = entry.tomatoQuantity > 0 ? entry.tomatoPrice : 0.0;
            return Math.max(price, Math.max(priceA, Math.max(priceB, Math.max(priceC, Math.max(priceD, priceE)))));
        },Double::max);
        System.out.println(result);
    }
    private static void answer3() throws IOException {
        //3. What was the date of the highest paid purchase by a customer?
        Path pathVariable = Paths.get(fileName);
        String result = Files.lines(pathVariable)
        .skip(1)
        .map(Entry::new)
        .reduce(
            new String[]{"", "0"},                             // identity (type: String[])
            (dateValue, entry) -> {                            // accumulator
                double totalPrice = entry.breadQuantity * entry.breadPrice
                                   + entry.milkQuantity * entry.milkPrice
                                   + entry.eggQuantity * entry.eggPrice
                                   + entry.potatoQuantity * entry.potatoPrice
                                   + entry.tomatoQuantity * entry.tomatoPrice;
    
                if (totalPrice > Double.parseDouble(dateValue[1])) {
                    dateValue[0] = entry.date;
                    dateValue[1] = String.valueOf(totalPrice);
                }
                return dateValue;
            },
            (arr1, arr2) -> Double.parseDouble(arr1[1]) > Double.parseDouble(arr2[1]) ? arr1 : arr2 // combiner
        )[0];

        System.out.println("result: " + result);
    }
    private static void answer4() throws IOException {
        //4. What was the most popular product before 2000, in terms of total number of purchases
        //whose include that item?


        int[] result = Files.lines(Path.of(fileName))
        .skip(1)
        .map(Entry::new)
        .filter(entry-> Integer.parseInt(entry.date.split("-")[0]) < 2000)
        .reduce(new int[]{0,0,0,0,0}, (arr, entry) -> 
        {
            arr[0] += entry.breadQuantity > 0 ? 1: 0;
            arr[1] += entry.milkQuantity > 0 ? 1: 0;
            arr[2] += entry.eggQuantity > 0 ? 1: 0;
            arr[3] += entry.potatoQuantity > 0 ? 1: 0;
            arr[4] += entry.tomatoQuantity > 0 ? 1: 0;
            return arr;
        }, (arr1,arr2) -> new int[]{arr1[0] + arr2[0], arr1[1] + arr2[1], arr1[2] + arr2[2],arr1[3] + arr2[3],arr1[4] + arr2[4]});

        System.out.println(intArrayToItemMax(result));



    }
    private static String intArrayToItemMax(int[] result)
    {
        int max = 0;
        int index = 0;
        for (int i=0; i< result.length; i++){
            int val = result[i];
            if (val > max)
            {
                index = i;
                max = val;
            }
        }

        return getItemName(index);
    }

    private static String intArrayToItemMin(int[] result)
    {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i=0; i< result.length; i++){
            int val = result[i];
            if (val < min)
            {
                index = i;
                min = val;
            }
        }

        return getItemName(index);
    }
    private static void answer5() throws IOException {
        // What was the least popular product after and including 2000, in terms of total number
        //of items purchased?
        int[] result = Files.lines(Path.of(fileName))
        .skip(1)
        .map(Entry::new)
        .filter(entry-> Integer.parseInt(entry.date.split("-")[0]) >= 2000)
        .reduce(new int[]{0,0,0,0,0}, (arr, entry) -> 
        {
            arr[0] += entry.breadQuantity;
            arr[1] += entry.milkQuantity;
            arr[2] += entry.eggQuantity;
            arr[3] += entry.potatoQuantity;
            arr[4] += entry.tomatoQuantity;
            return arr;
        }, (arr1,arr2) -> new int[]{arr1[0] + arr2[0], arr1[1] + arr2[1], arr1[2] + arr2[2],arr1[3] + arr2[3],arr1[4] + arr2[4]});

        // find the max value in the

        System.out.println(intArrayToItemMin(result));

    }

    private static void answer6() throws IOException {
        // Find the product with the smallest average customer age
        Path path = Paths.get(fileName);
        Integer[][] result = Files.lines(path)
        .skip(1)
        .map(Entry::new)
        .reduce(new Integer[][]{new Integer[]{0,0},new Integer[]{0,0}, new Integer[]{0,0}, new Integer[]{0,0}, new Integer[]{0,0}}
        , (arr,entry) -> {
            if(entry.breadQuantity > 0){
                arr[0][0] += entry.age;
                arr[0][1]++;
            }
            if(entry.milkQuantity > 0){
                arr[1][0] += entry.age;
                arr[1][1]++;
            }
            if(entry.eggQuantity > 0){
                arr[2][0] += entry.age;
                arr[2][1]++;
            }
            if(entry.potatoQuantity > 0){
                arr[3][0] += entry.age;
                arr[3][1]++;
            }
            if(entry.tomatoQuantity > 0){
                arr[4][0] += entry.age;
                arr[4][1]++;
            }
            return arr;
        },(arr1,arr2) -> arr1);
        double[] averages = {result[0][0] / result[0][1], result[1][0] / result[1][1],result[2][0] / result[2][1],result[3][0] / result[3][1],result[4][0] / result[4][1]};

        double min = Double.MAX_VALUE;
        int index = 0;
        for (int i=0; i< averages.length; i++){
            double val = averages[i];
            if (val < min)
            {
                index = i;
                min = val;
            }
        }
        System.out.println(getItemName(index));
    }
    private static String getItemName(int index)
    {
        switch (index) {
            case 0:
                return "Bread";
            case 1:
                return "Milk";
            case 2:
                return "Egg";
            case 3:
                return "Potato";
            case 4:
                return "Tomato";
            default:
                return null;
        }
    }

    private static void answer7() throws IOException {
        // Find the most inflated product based on the oldest and newest purchase prices
        double[] oldestPrice = {0, 0, 0, 0, 0};
        double[] newestPrice = {0, 0, 0, 0, 0};
        LocalDate oldestPossibleDate = LocalDate.of(1970, 1, 1);
        LocalDate newestPossibleDate = LocalDate.of(2020, 1, 1);
        LocalDate[] oldestDate = {newestPossibleDate, newestPossibleDate, newestPossibleDate, newestPossibleDate, newestPossibleDate};
        LocalDate[] newestDate = {oldestPossibleDate, oldestPossibleDate, oldestPossibleDate, oldestPossibleDate, oldestPossibleDate};

        Files.lines(Paths.get(fileName))
        .skip(1)
        .map(Entry::new)
        .forEach(entry -> {
            LocalDate date = parseDate(entry.date);
            if(entry.breadQuantity > 0){
                if (date.isAfter(oldestDate[0])) 
                {
                    oldestDate[0] = date;
                    oldestPrice[0] = entry.breadPrice;
                }
                if (date.isBefore(newestDate[0]))
                {
                    newestDate[0] = date;
                    newestPrice[0] = entry.breadPrice;
                }
            }
            if(entry.milkQuantity > 0){
                if (date.isAfter(oldestDate[1])) 
                {
                    oldestDate[1] = date;
                    oldestPrice[1] = entry.milkPrice;
                }
                if (date.isBefore(newestDate[1]))
                {
                    newestDate[1] = date;
                    newestPrice[1] = entry.milkPrice;
                }
            }
            if(entry.eggQuantity > 0){
                if (date.isAfter(oldestDate[2])) 
                {
                    oldestDate[2] = date;
                    oldestPrice[2] = entry.eggPrice;
                }
                if (date.isBefore(newestDate[2]))
                {
                    newestDate[2] = date;
                    newestPrice[2] = entry.eggPrice;
                }
            }
            if(entry.potatoQuantity > 0){
                if (date.isAfter(oldestDate[3])) 
                {
                    oldestDate[3] = date;
                    oldestPrice[3] = entry.potatoPrice;
                }
                if (date.isBefore(newestDate[3]))
                {
                    newestDate[3] = date;
                    newestPrice[3] = entry.potatoPrice;
                }
            }
            if(entry.tomatoQuantity > 0){
                if (date.isAfter(oldestDate[4])) 
                {
                    oldestDate[4] = date;
                    oldestPrice[4] = entry.tomatoPrice;
                }
                if (date.isBefore(newestDate[4]))
                {
                    newestDate[4] = date;
                    newestPrice[4] = entry.tomatoPrice;
                }
            }
        });

        double difference = Double.MIN_VALUE;
        int index = 0;

        for(int i =0; i < 5; i++){
            double dif = Math.abs(newestPrice[i] - oldestPrice[i]);
            if (dif > difference){
                difference = dif;
                index = i;
            }
        }

        System.out.println(getItemName(index));
    }

    private static LocalDate parseDate(String date)
    {
        String[] datee = date.split("-");
        return LocalDate.of(Integer.parseInt(datee[0]), Integer.parseInt(datee[1]), Integer.parseInt(datee[2]) );
    }





}
