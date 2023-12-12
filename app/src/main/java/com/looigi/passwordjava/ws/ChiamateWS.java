package com.looigi.passwordjava.ws;

import android.widget.LinearLayout;

import com.looigi.passwordjava.Log;
import com.looigi.passwordjava.MainActivity;
import com.looigi.passwordjava.db_dati;
import com.looigi.passwordjava.strutture.StrutturaPassword;
import com.looigi.passwordjava.Utility;
import com.looigi.passwordjava.VariabiliGlobali;
import com.looigi.passwordjava.strutture.StrutturaUtente;

import java.util.ArrayList;
import java.util.List;

public class ChiamateWS implements TaskDelegate {
    private com.looigi.passwordjava.ws.LetturaWSAsincrona bckAsyncTask;

    // http://looigi.ddns.net:1040/Service1.asmx
    private final String RadiceWS = VariabiliGlobali.getInstance().getUrlWS() + "/";
    private String ws = "Service1.asmx/";
    private String NS="http://passWS.it/";
    private String SA="http://passWS.it/";
    private String TipoOperazione = "";

    public void EliminaPassword(String idRiga) {
        String Urletto="EliminaPassword?" +
                "idUtente=" + VariabiliGlobali.getInstance().getIdUtente() + "&" +
                "idRiga=" + idRiga;
        boolean ApriDialog = true;
        TipoOperazione = "EliminaPassword";

        Esegue(
                RadiceWS + ws + Urletto,
                TipoOperazione,
                NS,
                SA,
                15000,
                ApriDialog);
    }

    public void ModificaPassword(StrutturaPassword s) {
        String Urletto="ModificaPassword?" +
                "idUtente=" + VariabiliGlobali.getInstance().getIdUtente() + "&" +
                "idRiga=" + s.getIdRiga() + "&" +
                "Sito=" + s.getSito() + "&" +
                "Utenza=" + s.getUtenza() + "&" +
                "Password=" + s.getPassword() + "&" +
                "Notelle=" + s.getNote() + "&" +
                "Indirizzo=" + s.getIndirizzo();
        boolean ApriDialog = true;
        TipoOperazione = "ModificaPassword";

        Esegue(
                RadiceWS + ws + Urletto,
                TipoOperazione,
                NS,
                SA,
                15000,
                ApriDialog);
    }

    public void ScriveNuovaPassword(StrutturaPassword s) {
        String Urletto="ScriveNuovaPassword?" +
                "idUtente=" + VariabiliGlobali.getInstance().getIdUtente() + "&" +
                "Sito=" + s.getSito() + "&" +
                "Utenza=" + s.getUtenza() + "&" +
                "Password=" + s.getPassword() + "&" +
                "Notelle=" + s.getNote() + "&" +
                "Indirizzo=" + s.getIndirizzo();
        boolean ApriDialog = true;
        TipoOperazione = "ScriveNuovaPassword";

        Esegue(
                RadiceWS + ws + Urletto,
                TipoOperazione,
                NS,
                SA,
                15000,
                ApriDialog);
    }

    public void RitornaPasswordLogin(String NickName) {
        String Urletto="RitornaPassword?" +
                "Nick=" + NickName;
        boolean ApriDialog = true;
        TipoOperazione = "RitornaPassword";

        Esegue(
                RadiceWS + ws + Urletto,
                TipoOperazione,
                NS,
                SA,
                15000,
                ApriDialog);
    }

    public void RitornaIdUtente(String NickName) {
        String Urletto="RitornaIDUtente?" +
                "Nick=" + NickName;
        boolean ApriDialog = true;
        TipoOperazione = "RitornaIDUtente";

        Esegue(
                RadiceWS + ws + Urletto,
                TipoOperazione,
                NS,
                SA,
                15000,
                ApriDialog);
    }

    public void SalvaNuovoUtente(StrutturaUtente s) {
        String Urletto="InserisceUtente?" +
                "Nick=" + s.getNick() + "&" +
                "Nome=" + s.getNome() + "&" +
                "Cognome=" + s.getCognome() + "&" +
                "Password=" + s.getPassword() + "&" +
                "Sesso=M&" +
                "Eta=0&" +
                "EMail=xx@xx.x&" +
                "Datella=1972-02-26&" +
                "Nazione=Italia";
        boolean ApriDialog = true;
        TipoOperazione = "InserisceUtente";

        Esegue(
                RadiceWS + ws + Urletto,
                TipoOperazione,
                NS,
                SA,
                15000,
                ApriDialog);
    }

    public void CaricaPassword() {
        String Urletto="RitornaStringaPassword?" +
                "idUtente=" + VariabiliGlobali.getInstance().getIdUtente() + "&" +
                "Stringa=" + VariabiliGlobali.getInstance().getRicerca();
        boolean ApriDialog = true;
        TipoOperazione = "RitornaStringaPassword";

        Esegue(
                RadiceWS + ws + Urletto,
                TipoOperazione,
                NS,
                SA,
                15000,
                ApriDialog);
    }

    public void Esegue(String Urletto, String tOperazione,
                       String NS, String SOAP_ACTION, int Timeout,
                       boolean ApriDialog) {

        Long tsLong = System.currentTimeMillis()/1000;
        String TimeStampAttuale = tsLong.toString();

        bckAsyncTask = new com.looigi.passwordjava.ws.LetturaWSAsincrona(
                NS,
                Timeout,
                SOAP_ACTION,
                tOperazione,
                ApriDialog,
                Urletto,
                TimeStampAttuale,
                this);
        bckAsyncTask.execute(Urletto);
    }

