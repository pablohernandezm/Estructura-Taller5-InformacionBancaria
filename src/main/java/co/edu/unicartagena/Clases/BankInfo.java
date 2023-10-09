package co.edu.unicartagena.Clases;

import co.edu.unicartagena.Estructuras.SimpleLinkedList;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lista enlazada simple para almacenar información de los registros de un banco.
 *
 * @author Pablo Jose Hernandez Melendez
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
        add(newRecord);
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
     * @return Registro.
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
     * {@inheritDoc}
     * <p>
     * Devuelve la lista como String, mostrando el total del capital e interés acumulado.
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

    /**
     * Devuelve una versión simplificada de la lista en un StringBuilder, con los datos de cada registro separados por punto y coma.
     *
     * @return Lista en un StringBuilder.
     * @see java.lang.StringBuilder
     */
    public StringBuilder getListSimplified() {
        StringBuilder sb = new StringBuilder();
        for (var node = head; node != null; node = node.getNext()) {
            sb.append(node.getValue().getSimplifiedString());
            sb.append("\n");

            if (!node.hasNext()) {
                break;
            }
        }

        return sb;
    }

    /**
     * Guarda los registros en un archivo.
     *
     * @param path Ruta del archivo.
     * @throws java.lang.Exception Cuando ocurre un error al guardar los registros en el archivo.
     */
    public void save(Path path) throws Exception {
        try {
            Files.writeString(path, getListSimplified());
        } catch (Exception e) {
            throw new Exception("Error al guardar los registros en el archivo.\nCausa: " + e.getMessage());
        }
    }

    /**
     * Carga los registros de un archivo.
     *
     * @param path Ruta del archivo.
     * @throws java.lang.Exception Cuando ocurre un error al cargar los registros del archivo.
     */
    public void load(Path path) throws Exception {
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger ignored = new AtomicInteger();

        try (var lines = Files.lines(path)) {
            lines.forEach(line -> {
                var data = line.split(";");

                try {
                    var capital = data[1].replace(",", ".");
                    var interest = data[3].replace(",", ".");

                    Record.checkCC(data[0]);
                    Record.checkCapital(new BigDecimal(capital));
                    Record.checkDay(Short.parseShort(data[2]));
                    Record.checkInterest(new BigDecimal(interest));
                } catch (IllegalArgumentException e) {
                    ignored.getAndIncrement();
                }

                add(data[0], new BigDecimal(data[1]), new BigDecimal(data[3]), Short.parseShort(data[2]));
                counter.getAndIncrement();
            });

            System.out.printf("Se cargaron %d registros.\n", counter.get());
            if (ignored.get() > 0) {
                System.out.printf("%d registros fueron ignorados porque no cumplían con el formato necesario.\n", ignored.get());
            }
        } catch (Exception e) {
            throw new Exception("Error al cargar los registros del archivo.\nCausa: " + e.getClass().getName()+": "+e.getCause());
        }
    }
}
