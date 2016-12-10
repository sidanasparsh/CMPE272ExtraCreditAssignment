/** Credits:
 * The MIT License
 *
 *	Guillaume Charhon (github.com/poiuytrez)
 *
 *	
 *
 */

/**
 * constructor
 */
function SpeechRecognizer() {
}

/**
 * Recognize speech and return a list of matches
 *
 * @param successCallback
 * @param errorCallback
 * @param reqCode User-defined integer request code which will be returned when recognition is complete
 * @param maxMatches The maximum number of matches to return. 0 means the service decides how many to return.
 * @param promptString An optional string to prompt the user during recognition
 */
SpeechRecognizer.prototype.startRecognize = function(successCallback, errorCallback, maxMatches, promptString, language) {
    return cordova.exec(successCallback, errorCallback, "SpeechRecognizer", "startRecognize", [maxMatches, promptString, language]);
};

/**
 * Get the list of the supported languages in IETF BCP 47 format
 * 
 * @param successCallback
 * @param errorCallback
 *
 * Returns an array of codes in the success callback
 */
SpeechRecognizer.prototype.getSupportedLanguages = function(successCallback, errorCallback) {
    return cordova.exec(successCallback, errorCallback, "SpeechRecognizer", "getSupportedLanguages", []);
};

/**
 * Get the list of the supported languages in IETF BCP 47 format
 *
 * @param successCallback
 * @param errorCallback
 *
 * Return a String message with the result
 */
SpeechRecognizer.prototype.checkSpeechRecognition = function(successCallback, errorCallback) {
    return cordova.exec(successCallback, errorCallback, "SpeechRecognizer", "checkSpeechRecognition", []);
};

/**
 * Export
 */
module.exports = new SpeechRecognizer();

