package co.edu.unicartagena.Clases;

import co.edu.unicartagena.Estructuras.SimpleLinkedList;

/**
 * Lista enlazada simple para almacenar información de los usuarios.
 */
public class BankInfo extends SimpleLinkedList<Record> {
    private double totalCapital;
    private double totalInterest;

    /**
     * Constructor de la clase.
     */
    public BankInfo() {
        super();
        totalCapital = 0;
        totalInterest = 0;
    }

    /**
     * Obtiene el capital total registrado.
     *
     * @return Capital total.
     */
    public double getTotalCapital() {
        return totalCapital;
    }

    /**
     * Obtiene el interés total registrado.
     *
     * @return Interés total.
     */
    public double getTotalInterest() {
        return totalInterest;
    }

    /**
     * Agrega un registro a la lista.
     *
     * @param cc       Cédula del usuario.
     * @param capital  Capital inicial del usuario.
     * @param interest Interés del usuario.
     * @param day      Día del usuario.
     */
    public void add(String cc, double capital, float interest, short day) {
        add(new Record(cc, capital, interest, day));
        totalCapital += capital;
        totalInterest += interest * capital;
    }

    /**
     * Remueve el usuario de la lista.
     *
     * @param cc Cédula del usuario.
     */
    public void removeRecord(String cc) {
        var capital = getRecord(cc).getCapital();
        var interest = getRecord(cc).getInterest();
        deleteFirst(new Record(cc, 0, 0, (short) 0));
        totalCapital -= capital;
        totalInterest -= interest * capital;
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

    private Record handleGet(Node<Record> node, String cc) {
        if (!node.getValue().getCc().equals(cc)) {
            return handleGet(node.getNext(), cc);
        }

        return node.getValue();
    }

    public void updateRecord(String cc, double capital, float interest, short day) {
        var toUpdate = getRecord(cc);
        var capitalDiff = capital - toUpdate.getCapital();
        var interestDiff = interest - toUpdate.getInterest();

        totalCapital += capitalDiff;
        totalInterest += interestDiff * capital;

        toUpdate.update(capital, interest, day);
    }

    /**
     * Devuelve la lista como String.
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
                String.format("%.2f", totalCapital),
                String.format("%.2f", totalInterest),
                String.format(Record.getFormat(), "Cédula", "Capital", "Interés", "Día"),
                super.toString());
    }
}
