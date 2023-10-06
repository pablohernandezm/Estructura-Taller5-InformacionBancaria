package co.edu.unicartagena;

import co.edu.unicartagena.Clases.BankInfo;
import co.edu.unicartagena.Clases.Record;

public class Main {
    public static void main(String[] args) {
        BankInfo b = new BankInfo();
        b.add("1234567890", 1000, 0.1f, (short) 1);
        b.add("1234567891", 2000, 0.2f, (short) 2);

        //System.out.printf(Record.getFormat()+"\n", "Cédula", "Capital", "Interés", "Día");
        //System.out.println(b);
    }
}