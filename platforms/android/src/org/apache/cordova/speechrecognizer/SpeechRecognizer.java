/**
 * Credits:
 *	Guillaume Charhon (github.com/poiuytrez)
 */
package org.apache.cordova.speechrecognizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;


public class SpeechRecognizer extends CordovaPlugin {
    private static final String LOG_TAG = SpeechRecognizer.class.getSimpleName();
    private static int REQUEST_CODE = 1001;

    private CallbackContext callbackContext;
 
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
		Boolean isValidAction = true;

    	this.callbackContext= callbackContext;


    	if ("startRecognize".equals(action)) {
            // recognize speech
            startSpeechRecognitionActivity(args);     
        }
        else {
            // Invalid action
        	this.callbackContext.error("Unknown action: " + action);
        	isValidAction = false;
        }
    	
        return isValidAction;

    }

	/**
     * Fire an intent to start the speech recognition activity.
     *
     * @param args Argument array with the following string args: [req code][number of matches][prompt string]
     */
    private void startSpeechRecognitionActivity(JSONArray args) {
        int maxMatches = 0;
        String prompt = "";
        String language = Locale.getDefault().toString();

        try {
            if (args.length() > 0) {
            	// Maximum number of matches, 0 means the recognizer decides
                String temp = args.getString(0);
                maxMatches = Integer.parseInt(temp);
            }
            if (args.length() > 1) {
            	// Optional text prompt
                prompt = args.getString(1);
            }
            if (args.length() > 2) {
            	// Optional language specified
            	language = args.getString(2);
            }
        }
        catch (Exception e) {
            Log.e(LOG_TAG, String.format("startSpeechRecognitionActivity exception: %s", e.toString()));
        }

        // Create the intent and set parameters
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);

        if (maxMatches > 0)
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, maxMatches);
        if (!prompt.equals(""))
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, prompt);
        cordova.startActivityForResult(this, intent, REQUEST_CODE);
    }

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            returnSpeechResults(matches);
        }
        else {
            // Failure - Let the caller know
            this.callbackContext.error(Integer.toString(resultCode));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void returnSpeechResults(ArrayList<String> matches) {
        JSONArray jsonMatches = new JSONArray(matches);
        this.callbackContext.success(jsonMatches);
    }
    
}
