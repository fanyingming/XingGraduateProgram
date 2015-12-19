package edu.buaa;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

public class AbstractPreferenceInitializer1 extends
		AbstractPreferenceInitializer {

	public AbstractPreferenceInitializer1() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
		// TODO Auto-generated method stub
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		store.setDefault(Activator.PREF_PARAM_1, "0");
        store.setDefault(Activator.PREF_PARAM_2, "0");
        store.setDefault(Activator.PREF_PARAM_3, "0");
        store.setDefault(Activator.PREF_PARAM_4, "0");
	}

}
