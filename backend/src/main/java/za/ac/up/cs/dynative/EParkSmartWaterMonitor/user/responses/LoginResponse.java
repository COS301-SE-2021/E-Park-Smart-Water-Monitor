package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses;

public class LoginResponse {

    private String jwt;

    public LoginResponse(){}

    public LoginResponse(String jwt){
        this.jwt = jwt;
    }

    public String getJwt(){
        return jwt;
    }
}
