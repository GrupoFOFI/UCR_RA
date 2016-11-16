package ra.inge.ucr.ucraumentedreality.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * <h1>Command Handler</h1>
 * <p>
 * Class used to handle the commands emmited by the app and interpret actions
 *
 * @author Konrad
 * @version 1.0
 * @since 1.0
 */
public class CommandHandler {

    private Context context;

    public CommandHandler(Context context) {
        this.context = context;
    }
    public void translate(String command) {

        switch (command) {
            case "Hola":
                toastLog("Como estás?");
                break;
            case "Dime":
                toastLog("Qué pasó?");
                break;
            case "Está bien":
                toastLog("Bueno");
                break;
        }
    }

    public void toastLog(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }


}
