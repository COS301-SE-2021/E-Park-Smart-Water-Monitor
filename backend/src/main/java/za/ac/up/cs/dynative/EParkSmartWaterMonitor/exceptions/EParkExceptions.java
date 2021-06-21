package za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions;


public class EParkExceptions extends Exception  {
    public EParkExceptions(){

    }

    public EParkExceptions(String message) {
        super(message);
    }

    public EParkExceptions(Throwable cause){
        super(cause);
    }

    public EParkExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public EParkExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
