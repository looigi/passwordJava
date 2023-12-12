package com.looigi.passwordjava;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.looigi.passwordjava.strutture.StrutturaPassword;
import com.looigi.passwordjava.strutture.StrutturaUtente;
import com.looigi.passwordjava.ws.ChiamateWS;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class db_dati {
    private final String PathDB = VariabiliGlobali.getInstance().getPercorsoDIR()+"/DB/";
    private final SQLiteDatabase myDB;

    public db_dati() {
        File f = new File(PathDB);
        try {
            f.mkdirs();
        } catch (Exception ignored) {

        }
        myDB = ApreDB();
    }

    private SQLiteDatabase ApreDB() {
        SQLiteDatabase db = null;
        try {
            String nomeDB = "dati.db";
            db = VariabiliGlobali.getInstance().getContext().openOrCreateDatabase(
                    PathDB + nomeDB, MODE_PRIVATE, null);
        } catch (Exception e) {
            String Messaggio = "ERRORE Nell'apertura del db: " + Utility.getInstance().PrendeErroreDaException(e);
            Log.getInstance().ScriveLog(Messaggio);
            Utility.getInstance().VisualizzaMessaggio(Messaggio);
        }
        return  db;
    }

    public void CreazioneTabelle() {
        try {
            if (myDB != null) {
                String sql = "CREATE TABLE IF NOT EXISTS "
                        + "Password "
                        + " (idUtente VARCHAR, idRiga VARCHAR, Sito VARCHAR, Utenza VARCHAR, Password VARCHAR, Note VARCHAR, " +
                        "Indirizzo VARCHAR);";
                myDB.execSQL(sql);

                sql = "CREATE TABLE IF NOT EXISTS "
                        + "Utente "
                        + " (idUtente VARCHAR, Nick VARCHAR, Nome VARCHAR, Cognome VARCHAR, Password VARCHAR);";
                myDB.execSQL(sql);
            }
        } catch (Exception e) {
            String Messaggio = "ERRORE Nella creazione delle tabelle: " + Utility.getInstance().PrendeErroreDaException(e);
            Log.getInstance().ScriveLog(Messaggio);
            Utility.getInstance().VisualizzaMessaggio(Messaggio);
        }
    }

    public void SalvaUtente(StrutturaUtente s, boolean AggiungeOnLine) {
        try {
            String SQL = "SELECT * FROM Utente Where idUtente=" + s.getIdUtente();
            Cursor c = myDB.rawQuery(SQL, null);
            c.moveToFirst();
            if (c.getCount() > 0) {
                Log.getInstance().ScriveLog("Salvataggio Utente. Skippo " + s.getIdUtente() + "-" + s.getNick() + " in quanto già esistente");
            } else {
                SQL = "Insert Into Utente Values(" +
                        "'" + s.getIdUtente() + "', " +
                        "'" + s.getNick().replace("'", "''") + "', " +
                        "'" + s.getNome().replace("'", "''") + "', " +
                        "'" + s.getCognome().replace("'", "''") + "', " +
                        "'" + s.getPassword().replace("'", "''") + "' " +
                        ")";
                myDB.execSQL(SQL);

                if (AggiungeOnLine) {
                    ChiamateWS ws = new ChiamateWS();
                    ws.SalvaNuovoUtente(s);
                }
            }
        } catch (Exception e) {
            String Messaggio = "ERRORE Nel salvataggio dell'utente: " + Utility.getInstance().PrendeErroreDaException(e);
            Log.getInstance().ScriveLog(Messaggio);
            Utility.getInstance().VisualizzaMessaggio(Messaggio);
        }
    }

    public void EliminaPassword(String idRiga, boolean AggiungeOnLine) {
        try {
            String SQL = "Delete From Password Where idUtente=" + VariabiliGlobali.getInstance().getIdUtente() +
                    " And idRiga=" + idRiga;
            myDB.execSQL(SQL);

            if (AggiungeOnLine) {
                ChiamateWS ws = new ChiamateWS();
                ws.EliminaPassword(idRiga);
            }
        } catch (Exception e) {
            String Messaggio = "ERRORE Nell'eliminazione della password: " + Utility.getInstance().PrendeErroreDaException(e);
            Log.getInstance().ScriveLog(Messaggio);
            Utility.getInstance().VisualizzaMessaggio(Messaggio);
        }
    }

    public void ModificaPassword(StrutturaPassword s, boolean AggiungeOnLine) {
        try {
            String SQL = "Delete From Password Where idUtente=" + VariabiliGlobali.getInstance().getIdUtente() +
                    " And idRiga=" + s.getIdRiga();
            myDB.execSQL(SQL);

            SQL = "Insert Into Password Values(" +
                    "'" + VariabiliGlobali.getInstance().getIdUtente() + "', " +
                    "'" + s.getIdRiga() + "', " +
                    "'" + s.getSito().replace("'", "''") + "', " +
                    "'" + s.getUtenza().replace("'", "''") + "', " +
                    "'" + s.getPassword().replace("'", "''") + "', " +
                    "'" + s.getNote().replace("'", "''") + "', " +
                    "'" + s.getIndirizzo().replace("'", "''") + "' " +
                    ")";
            myDB.execSQL(SQL);

            if (AggiungeOnLine) {
                ChiamateWS ws = new ChiamateWS();
                ws.ModificaPassword(s);
            }
        } catch (Exception e) {
            String Messaggio = "ERRORE Nella modifica della password: " + Utility.getInstance().PrendeErroreDaException(e);
            Log.getInstance().ScriveLog(Messaggio);
            Utility.getInstance().VisualizzaMessaggio(Messaggio);
        }
    }

    public void SalvaPassword(StrutturaPassword s, boolean AggiungeOnLine) {
        try {
            int id = -1;

            String SQL = "SELECT Max(idRiga) FROM Password";
            Cursor c = myDB.rawQuery(SQL, null);
            c.moveToFirst();
            if (c.getCount() > 0) {
                id = c.getInt(0);

                SQL = "Insert Into Password Values(" +
                        "'" + VariabiliGlobali.getInstance().getIdUtente() + "', " +
                        "'" + id + "', " +
                        "'" + s.getSito().replace("'", "''") + "', " +
                        "'" + s.getUtenza().replace("'", "''") + "', " +
                        "'" + s.getPassword().replace("'", "''") + "', " +
                        "'" + s.getNote().replace("'", "''") + "', " +
                        "'" + s.getIndirizzo().replace("'", "''") + "' " +
                        ")";
                myDB.execSQL(SQL);

                if (AggiungeOnLine) {
                    ChiamateWS ws = new ChiamateWS();
                    ws.ScriveNuovaPassword(s);
                }
            } else {
                String Messaggio = "ERRORE Nel salvataggio della password: Id Riga non rilevato";
                Log.getInstance().ScriveLog(Messaggio);
                Utility.getInstance().VisualizzaMessaggio(Messaggio);
            }
        } catch (Exception e) {
            String Messaggio = "ERRORE Nel salvataggio della password: " + Utility.getInstance().PrendeErroreDaException(e);
            Log.getInstance().ScriveLog(Messaggio);
            Utility.getInstance().VisualizzaMessaggio(Messaggio);
        }
    }

    public boolean LeggeUtente() {
        String SQL = "SELECT * FROM Utente";
        Cursor c = myDB.rawQuery(SQL, null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            StrutturaUtente s = new StrutturaUtente();
            s.setIdUtente(Integer.parseInt(c.getString(0)));
            s.setNick(c.getString(1));
            s.setNome(c.getString(2));
            s.setCognome(c.getString(3));
            s.setPassword(c.getString(4));
            VariabiliGlobali.getInstance().setUtenteAttuale(s);
            VariabiliGlobali.getInstance().setIdUtente(s.getIdUtente());

            return true;
        } else {
            return false;
        }
    }

    public boolean LeggePasswords() {
        boolean Ritorno = false;

        String Altro = "";
        if (!VariabiliGlobali.getInstance().getRicerca().isEmpty()) {
            String Ricerca = VariabiliGlobali.getInstance().getRicerca().replace("'", "''");
            Altro = "And (Sito Like '%" + Ricerca + "%' Or Note Like '%" + Ricerca + "%' Or Indirizzo Like '%" + Ricerca + "%')";
        }
        String SQL = "SELECT * FROM Password Where idUtente=" + VariabiliGlobali.getInstance().getIdUtente() + " " + Altro;
        Log.getInstance().ScriveLog("Legge Passwords sul db: " + SQL);

        Cursor c = myDB.rawQuery(SQL, null);
        c.moveToFirst();
        List<StrutturaPassword> lista = new ArrayList<>();
        if (c.getCount() > 0) {
            do {
                StrutturaPassword s = new StrutturaPassword();
                s.setIdRiga(Integer.parseInt(c.getString(1)));
                s.setSito(c.getString(2));
                s.setUtenza(c.getString(3));
                s.setPassword(c.getString(4));
                s.setNote(c.getString(5));
                s.setIndirizzo(c.getString(6));
                lista.add(s);

                Ritorno = true;
            } while (c.moveToNext());
        }
        VariabiliGlobali.getInstance().setListaPassword(lista);

        return Ritorno;
    }
}
