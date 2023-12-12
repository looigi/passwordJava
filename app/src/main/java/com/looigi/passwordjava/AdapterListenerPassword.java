package com.looigi.passwordjava;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.looigi.passwordjava.strutture.StrutturaPassword;

import java.util.List;

public class AdapterListenerPassword extends BaseAdapter {
    Context context;
    List<StrutturaPassword> listaPassword;
    LayoutInflater inflter;

    public AdapterListenerPassword(Context applicationContext, List<StrutturaPassword> Password) {
        this.context = context;
        this.listaPassword = Password;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return listaPassword.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.lista_password, null);

        ImageView imgModifica = (ImageView) view.findViewById(R.id.imgModifica);
        imgModifica.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().setModalitaEdit("MODIFICA");
                VariabiliGlobali.getInstance().getLayPassword().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getTxtId().setText(Integer.toString(listaPassword.get(i).getIdRiga()));
                VariabiliGlobali.getInstance().getEdtSito().setText(listaPassword.get(i).getSito());
                VariabiliGlobali.getInstance().getEdtUtenza().setText(listaPassword.get(i).getUtenza());
                VariabiliGlobali.getInstance().getEdtPassword().setText(listaPassword.get(i).getPassword());
                VariabiliGlobali.getInstance().getEdtNote().setText(listaPassword.get(i).getNote());
                VariabiliGlobali.getInstance().getEdtIndirizzo().setText(listaPassword.get(i).getIndirizzo());
            }
        });

        ImageView imgElimino = (ImageView) view.findViewById(R.id.imgElimina);
        imgElimino.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Messaggio = "Si vuole eliminare la password selezionata (id " + Integer.toString(listaPassword.get(i).getIdRiga()) + ") ?";

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.context).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(Messaggio);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            db_dati db = new db_dati();
                            db.EliminaPassword(Integer.toString(listaPassword.get(i).getIdRiga()), true);

                            db.LeggePasswords();

                            Utility.getInstance().RiempieArrayLista();

                            dialog.dismiss(); //<-- change it with ur code
                        }
                    });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Annulla",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                alertDialog.show();
            }
        });

        TextView txtIdRiga = (TextView) view.findViewById(R.id.txtIdRiga);
        txtIdRiga.setText(Integer.toString(listaPassword.get(i).getIdRiga()));

        TextView txtSito = (TextView) view.findViewById(R.id.txtSito);
        txtSito.setText(listaPassword.get(i).getSito());

        TextView txtUtenza = (TextView) view.findViewById(R.id.txtUtenza);
        txtUtenza.setText(listaPassword.get(i).getUtenza());

        TextView txtPassword = (TextView) view.findViewById(R.id.txtPassword);
        txtPassword.setText(listaPassword.get(i).getPassword());

        TextView txtNote = (TextView) view.findViewById(R.id.txtNote);
        txtNote.setText(listaPassword.get(i).getNote());

        TextView txtIndirizzo = (TextView) view.findViewById(R.id.txtIndirizzo);
        txtIndirizzo.setText(listaPassword.get(i).getIndirizzo());

        txtIndirizzo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Indirizzo = listaPassword.get(i).getIndirizzo();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Indirizzo));
                MainActivity.context.startActivity(browserIntent);
            }
        });

        return view;
    }
}
