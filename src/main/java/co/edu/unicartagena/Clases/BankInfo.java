package co.edu.unicartagena.Clases;

import co.edu.unicartagena.Estructuras.SimpleLinkedList;

import java.math.BigDecimal;

/**
 * Lista enlazada simple para almacenar información de los registros de un banco.
 * @author Pablo José Hernández Meléndez
 */
public class BankInfo extends SimpleLinkedList<Record> {
    /**
     * Capital total registrado.
     */
    private BigDecimal totalCapital;

    /**
     * Interés total registrado.
     */
    private BigDecimal totalInterest;

    /**
     * Constructor de la clase.
     */
    public BankInfo() {
        super();
        totalCapital = new BigDecimal(0);
        totalInterest = new BigDecimal(0);
    }

    /**
     * Obtiene el capital total registrado.
     *
     * @return Capital total.
     */
    public BigDecimal getTotalCapital() {
        return totalCapital;
    }

    /**
     * Obtiene el interés total registrado.
     *
     * @return Interés total.
     */
    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    /**
     * Agrega un registro a la lista.
     *
     * @param cc          Cédula del usuario.
     * @param capital     Capital inicial del registro.
     * @param interestTax Tasa de interés del registro.
     * @param day         Día del usuario.
     */
    public void add(String cc, BigDecimal capital, BigDecimal interestTax, short day) {
        Record newRecord = new Record(cc, capital, interestTax, day);
        add(new Record(cc, capital, interestTax, day));
        totalCapital = totalCapital.add(capital);
        totalInterest = totalInterest.add(newRecord.getInterest());
    }

    /**
     * Remueve un registro de la lista.
     *
     * @param cc Cédula del usuario.
     */
    public void removeRecord(String cc) {
        var capital = getRecord(cc).getCapital();
        var interest = getRecord(cc).getInterest();
        deleteFirst(getRecord(cc));
        totalCapital = totalCapital.subtract(capital);
        totalInterest = totalInterest.subtract(interest);
    }

    /**
     * Obtiene el registro de la lista.
     *
     * @param cc Cédula del usuario.
     */
    public Record getRecord(String cc) {
        try {
            Record.checkCC(cc);
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            return handleGet(this.head, cc);
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Método recursivo para obtener un registro de la lista.
     *
     * @param node Nodo actual.
     * @param cc   Cédula del usuario.
     * @return Registro del usuario.
     */
    private Record handleGet(Node<Record> node, String cc) {
        if (!node.getValue().getCc().equals(cc)) {
            return handleGet(node.getNext(), cc);
        }

        return node.getValue();
    }

    /**
     * Actualiza un registro de la lista.
     *
     * @param cc          Cédula del usuario.
     * @param capital     Capital del usuario.
     * @param interestTax Tasa de interés del usuario.
     * @param day         Día en que el depósito fue realizado.
     */
    public void updateRecord(String cc, BigDecimal capital, BigDecimal interestTax, short day) {
        var toUpdate = getRecord(cc);
        var capitalDiff = capital.subtract(toUpdate.getCapital());

        totalCapital = totalCapital.add(capitalDiff);
        totalInterest = totalInterest.subtract(toUpdate.getInterest());

        toUpdate.update(capital, interestTax, day);
        totalInterest = totalInterest.add(toUpdate.getInterest());
    }

    /**
     * Devuelve la lista como String, mostrando el total del capital e interés acumulado.
     *
     * @return Lista como String.
     */
    @Override
    public String toString() {
        return """
                Capital total: $%s
                Interés total: $%s
                                
                %s
                %s
                """.formatted(
                String.format("%.2f", totalCapital.round(Record.context)),
                String.format("%.2f", totalInterest.round(Record.context)),
                String.format(Record.getFormat(), "Cédula", "Capital", "Interés", "Día"),
                super.toString());
    }
}
