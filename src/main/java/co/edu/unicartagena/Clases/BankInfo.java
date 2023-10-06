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
     * @return Capital total.
     */
    public double getTotalCapital() {
        return totalCapital;
    }

    /**
     * Obtiene el interés total registrado.
     * @return Interés total.
     */
    public double getTotalInterest() {
        return totalInterest;
    }

    /**
     * Agrega un registro a la lista.
     * @param cc Cédula del usuario.
     * @param capital Capital inicial del usuario.
     * @param interest Interés del usuario.
     * @param day Día del usuario.
     */
    public void add(String cc, float capital, float interest, short day) {
        add(new Record(cc, capital, interest, day));
        totalCapital += capital;
        totalInterest += interest;
    }

    /**
     * Remueve el usuario de la lista.
     * @param cc Cédula del usuario.
     */
    public void removeRecord(String cc) {
        deleteFirst(new Record(cc, 0, 0, (short) 0));
    }

    /**
     * Obtiene el registro de la lista.
     * @param cc Cédula del usuario.
     */
    public Record getRecord(String cc) {
        return getFirst(new Record(cc, 0, 0, (short) 0));
    }

    /**
     * Actualiza el registro de la lista.
     * @param record Registro a actualizar.
     */
    public void updateRecord(Record record){
        var toUpdate = getRecord(record.getCc());
        var capitalDiff = record.getCapital() - toUpdate.getCapital();
        var interestDiff = record.getInterest() - toUpdate.getInterest();
        totalCapital += capitalDiff;
        totalInterest += interestDiff;
        toUpdate.setCapital(record.getCapital());
        toUpdate.setInterest(record.getInterest());
        toUpdate.setDay(record.getDay());
    }
}
