package co.edu.unicartagena.Clases;

/**
 * Clase que representa un registro de un usuario para la información bancaria.
 */
public class Record {
    private String cc;
    private double capital;
    private float interest;
    private short day;

    public Record(String cc, double capital, float interest, short day) {
        checkCC(cc);
        checkCapital(capital);
        checkInterest(interest);
        checkDay(day);

        this.cc = cc;
        this.capital = capital;
        this.interest = interest;
        this.day = day;
    }

    public static void checkCC(String cc) throws IllegalArgumentException {
        if (cc.length() != 10) {
            throw new IllegalArgumentException("La cédula debe tener 10 dígitos. Ingresaste %d de 10 dígitos.".formatted(cc.length()));
        }

        for (var c : cc.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("La cédula debe contener solo dígitos");
            }
        }
    }

    public static void checkCapital(double capital) throws IllegalArgumentException {
        if (capital < 0) {
            throw new IllegalArgumentException("El capital debe ser mayor o igual a 0");
        }
    }

    public static void checkInterest(float interest) throws IllegalArgumentException {
        if (interest < 0) {
            throw new IllegalArgumentException("El interés debe ser un número mayor o igual a 0");
        }
    }

    public static void checkDay(short day) throws IllegalArgumentException {
        if (day < 0 || day > 365) {
            throw new IllegalArgumentException("El día debe ser un número entre 0 y 365");
        }
    }

    public String getCc() {
        return cc;
    }

    public double getCapital() {
        return capital;
    }

    public float getInterest() {
        return interest;
    }

    public short getDay() {
        return day;
    }

    public void setCapital(double capital) {
        checkCapital(capital);
        this.capital = capital;
    }

    public void setDay(short day) {
        checkDay(day);
        this.day = day;
    }

    public void setInterest(float interest) {
        checkInterest(interest);
        this.interest = interest;
    }

    public void update(double capital, float interest, short day){
        this.capital = capital;
        this.interest = interest;
        this.day = day;
    }

    public boolean equals(Record record) {
        return this.cc.equals(record.getCc());
    }

    public static String getFormat() {
        return "%-10s  %-13s  %-13s  %-4s";
    }

    public String toString() {
        return String.format("%-10s  %-13s  %-13s  %-4d", cc, "$%.2f".formatted(capital), "%.4f%%".formatted(interest), day);
    }
}
