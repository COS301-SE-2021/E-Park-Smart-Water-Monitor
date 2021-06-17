package za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.responses;

public class EditUserResponse
{

        private String status;
        private Boolean success;

        public EditUserResponse(String status, Boolean success)
        {
            this.status = status;
            this.success = success;
        }

        public EditUserResponse()
        {
        }

        public String getStatus() 
        {
            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public Boolean getSuccess()
        {
            return success;
        }

        public void setSuccess(Boolean success)
        {
            this.success = success;
        }

}
