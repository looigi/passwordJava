package com.looigi.passwordjava;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.looigi.passwordjava.strutture.StrutturaPassword;
import com.looigi.passwordjava.strutture.StrutturaUtente;

import java.util.List;

public class VariabiliGlobali {
    private static final VariabiliGlobali ourInstance = new VariabiliGlobali();

    public static VariabiliGlobali getInstance() {
        return ourInstance;
    }

    private VariabiliGlobali() {
    }

    private Context context;
    private Activity activity;
    private final String NomeApplicazione = "Password";
    private final String PercorsoDIR = Environment.getExternalStorageDirectory().getPath()+"/LooigiSoft/Password";
    private final String UrlWS = "http://looigi.ddns.net:1040";
    private boolean AzionaDebug = true;
    // private boolean RetePresente = true;
    private List<StrutturaPassword> listaPassword = null;
    private StrutturaUtente UtenteAttuale;
    private ListView lstPassword;
    private boolean deveAggiungereRigheAlDb = false;
    private String PasswordAppoggio = "";
    private String NomeUtenteAppoggio = "";
    private StrutturaUtente StrutturaUtenteAppoggio;
    private LinearLayout laySceltaPassword;
    private LinearLayout layNuovoUtente;

    private int idUtente = -1;
    private String Ricerca = "";

    private TextView txtQuante;

    private LinearLayout layPassword;
    private TextView txtId;
    private EditText edtSito;
    private EditText edtUtenza;
    private EditText edtPassword;
    private EditText edtNote;
    private EditText edtIndirizzo;
    private String modalitaEdit = "";

    private boolean LoginEffettuata = false;

    public boolean isLoginEffettuata() {
        return LoginEffettuata;
    }

    public void setLoginEffettuata(boolean loginEffettuata) {
        LoginEffettuata = loginEffettuata;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TextView getTxtId() {
        return txtId;
    }

    public void setTxtId(TextView txtId) {
        this.txtId = txtId;
    }

    public String getModalitaEdit() {
        return modalitaEdit;
    }

    public void setModalitaEdit(String modalitaEdit) {
        this.modalitaEdit = modalitaEdit;
    }

    public TextView getTxtQuante() {
        return txtQuante;
    }

    public void setTxtQuante(TextView txtQuante) {
        this.txtQuante = txtQuante;
    }

    public EditText getEdtUtenza() {
        return edtUtenza;
    }

    public void setEdtUtenza(EditText edtUtenza) {
        this.edtUtenza = edtUtenza;
    }

    public EditText getEdtPassword() {
        return edtPassword;
    }

    public void setEdtPassword(EditText edtPassword) {
        this.edtPassword = edtPassword;
    }

    public EditText getEdtNote() {
        return edtNote;
    }

    public void setEdtNote(EditText edtNote) {
        this.edtNote = edtNote;
    }

    public EditText getEdtIndirizzo() {
        return edtIndirizzo;
    }

    public void setEdtIndirizzo(EditText edtIndirizzo) {
        this.edtIndirizzo = edtIndirizzo;
    }

    public LinearLayout getLayPassword() {
        return layPassword;
    }

    public void setLayPassword(LinearLayout layPassword) {
        this.layPassword = layPassword;
    }

    public EditText getEdtSito() {
        return edtSito;
    }

    public void setEdtSito(EditText edtSito) {
        this.edtSito = edtSito;
    }

    public StrutturaUtente getStrutturaUtenteAppoggio() {
        return StrutturaUtenteAppoggio;
    }

    public void setStrutturaUtenteAppoggio(StrutturaUtente strutturaUtenteAppoggio) {
        StrutturaUtenteAppoggio = strutturaUtenteAppoggio;
    }

    public LinearLayout getLayNuovoUtente() {
        return layNuovoUtente;
    }

    public void setLayNuovoUtente(LinearLayout layNuovoUtente) {
        this.layNuovoUtente = layNuovoUtente;
    }

    public String getNomeUtenteAppoggio() {
        return NomeUtenteAppoggio;
    }

    public void setNomeUtenteAppoggio(String nomeUtenteAppoggio) {
        NomeUtenteAppoggio = nomeUtenteAppoggio;
    }

    public String getPasswordAppoggio() {
        return PasswordAppoggio;
    }

    public void setPasswordAppoggio(String passwordAppoggio) {
        PasswordAppoggio = passwordAppoggio;
    }

    public LinearLayout getLaySceltaPassword() {
        return laySceltaPassword;
    }

    public void setLaySceltaPassword(LinearLayout laySceltaPassword) {
        this.laySceltaPassword = laySceltaPassword;
    }

    public boolean isDeveAggiungereRigheAlDb() {
        return deveAggiungereRigheAlDb;
    }

    public void setDeveAggiungereRigheAlDb(boolean deveAggiungereRigheAlDb) {
        this.deveAggiungereRigheAlDb = deveAggiungereRigheAlDb;
    }

    public ListView getLstPassword() {
        return lstPassword;
    }

    public void setLstPassword(ListView lstPassword) {
        this.lstPassword = lstPassword;
    }

    public StrutturaUtente getUtenteAttuale() {
        return UtenteAttuale;
    }

    public void setUtenteAttuale(StrutturaUtente utenteAttuale) {
        UtenteAttuale = utenteAttuale;
    }

    public String getRicerca() {
        return Ricerca;
    }

    public List<StrutturaPassword> getListaPassword() {
        return listaPassword;
    }

    public void setListaPassword(List<StrutturaPassword> listaPassword) {
        this.listaPassword = listaPassword;
    }

    public void setRicerca(String ricerca) {
        Ricerca = ricerca;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    /* public boolean isRetePresente() {
        return RetePresente;
    }

    public void setRetePresente(boolean retePresente) {
        RetePresente = retePresente;
    } */

    public String getUrlWS() {
        return UrlWS;
    }

    public String getPercorsoDIR() {
        return PercorsoDIR;
    }

    public boolean isAzionaDebug() {
        return AzionaDebug;
    }

    public void setAzionaDebug(boolean azionaDebug) {
        AzionaDebug = azionaDebug;
    }
}
