package br.com.cacira.meutwitter;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Config extends PreferenceActivity{
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
	
    	// Cria as preferências a partir do XML
    	addPreferencesFromResource(R.xml.prefs);
    }

}
