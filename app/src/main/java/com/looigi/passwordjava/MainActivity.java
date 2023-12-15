package com.looigi.passwordjava;


import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.looigi.passwordjava.strutture.StrutturaPassword;
import com.looigi.passwordjava.strutture.StrutturaUtente;
import com.looigi.passwordjava.ws.ChiamateWS;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    private boolean ciSonoPermessi;
    public static Context context;
    private Runnable rAgg;
    private Handler handlerAgg;
    private Runnable rAgg2;
    private Handler handlerAgg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        VariabiliGlobali.getInstance().setContext(getApplicationContext());
        VariabiliGlobali.getInstance().setActivity(this);

        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
            );
            startActivityForResult(intent, 123);
        } */

        Permessi p = new Permessi();
        ciSonoPermessi = p.ControllaPermessi();
        if (ciSonoPermessi) {
            Inizio();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (!ciSonoPermessi) {
            int index = 0;
            Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
            for (String permission : permissions) {
                PermissionsMap.put(permission, grantResults[index]);
                index++;
            }

            Log.getInstance().ScriveLog("Eseguo entrata per esecuzione permessi");
            Inizio();
        }
    }

    private void Inizio() {
        // VariabiliGlobali.getInstance().setRetePresente(true);

        Log.getInstance().ScriveLog("Apro db");
        db_dati db = new db_dati();
        Log.getInstance().ScriveLog("Creo tabelle");
        db.CreazioneTabelle();

        LinearLayout laySceltaPWD = (LinearLayout) findViewById(R.id.laySceltaPassword);
        laySceltaPWD.setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().setLaySceltaPassword(laySceltaPWD);

        LinearLayout layNuovoUtente = (LinearLayout) findViewById(R.id.layNuovoUtente);
        layNuovoUtente.setVisibility(LinearLayout.GONE);
        VariabiliGlobali.getInstance().setLayNuovoUtente(layNuovoUtente);

        VariabiliGlobali.getInstance().setLayPassword(findViewById(R.id.layEditPassword));
        VariabiliGlobali.getInstance().getLayPassword().setVisibility(LinearLayout.GONE);

        ImageView layRicerca = (ImageView) findViewById(R.id.imgRicerca);
        layRicerca.setVisibility(LinearLayout.GONE);

        EditText edtUtente = (EditText) findViewById(R.id.edtUtenteLogin);
        EditText edtPassword = (EditText) findViewById(R.id.edtPasswordLogin);

        boolean utenza = db.LeggeUtente();
        if (utenza) {
            edtUtente.setText(VariabiliGlobali.getInstance().getUtenteAttuale().getNick());
        } else {
            edtUtente.setText("");
        }
        utenza = false;

        if (!utenza) {
            // Non esiste l'utente sul db
            laySceltaPWD.setVisibility(LinearLayout.VISIBLE);

            ImageView imgLogin = (ImageView) findViewById(R.id.imgLogin);
            imgLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String NomeUtente = edtUtente.getText().toString();
                    String Password = edtPassword.getText().toString();

                    VariabiliGlobali.getInstance().setNomeUtenteAppoggio(NomeUtente);
                    VariabiliGlobali.getInstance().setPasswordAppoggio(Password);

                    ChiamateWS ws = new ChiamateWS();
                    ws.RitornaPasswordLogin(NomeUtente);
                }
            });

            ImageView imgNuovoUtente = (ImageView) findViewById(R.id.imgNuovoUtente);
            imgNuovoUtente.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliGlobali.getInstance().getLayNuovoUtente().setVisibility(LinearLayout.VISIBLE);

                    ImageView imgAnnullaNuovo = (ImageView) findViewById(R.id.imgAnnullaNuovo);
                    imgAnnullaNuovo.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            VariabiliGlobali.getInstance().getLayNuovoUtente().setVisibility(LinearLayout.GONE);
                        }
                    });

                    ImageView imgSalvaNuovo = (ImageView) findViewById(R.id.imgSalvaNuovo);
                    imgSalvaNuovo.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            EditText edtNick = (EditText) findViewById(R.id.edtUtenteNuovo);
                            EditText edtNome = (EditText) findViewById(R.id.edtNomeNuovo);
                            EditText edtCognome = (EditText) findViewById(R.id.edtCognomeNuovo);
                            EditText edtPassword = (EditText) findViewById(R.id.edtPasswordNuovo);
                            String Nick = edtNick.getText().toString();
                            String Nome = edtNome.getText().toString();
                            String Cognome = edtCognome.getText().toString();
                            String Password = edtPassword.getText().toString();

                            StrutturaUtente s = new StrutturaUtente();
                            s.setNick(Nick);
                            s.setNome(Nome);
                            s.setCognome(Cognome);
                            s.setPassword(Password);

                            VariabiliGlobali.getInstance().setStrutturaUtenteAppoggio(s);

                            ChiamateWS ws = new ChiamateWS();
                            ws.SalvaNuovoUtente(s);
                        }
                    });
                }
            });

            ImageView imgAnnullaLogin = (ImageView) findViewById(R.id.imgAnnullaLogin);
            imgAnnullaLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                    System.exit(1);
                }
            });

            VariabiliGlobali.getInstance().setLoginEffettuata(false);
            handlerAgg2 = new Handler();
            rAgg2 = new Runnable() {
                public void run() {
                    if (!VariabiliGlobali.getInstance().isLoginEffettuata()) {
                        handlerAgg2.postDelayed(rAgg2, 100);
                    } else {
                        ContinuaEsecuzione();
                    }
                }
            };
            handlerAgg2.postDelayed(rAgg2, 100);
        } else {
            // Login eliminato
            ContinuaEsecuzione();
        }

        ImageView imgSplash = (ImageView) findViewById(R.id.imgSplash);
        imgSplash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imgSplash.setVisibility(LinearLayout.GONE);
            }
        });
        handlerAgg = new Handler();
        rAgg = new Runnable() {
            public void run() {
                imgSplash.setVisibility(LinearLayout.GONE);
            }
        };
        handlerAgg.postDelayed(rAgg, 3000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.getInstance().ScriveLog("Premuto uscita");
            finish();
            System.exit(1);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void ContinuaEsecuzione() {
        db_dati db = new db_dati();

        ImageView layRicerca = (ImageView) findViewById(R.id.imgRicerca);
        layRicerca.setVisibility(LinearLayout.VISIBLE);

        ListView lstPassword = (ListView) findViewById(R.id.lstPassword);
        VariabiliGlobali.getInstance().setLstPassword(lstPassword);

        VariabiliGlobali.getInstance().setTxtQuante(findViewById(R.id.txtQuante));

        VariabiliGlobali.getInstance().setTxtId(findViewById(R.id.txtIdEdit));
        VariabiliGlobali.getInstance().setEdtSito(findViewById(R.id.edtSito));
        VariabiliGlobali.getInstance().setEdtUtenza(findViewById(R.id.edtUtenza));
        VariabiliGlobali.getInstance().setEdtPassword(findViewById(R.id.edtPassword));
        VariabiliGlobali.getInstance().setEdtNote(findViewById(R.id.edtNote));
        VariabiliGlobali.getInstance().setEdtIndirizzo(findViewById(R.id.edtIndirizzo));

        ImageView imgSalvaPWD = (ImageView) findViewById(R.id.imgSalvaPassword);
        imgSalvaPWD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db_dati db = new db_dati();
                StrutturaPassword s = new StrutturaPassword();
                s.setSito(VariabiliGlobali.getInstance().getEdtSito().getText().toString());
                s.setUtenza(VariabiliGlobali.getInstance().getEdtUtenza().getText().toString());
                s.setPassword(VariabiliGlobali.getInstance().getEdtPassword().getText().toString());
                s.setNote(VariabiliGlobali.getInstance().getEdtNote().getText().toString());
                s.setIndirizzo(VariabiliGlobali.getInstance().getEdtIndirizzo().getText().toString());

                if (VariabiliGlobali.getInstance().getModalitaEdit().equals("INSERT")) {
                    db.SalvaPassword(s, true);
                } else {
                    s.setIdRiga(Integer.parseInt(VariabiliGlobali.getInstance().getTxtId().getText().toString()));
                    db.ModificaPassword(s, true);
                }

                db.LeggePasswords();

                Utility.getInstance().RiempieArrayLista();

                VariabiliGlobali.getInstance().setModalitaEdit("");
                VariabiliGlobali.getInstance().getLayPassword().setVisibility(LinearLayout.GONE);
            }
        });

        ImageView imgChiudeEditPWD = (ImageView) findViewById(R.id.imgAnnullaEdit);
        imgChiudeEditPWD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().getLayPassword().setVisibility(LinearLayout.GONE);
            }
        });

        if (db.LeggePasswords()) {
            // Utility.getInstance().RiempieArrayLista();
        } else {
            VariabiliGlobali.getInstance().setRicerca("");
            VariabiliGlobali.getInstance().setDeveAggiungereRigheAlDb(true);

            ChiamateWS ws = new ChiamateWS();
            ws.CaricaPassword();
        }

        ImageView imgAggiunge = (ImageView) findViewById(R.id.imgAggiunge);
        imgAggiunge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VariabiliGlobali.getInstance().setModalitaEdit("INSERT");
                VariabiliGlobali.getInstance().getLayPassword().setVisibility(LinearLayout.VISIBLE);
                VariabiliGlobali.getInstance().getTxtId().setText("");
                VariabiliGlobali.getInstance().getEdtSito().setText("");
                VariabiliGlobali.getInstance().getEdtUtenza().setText("");
                VariabiliGlobali.getInstance().getEdtPassword().setText("");
                VariabiliGlobali.getInstance().getEdtNote().setText("");
                VariabiliGlobali.getInstance().getEdtIndirizzo().setText("");
            }
        });

        EditText edtRicerca = (EditText) findViewById(R.id.edtRicerca);
        ImageView imgRicerca = (ImageView) findViewById(R.id.imgRicerca);
        imgRicerca.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Ricerca = edtRicerca.getText().toString();
                VariabiliGlobali.getInstance().setRicerca(Ricerca);

                db_dati db = new db_dati();
                db.LeggePasswords();

                Utility.getInstance().RiempieArrayLista();
            }
        });
    }
}