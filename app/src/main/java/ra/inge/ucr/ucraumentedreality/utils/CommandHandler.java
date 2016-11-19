package ra.inge.ucr.ucraumentedreality.utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

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

    private TextToSpeech textToSpeech;

    private OnCommandInteraction onCommandInteraction;
    private Timer timer;

    public interface OnCommandInteraction {
        void onApproval();

        void onDenial();

        void onDialogMessage();
    }

    public void setOnCommandInteraction(OnCommandInteraction onCommandInteraction) {
        this.onCommandInteraction = onCommandInteraction;
    }

    public CommandHandler(Context context) {
        this.context = context;
        timer = new Timer();
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    Locale locSpanish = new Locale("spa", "MEX");
                    textToSpeech.setLanguage(locSpanish);

                }
            }
        });
    }

    public void translate(String command) {

        switch (command) {
            case "Hola":
                talk2User("Te puedo complacer?");
                toastLog("Te puedo complacer?");
                break;

            case "dime":
                talk2User("Te ofrezco algo de beber?");
                toastLog("Te ofrezco algo de beber?");
                break;

            case "quizás no te haya dicho esto antes":
                talk2User("Pero esque yo no me atrevo a preguntarte");
                toastLog("Pero esque yo no me atrevo a preguntarte");
                break;

            case "Allan":
                talk2User("Es Allan Calderón");
                toastLog("Es Allan Calderón");
                break;

            case "sí":
                talk2User("Bienvenido a la modalidad de accesibilidad");
                toastLog("Bienvenido a la modalidad de accesibilidad");
                onCommandInteraction.onApproval();
                break;

            case "no":
                talk2User("Bienvenido a la modalidad de accesibilidad");
                toastLog("Bienvenido a la modalidad de accesibilidad");
                onCommandInteraction.onDenial();
                break;
        }
    }

    public void toastLog(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }


    public void talk2User(String str) {
        textToSpeech.speak(str, TextToSpeech.QUEUE_ADD, null);
    }

    public void talkUserHelp(boolean activating) {

        if (activating) {

            talk2User("Para utilizar esta aplicación hemos desarrollado una modalidad que permite ejecutar instrucciones mediante comandos de voz");
            toastLog("Para utilizar esta aplicación hemos desarrollado una modalidad que permite ejecutar instrucciones mediante comandos de voz");

            talk2User("Responda Sí o pulse aceptar para ingresar al modo de accesibilidad");
            toastLog("Responda Sí o pulse aceptar para ingresar al modo de accesibilidad");
        } else {

            talk2User("Desea apagar el modo de accesibilidad?");
            toastLog("Desea apagar el modo de accesibilidad?");


            talk2User("Responda Sí o pulse aceptar para apagar el modo de accesibilidad");
            toastLog("Responda Sí o pulse aceptar para apagar el modo de accesibilidad");
        }



        timer.schedule(new TimerTask() {
            @Override
            public void run() {
               Log.i("Waiting", "waiting to finish");
            }
        }, 6*1000);

        onCommandInteraction.onDialogMessage();

}

    private void accesibilityMessage(String str) {

    }


}
