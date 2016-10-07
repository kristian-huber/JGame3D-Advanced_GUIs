package aurora.util;

import java.io.File;

public class IOManager {
	private static final File APPDATA = new File(System.getenv("APPDATA") + "\\aurora");
	
	/* Loads the Application Data */
	public static void loadAppdata() {

		if (!APPDATA.exists()){
			APPDATA.mkdir();
		}
	}
}
