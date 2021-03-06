package edu.buaa;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	public static final String PREF_PARAM_1 = "realtime_dataSize1";
    public static final String PREF_PARAM_2 = "realtime_delay1";
    public static final String PREF_PARAM_3 = "reliability_dataSize1";
    public static final String PREF_PARAM_4 = "reliability_reliability1";
    
    public static final String PREF_PARAM_5 = "realtime_dataSize2";
    public static final String PREF_PARAM_6 = "realtime_delay2";
    public static final String PREF_PARAM_7 = "reliability_dataSize2";
    public static final String PREF_PARAM_8 = "reliability_reliability2";
    
    public static final String PREF_PARAM_MODEL_PATH = "model_path";
	// The plug-in ID
	public static final String PLUGIN_ID = "edu.buaa"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
