package com.looigi.passwordjava;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final com.looigi.passwordjava.Log ourInstance = new com.looigi.passwordjava.Log();

    public static com.looigi.passwordjava.Log getInstance() {
        return ourInstance;
    }

    private Log() {
        File dir = new File(VariabiliGlobali.getInstance().getPercorsoDIR());
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void EliminaFileLog() {
        Utility.getInstance().EliminaFile(VariabiliGlobali.getInstance().getPercorsoDIR(), "Logs.txt");
    }

    public void ScriveLog(String RigaDebug) {
        if (VariabiliGlobali.getInstance().isAzionaDebug()) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
            String strDate = formatter.format(date);

            String Scritta = strDate + ": " + RigaDebug + '\n';
            Utility.getInstance().ScriveFile(VariabiliGlobali.getInstance().getPercorsoDIR(), "Logs.txt", Scritta);
        }
    }
}
