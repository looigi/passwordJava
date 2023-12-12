package com.looigi.passwordjava;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class Utility {
    private ProgressDialog progressDialog;

    private static final Utility ourInstance = new Utility();

    public static Utility getInstance() {
        return ourInstance;
    }

    private Utility() {
    }

    public String PrendeErroreDaException(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));

        return TransformError(errors.toString());
    }

    private String TransformError(String error) {
        String Return = error;

        if (Return.length() > 250) {
            Return = Return.substring(0, 247) + "...";
        }
        Return = Return.replace("\n", " ");

        return Return;
    }

    public Integer ScriveFile(String Path, String fileName, String CosaScrivere) {
        try {
            File newFolder = new File(Path);
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
            try {
                File file = new File(Path, fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }

                Writer out = new BufferedWriter(new FileWriter(file, true), 1024);
                out.write(CosaScrivere);
                out.close();

                return 0;
            } catch (Exception ex) {
                // System.out.println("ex: " + ex);
                return -1;
            }
        } catch (Exception e) {
            // System.out.println("e: " + e);
            return -2;
        }
    }

    public boolean EliminaFile(String Path, String fileName) {
        File file = new File(Path + "/" + fileName);
        return file.delete();
    }

    public void ChiudeDialog() {
        try {
            progressDialog.dismiss();
        } catch (Exception ignored) {
        }
    }

    public void ApriDialog(boolean ApriDialog, String tOperazione) {
        if (!ApriDialog) {
            // OggettiAVideo.getInstance().getImgRest().setVisibility(LinearLayout.VISIBLE);
        } else {
            try {
                progressDialog = new ProgressDialog(VariabiliGlobali.getInstance().getActivity());
                progressDialog.setMessage("Attendere Prego...\n\n" + tOperazione);
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
            } catch (Exception e) {
                VisualizzaMessaggio(PrendeErroreDaException(e));
            }
        }
    }

    public void VisualizzaMessaggio(String Messaggio) {
        /* VariabiliGlobali.getInstance().getActivity().runOnUiThread(new Runnable() {
            public void run() { */
                AlertDialog alertDialog = new AlertDialog.Builder(VariabiliGlobali.getInstance().getActivity()).create();
                alertDialog.setTitle("Messaggio");
                alertDialog.setMessage(Messaggio);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            // }
        // });
    }

    public void RiempieArrayLista() {
        AdapterListenerPassword customAdapterT = new AdapterListenerPassword(VariabiliGlobali.getInstance().getActivity(),
                VariabiliGlobali.getInstance().getListaPassword());
        VariabiliGlobali.getInstance().getLstPassword().setAdapter(customAdapterT);

        VariabiliGlobali.getInstance().getTxtQuante().setText("Rilevate:\n" + Integer.toString(VariabiliGlobali.getInstance().getListaPassword().size()));
    }
}
