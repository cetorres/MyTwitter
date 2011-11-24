/*
 * Meu Twitter
 * Cliente básico de Twitter
 * Aplicativo de teste para demonstrar algumas capacidades do Android
 * 
 * @author: Carlos Eugênio Torres <carlos.torres@cacira.com.br>
 * @date: 22/11/2011
 */
package br.com.cacira.meutwitter;

import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MeuTwitterActivity extends Activity {
    
	Button buttonRefresh;
	ListView listTweets;
	TweetAdapter adapter;
	ProgressDialog progressLoading;
	SharedPreferences prefs;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Carrega botão de atualização
        buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshTwitter();
			}
		});
        
        // Carrega preferências/configurações
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(
        	new OnSharedPreferenceChangeListener() {
        			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        				refreshTwitter();
        			}
       		}
        );
    	
        // Carrega diálogo de progresso
        progressLoading = ProgressDialog.show(this, "", "Carregando tweets...", true);
        
        // Carrega listagem
        listTweets = (ListView) findViewById(R.id.listTweets);
        
        // Atualiza tweets
        refreshTwitter();
    }
    
    public void refreshTwitter()
    {
    	// Inicia a tarefa assíncrona de atualização de tweets
    	new UpdateTweetsTask().execute();
    }
    
    /*
     * Tarefa assíncrona para atualização dos tweets
     */
    private class UpdateTweetsTask extends AsyncTask<Void, Void, List<Tweet>> {
    	protected void onPreExecute() {
    		
    		progressLoading.show();
    	}
    	
		@Override
		protected List<Tweet> doInBackground(Void... params) {
			
			return TwitterApi.getTweetsFromSearch(prefs.getString("twitterSearchParameter", ""));
		}
		
		protected void onPostExecute(List<Tweet> tweetList) {
			
			if (tweetList == null) {
				final AlertDialog d = new AlertDialog.Builder(MeuTwitterActivity.this).setMessage("Não foi possível obter tweets.")
				.setTitle("Erro")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton("Ok", null).create();
				d.show();
			} else {				
				adapter = new TweetAdapter(tweetList);
		        listTweets.setAdapter(adapter);
			}			
			
			progressLoading.dismiss();
        }
    }

    /*
     * Menu da activity
     */
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.menuAbout:
				// Cria o alerta para mostrar
				String texto = "Cliente básico de Twitter.\nAplicativo desenvolvido para demonstrar algumas capacidades do Android em palestras e aulas.\n\nAutor: Carlos Eugênio Torres\nEmail: carlos.torres@cacira.com.br\nWebsite: http://cacira.com.br";
				final SpannableString message = new SpannableString(texto);
				Linkify.addLinks(message, Linkify.ALL);
				final AlertDialog d = new AlertDialog.Builder(this).setMessage(message)
						.setTitle("Meu Twitter")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("Ok", null).create();
				d.show();

				// Torna o textview clicável. Deve ser chamado após o show()
				((TextView) d.findViewById(android.R.id.message))
						.setMovementMethod(LinkMovementMethod.getInstance());
	        	break;
	        	
        	case R.id.menuConfig:
        	    Intent ip = new Intent(this, Config.class);
        	    startActivity(ip);
        	    break;
		}
		return true;
    }
}