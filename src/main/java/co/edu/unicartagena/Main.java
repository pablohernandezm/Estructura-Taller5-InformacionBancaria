package co.edu.unicartagena;

import co.edu.unicartagena.Clases.BankInfo;
import co.edu.unicartagena.Clases.Record;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Sistema de administración bancaria con interfaz de clientes basado en listas simples enlazadas
 *
 * @author Pablo Jose Hernandez Melendez
 * @see co.edu.unicartagena.Estructuras.SimpleLinkedList
 * @see co.edu.unicartagena.Clases.BankInfo
 * @see co.edu.unicartagena.Clases.Record
 */
public class Main {
    /**
     * Scanner para leer datos de la consola.
     */
    private static final Scanner sc = new Scanner(System.in);
    /**
     * Lista enlazada simple para almacenar información de los registros del banco.
     */
    private static final BankInfo bi = new BankInfo();

    /**
     * Método principal del programa.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        menu:
        do {
            cleanConsole();
            System.out.print("""
                    INFORMACIÓN BANCARIA
                    1.  Número de clientes.
                    2.  Total de capital depositado.
                    3.  Total intereses a pagar.
                    4.  Adicionar nuevo usuario.
                    5.  Dar de baja a usuarios.
                    6.  Actualizar usuarios.
                    7.  Buscar usuarios (por cédula).
                    8.  Listar los usuarios.
                    9.  Guardar lista en un archivo
                    10. Obtener Lista desde un archivo.
                    11.  Salir
                                        
                    Opción:""");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1 -> execute(() -> System.out.println("Número de clientes: " + bi.getSize()), false);
                case 2 ->
                        execute(() -> System.out.println("Total capital depositado: " + String.format("$%.2f", bi.getTotalCapital())), false);
                case 3 ->
                        execute(() -> System.out.println("Total intereses a pagar: " + String.format("$%.2f", bi.getTotalInterest())), false);
                case 4 -> execute(Main::addUser, false);
                case 5 -> execute(Main::removeUser, true);
                case 6 -> execute(Main::updateUser, true);
                case 7 -> execute(Main::searchUser, true);
                case 8 -> execute(() -> System.out.println(bi), true);
                case 9 -> execute(Main::saveRecords, true);
                case 10 -> execute(Main::loadRecords, false);
                case 11 -> {
                    break menu;
                }
            }
        } while (true);
    }

    /**
     * Método para encapsular de operaciones.
     *
     * @param action Acción a ejecutar.
     */
    private static void execute(Runnable action, boolean needsUser) {
        System.out.println("-".repeat(10));
        if (needsUser && bi.getSize() == 0) {
            System.out.print("No hay registros en la lista. Por favor ingrese alguno para proceder con la operación.");
            sc.nextLine();
            return;
        }

        action.run();

        System.out.println("-".repeat(10));
        System.out.print("Presione enter para continuar...");
        sc.nextLine();
    }

    /**
     * Maneja los errores de entrada.
     *
     * @param e Excepción lanzada.
     * @return Si se desea volver a intentar.
     */
    private static boolean handleInputError(Exception e) {
        System.out.println(e.getMessage());
        System.out.print("¿Desea volver a intentarlo? (s/n): ");
        String option = sc.nextLine();
        System.out.println();
        return option.equalsIgnoreCase("s");
    }

    /**
     * Envuelve la entrada de datos en un ciclo para manejar los errores.
     *
     * @param action Acción a ejecutar.
     * @return Si la entrada fue válida.
     */
    private static boolean inputWrapper(Runnable action) {
        cleanConsole();
        while (true) {
            try {
                action.run();
                break;
            } catch (InputMismatchException | IllegalArgumentException e) {
                if (!handleInputError(e)) {
                    return false;
                }

            }
        }

        return true;
    }

