package co.edu.unicartagena.Clases;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase que representa un registro información bancaria para un usuario.
 *
 * @author Pablo Jose Hernandez Melendez
 */
public class Record {
    /**
     * Cédula del usuario.
     */
    private final String cc;

    /**
     * Capital inicial del usuario.
     */
    private BigDecimal capital;

    /**
     * Interés del usuario.
     */
    private BigDecimal interestTax;

    /**
     * Día en que se depositó el capital.
     */
    private short day;

    /**
     * Constructor de la clase.
     *
     * @param cc          cédula del usuario.
     * @param capital     capital inicial del usuario.
     * @param interestTax interés del usuario.
     * @param day         día en que se depositó el capital.
     */
    public Record(String cc, BigDecimal capital, BigDecimal interestTax, short day) {
        checkCC(cc);
        checkCapital(capital);
        checkInterest(interestTax);
        checkDay(day);

        this.cc = cc;
        this.capital = capital.setScale(2, RoundingMode.DOWN);
        this.interestTax = interestTax;
        this.day = day;
    }

    /**
     * Verifica que la cédula ingresada sea válida.
     *
     * @param cc cédula del usuario.
     * @throws java.lang.IllegalArgumentException Si la cédula no tiene 10 dígitos o si no son números.
     */
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

    /**
     * Verifica que el capital ingresado sea válido.
     *
     * @param capital capital inicial del usuario.
     * @throws java.lang.IllegalArgumentException Si el capital ingresado es menor a 0.
     */
    public static void checkCapital(BigDecimal capital) throws IllegalArgumentException {
        if (capital.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("El capital debe ser mayor o igual a 0");
        }
    }

    /**
     * Verifica que el interés ingresado sea válido.
     *
     * @param interest interés del usuario.
     * @throws java.lang.IllegalArgumentException Si el interés ingresado es menor a 0.
     */
    public static void checkInterest(BigDecimal interest) throws IllegalArgumentException {
        if (interest.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException("El interés debe ser un número mayor o igual a 0");
        }
    }

    /**
     * Verifica que el día ingresado sea válido.
     *
     * @param day día en que se depositó el capital.
     * @throws java.lang.IllegalArgumentException Si el día ingresado es menor a 0 o mayor a 360.
     */
    public static void checkDay(short day) throws IllegalArgumentException {
        if (day < 0 || day > 360) {
            throw new IllegalArgumentException("El día debe ser un número entre 0 y 360");
        }
    }

    /**
     * Obtiene la cédula del usuario.
     *
     * @return Cédula del usuario.
     */
    public String getCc() {
        return cc;
    }

    /**
     * Obtiene el capital inicial del usuario.
     *
     * @return Capital inicial del usuario.
     */
    public BigDecimal getCapital() {
        return capital;
    }

    /**
     * Obtiene la tasa de interés del usuario.
     *
     * @return Interés del usuario.
     */
    public BigDecimal getInterestTax() {
        return interestTax;
    }

    /**
     * Obtiene el interés del usuario.
     *
     * @return Interés del usuario.
     */
    public BigDecimal getInterest() {
        return capital.multiply(interestTax).add(BigDecimal.valueOf(360).subtract(BigDecimal.valueOf(day)).divide(BigDecimal.valueOf(360), 10, RoundingMode.HALF_UP)).setScale(2, RoundingMode.UP);
    }

    /**
     * Obtiene el día en que se depositó el capital.
     *
     * @return Día en que se depositó el capital.
     */
    public short getDay() {
        return day;
    }

    /**
     * Actualiza los datos del registro.
     *
     * @param capital  Capital inicial del usuario.
     * @param interest Interés del usuario.
     * @param day      Día en que se depositó el capital.
     */
    public void update(BigDecimal capital, BigDecimal interest, short day) {
        this.capital = capital.setScale(2, RoundingMode.DOWN);
        this.interestTax = interest;
        this.day = day;
    }

    /**
     * Obtiene el formato utilizado para imprimir los registros.
     *
     * @return Formato utilizado para imprimir los registros.
     * @author Pablo José Hernández Meléndez
     */
    public static String getFormat() {
        return "%-10s  %-13s  %-13s  %-4s";
    }

    /**
     * Información del registro.
     *
     * @return Información del registro.
     * @see #getFormat() Formato utilizado para imprimir los registros.
     */
    public String toString() {
        return String.format("%-10s  %-13s  %-13s  %-4d", cc, "$%.2f".formatted(capital), "%.4f%%".formatted(interestTax.multiply(BigDecimal.valueOf(100))), day);
    }

    /**
     * Obtiene los datos del registro separados por punto y coma.
     *
     * @return String con los datos del registro.
     */
    public String getSimplifiedString() {
        return String.format("%s;%s;%d;%s", cc, capital.toPlainString(), day, interestTax.toPlainString());
    }
}