    @Override
    public void TaskCompletionResult(String result) {
        Log.getInstance().ScriveLog("Ritorno WS " + TipoOperazione + ". OK");

        switch (TipoOperazione) {
            case "EliminaPassword":
                fEliminaPassword(result);
                break;
            case "ModificaPassword":
                fModificaPassword(result);
                break;
            case "ScriveNuovaPassword":
                fScriveNuovaPassword(result);
                break;
            case "RitornaIDUtente":
                fRitornaIDUtente(result);
                break;
            case "RitornaPassword":
                fRitornaPassword(result);
                break;
            case "InserisceUtente":
                fInserisceUtente(result);
                break;
            case "RitornaStringaPassword":
                fRitornaStringaPassword(result);
                break;
        }
    }

    public void StoppaEsecuzione() {
        bckAsyncTask.cancel(true);
    }

    private void fEliminaPassword(String result) {
        if (result.contains("ERROR:")) {
            Utility.getInstance().VisualizzaMessaggio(result);
        } else {
        }
    }

    private void fModificaPassword(String result) {
        if (result.contains("ERROR:")) {
            Utility.getInstance().VisualizzaMessaggio(result);
        } else {
        }
    }

    private void fScriveNuovaPassword(String result) {
        if (result.contains("ERROR:")) {
            Utility.getInstance().VisualizzaMessaggio(result);
        } else {
        }
    }

    private void fRitornaPassword(String result) {
        if (result.contains("ERROR:")) {
            Utility.getInstance().VisualizzaMessaggio(result);
        } else {
            if (result.equals(VariabiliGlobali.getInstance().getPasswordAppoggio())) {
                ChiamateWS ws = new ChiamateWS();
                ws.RitornaIdUtente(VariabiliGlobali.getInstance().getNomeUtenteAppoggio());
            } else {
                Utility.getInstance().VisualizzaMessaggio("Password non valida");
            }
        }
    }

    private void fRitornaIDUtente(String result) {
        if (result.contains("ERROR:")) {
            Utility.getInstance().VisualizzaMessaggio(result);
        } else {
            if (result.equals("-1")) {
                Utility.getInstance().VisualizzaMessaggio("Utente non valido");
            } else {
                if (result.equals("-2")) {
                    Utility.getInstance().VisualizzaMessaggio("Utente non presente");
                } else {
                    String[] campi = result.split(";");
                    StrutturaUtente s = new StrutturaUtente();
                    s.setIdUtente(Integer.parseInt(campi[0]));
                    s.setNick(campi[1]);
                    s.setNome(campi[2]);
                    s.setCognome(campi[3]);
                    s.setPassword(campi[4]);

                    VariabiliGlobali.getInstance().setIdUtente(s.getIdUtente());
                    db_dati db = new db_dati();
                    db.SalvaUtente(s, false);

                    VariabiliGlobali.getInstance().setNomeUtenteAppoggio("");
                    VariabiliGlobali.getInstance().setPasswordAppoggio("");

                    VariabiliGlobali.getInstance().getLaySceltaPassword().setVisibility(LinearLayout.GONE);

                    VariabiliGlobali.getInstance().setLoginEffettuata(true);
                }
            }
        }
    }

    private void fInserisceUtente(String result) {
        if (result.contains("ERROR:")) {
            Utility.getInstance().VisualizzaMessaggio(result);
        } else {
            VariabiliGlobali.getInstance().setIdUtente(Integer.parseInt(result));
            VariabiliGlobali.getInstance().setUtenteAttuale(VariabiliGlobali.getInstance().getStrutturaUtenteAppoggio());

            db_dati db = new db_dati();
            db.SalvaUtente(VariabiliGlobali.getInstance().getStrutturaUtenteAppoggio(), false);

            VariabiliGlobali.getInstance().getLayNuovoUtente().setVisibility(LinearLayout.GONE);
            VariabiliGlobali.getInstance().getLaySceltaPassword().setVisibility(LinearLayout.GONE);
            VariabiliGlobali.getInstance().setStrutturaUtenteAppoggio(null);
        }
    }

    private void fRitornaStringaPassword(String result) {
        if (result.contains("ERROR:")) {
            Utility.getInstance().VisualizzaMessaggio(result);
        } else {
            // Caricamento password
            db_dati db = new db_dati();
            List<StrutturaPassword> lista = new ArrayList<StrutturaPassword>();
            String[] righe = result.split("°");
            for (int q = 0; q < righe.length; q++) {
                // if (righe[q].isEmpty()) {
                try {
                    String[] campi = righe[q].split("§");
                    StrutturaPassword s = new StrutturaPassword();
                    s.setIdRiga(Integer.parseInt(campi[0]));
                    s.setSito(campi[1]);
                    s.setUtenza(campi[2]);
                    s.setPassword(campi[3]);
                    s.setNote(campi[4]);
                    s.setIndirizzo(campi[5]);

                    lista.add(s);

                    if (VariabiliGlobali.getInstance().isDeveAggiungereRigheAlDb()) {
                        db.SalvaPassword(s, false);
                    }
                } catch (Exception ignored) {

                }
                // }
            };
            VariabiliGlobali.getInstance().setListaPassword(lista);
            VariabiliGlobali.getInstance().setDeveAggiungereRigheAlDb(false);

            Utility.getInstance().RiempieArrayLista();
        }
    }
}
