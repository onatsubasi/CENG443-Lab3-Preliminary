public class Entry {
    public final String name;
    public final int age;
    public final String date;
    public final double breadPrice;
    public final int breadQuantity;
    public final double milkPrice;
    public final int milkQuantity;
    public final double eggPrice;
    public final int eggQuantity;
    public final double potatoPrice;
    public final int potatoQuantity;
    public final double tomatoPrice;
    public final int tomatoQuantity;

    public Entry(String line) {
        String[] parts = line.split(",",-1);
        name = isEmpty(parts[0]) ? null : parts[0];
        age = isEmpty(parts[1]) ? 0 : Integer.parseInt(parts[1]);
        date = isEmpty(parts[2]) ? null : parts[2];
        breadPrice = isEmpty(parts[3]) ? 0.0 : Double.parseDouble(parts[3]);
        breadQuantity = isEmpty(parts[4]) ? 0 : Integer.parseInt(parts[4]);
        milkPrice = isEmpty(parts[5]) ? 0.0 : Double.parseDouble(parts[5]);
        milkQuantity = isEmpty(parts[6]) ? 0 : Integer.parseInt(parts[6]);
        eggPrice = isEmpty(parts[7]) ? 0.0 : Double.parseDouble(parts[7]);
        eggQuantity = isEmpty(parts[8]) ? 0 : Integer.parseInt(parts[8]);
        potatoPrice = isEmpty(parts[9]) ? 0.0 : Double.parseDouble(parts[9]);
        potatoQuantity = isEmpty(parts[10]) ? 0 : Integer.parseInt(parts[10]);
        tomatoPrice = isEmpty(parts[11]) ? 0.0 : Double.parseDouble(parts[11]);
        tomatoQuantity = isEmpty(parts[12]) ? 0 : Integer.parseInt(parts[12]);
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    @Override
    public String toString() {
        return name + "," +
                age + "," +
                date + "," +
                breadPrice + "," +
                breadQuantity + "," +
                milkPrice + "," +
                milkQuantity + "," +
                eggPrice + "," +
                eggQuantity + "," +
                potatoPrice + "," +
                potatoQuantity + "," +
                tomatoPrice + "," +
                tomatoQuantity;
    }
}