    /**
     * Añade un registro a la lista.
     */
    private static void addUser() {
        AtomicReference<BigDecimal> capital = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> interest = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<Short> day = new AtomicReference<>((short) 0);
        AtomicReference<String> cc = new AtomicReference<>("");

        AtomicReference<String> stateOfMenu = new AtomicReference<>("AÑADIR USUARIO");

        Runnable[] tareas = {
                () -> {
                    System.out.println(stateOfMenu.get());
                    System.out.print("Ingrese la cédula: ");
                    var x = sc.nextLine().trim();
                    Record.checkCC(x);
                    if (bi.getRecord(x) != null) {
                        throw new IllegalArgumentException("Ya existe un usuario con esa cédula.");
                    }

                    cc.set(x);
                    stateOfMenu.set(stateOfMenu.get() +
                            "\nCédula: %s".formatted(cc.get()));
                },
                () -> {
                    System.out.println(stateOfMenu.get());
                    System.out.print("Ingrese el capital depositado: ");
                    var x = new BigDecimal(sc.nextLine().replace(",", ".").trim());

                    Record.checkCapital(x);
                    capital.set(x);

                    stateOfMenu.set(stateOfMenu.get() +
                            "\nCapital: $%.2f".formatted(capital.get()));
                },
                () -> {
                    System.out.println(stateOfMenu.get());
                    System.out.print("""
                            La tasa de interés se debe colocar en términos de decimales (0.1, 0.2, etc... estos son equivalentes a 10%, 20%, y así sucesivamente),
                            Ingrese la tasa de interés:""");
                    var x = new BigDecimal(sc.nextLine().replace(",", ".").trim());

                    Record.checkInterest(x);
                    interest.set(x);
                    stateOfMenu.set(stateOfMenu.get() +
                            "\nTasa de interés: %.4f".formatted(interest.get()));
                },
                () -> {
                    System.out.println(stateOfMenu.get());
                    System.out.print("Ingrese el día: ");
                    var x = Short.parseShort(sc.nextLine().replace(",", ".").trim());
                    Record.checkDay(x);
                    day.set(x);
                }
        };

        for (var tarea : tareas) {
            var valid = inputWrapper(tarea);
            if (!valid) {
                return;
            }
        }

        bi.add(cc.get(), capital.get(), interest.get(), day.get());
        System.out.println("\nEl usuario ha sido añadido a los registros.");
    }

    /**
     * Remueve un registro de la lista.
     */
    private static void removeUser() {
        AtomicReference<String> cc = new AtomicReference<>("");
        var valid = inputWrapper(() -> {
            System.out.println("Ingrese la cédula del usuario a eliminar: ");
            var x = sc.nextLine();
            Record.checkCC(x);
            cc.set(x);
        });

        if (!valid) {
            return;
        }

        try {
            bi.removeRecord(cc.get());
            System.out.println("\nEl usuario ha sido eliminado con éxito.");
        } catch (NullPointerException e) {
            if (handleInputError(e)) {
                cleanConsole();
                removeUser();
            }
        }


    }

