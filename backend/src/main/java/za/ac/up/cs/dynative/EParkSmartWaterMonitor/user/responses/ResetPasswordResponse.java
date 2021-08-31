package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses;

public class ResetPasswordResponse {
    private String code;

    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code= code;
    }

    public ResetPasswordResponse(String code){
        this.code= code;
    }

    public ResetPasswordResponse(){}
}
