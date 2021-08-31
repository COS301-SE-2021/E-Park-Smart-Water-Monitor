package za.ac.up.cs.dynative.EParkSmartWaterMonitor.inspection.responses;

public class DeleteInternalResponse
{
    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success=success;
    }

    boolean success;

    public DeleteInternalResponse(){
    }

    public DeleteInternalResponse(boolean suc){
        success=suc;
    }



}