    /**
     * Actualiza un registro de la lista.
     */
    private static void updateUser() {
        AtomicReference<String> cc = new AtomicReference<>("");
        AtomicReference<BigDecimal> capital = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> interest = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<Short> day = new AtomicReference<>((short) 0);

        AtomicReference<Record> record = new AtomicReference<>(null);

        var valid = inputWrapper(() -> {
            System.out.print("Ingrese la cédula a actualizar: ");
            var x = sc.nextLine();

            Record.checkCC(x);
            record.set(bi.getRecord(x));

            if (record.get() == null) {
                throw new IllegalArgumentException("No existe un usuario con esa cédula.");
            }

            cc.set(x);
        });

        if (!valid) {
            return;
        }

        inputWrapper(() -> {
            System.out.println("ESTADO ACTUAL DEL USUARIO");
            System.out.printf((Record.getFormat()) + "%n", "Cédula", "Capital", "Interés", "Día");
            System.out.println(record.get());
            System.out.println();

            System.out.print("""
                    ¿Qué desea actualizar?
                    1. Capital
                    2. Interés
                    3. Día
                    4. Todo
                    Nota: Si desea seleccionar más de una acción escriba los números separados por comas(ejemplo: 1, 2).
                                        
                    Opción:""");

            var option = sc.nextLine().replaceAll(" ", "");
            var options = option.split(",");
            byte optionCount = 0;

            for (var o : options) {
                if (!o.matches("[1-4]")) {
                    throw new IllegalArgumentException("Opción inválida.");
                }

                if (o.equals("4")) {
                    optionCount = 3;
                    break;
                } else {
                    optionCount++;
                }
            }

            Runnable[] tasks = new Runnable[optionCount];
            var all = optionCount == 3;
            byte i = 0;

            if (!all) {
                if (!option.contains("1")) {
                    capital.set(record.get().getCapital());
                }
                if (!option.contains("2")) {
                    interest.set(record.get().getInterestTax());
                }
                if (!option.contains("3")) {
                    day.set(record.get().getDay());
                }
            }

            if (all || option.contains("1")) {
                tasks[i] = () -> {
                    System.out.print("Ingrese el nuevo capital: ");
                    var x = new BigDecimal(sc.nextLine().replace(",", ".").trim());

                    Record.checkCapital(x);
                    capital.set(x);
                };
                i++;
            }

            if (all || option.contains("2")) {
                tasks[i] = () -> {
                    System.out.print("Ingrese la nueva tasa de interés: ");
                    var x = new BigDecimal(sc.nextLine().replace(",", ".").trim());

                    Record.checkInterest(x);
                    interest.set(x);
                };
                i++;
            }

            if (all || option.contains("3")) {
                tasks[i] = () -> {
                    System.out.print("Ingrese el nuevo día: ");
                    var x = sc.nextShort();

                    Record.checkDay(x);
                    day.set(x);
                };
            }

            for (var task : tasks) {
                inputWrapper(task);
            }
        });

        bi.updateRecord(cc.get(), capital.get(), interest.get(), day.get());
        System.out.println("\nEl usuario ha sido actualizado con éxito.");
    }

    /**
     * Busca un registro de la lista.
     */
    private static void searchUser() {
        AtomicReference<String> cc = new AtomicReference<>("");
        inputWrapper(() -> {
            System.out.print("Ingrese la cédula del usuario a buscar: ");
            var x = sc.nextLine();
            Record.checkCC(x);
            cc.set(x);
        });

        var record = bi.getRecord(cc.get());
        if (record == null) {
            System.out.println("No existe un usuario con esa cédula.");
        } else {
            System.out.println(record);
        }
    }

    /**
     * Guarda los registros en un archivo.
     *
     * @see co.edu.unicartagena.Main#openFileChooser(String, Consumer)
     */
    private static void saveRecords() {
        var valid = openFileChooser("Guardar registros", file -> {
            Path path = Paths.get(file.getAbsolutePath());
            try {
                bi.save(path);
                System.out.println("El archivo se ha guardado con éxito.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        if (!valid) {
            System.out.print("El archivo no se ha guardado.");
        }
    }

    /**
     * Carga los registros desde un archivo.
     *
     * @see co.edu.unicartagena.Main#openFileChooser(String, Consumer)
     */
    private static void loadRecords() {
        var valid = openFileChooser("Cargar registros", file -> {
            Path path = Paths.get(file.getAbsolutePath());
            try {
                bi.load(path);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        if (!valid) {
            System.out.println("No se pudo cargar el archivo.");
        }
    }

    /**
     * Abre un JFileChooser para seleccionar un archivo.
     *
     * @param title  Título del JFileChooser
     * @param action Acción a ejecutar con el archivo seleccionado.
     */
    private static boolean openFileChooser(String title, Consumer<File> action) {
        boolean valid = true;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        JDialog dialog = new JDialog((Frame) null, title);
        dialog.setAlwaysOnTop(true);

        JFileChooser fc = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        fc.setFileFilter(filter);

        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));

        var option = fc.showSaveDialog(dialog);
        if (option == JFileChooser.APPROVE_OPTION) {
            var file = fc.getSelectedFile();
            action.accept(file);
        } else {
            valid = false;
        }

        dialog.dispose();
        return valid;
    }

    /**
     * Limpia la consola o imprime espacios para aparentar una consola limpia.
     */
    private static void cleanConsole() {
        try {
            // windows
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // unix o linux
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

        } catch (Exception ignore) {
        } finally {
            System.out.print("\n".repeat(100));
        }
    }
}
