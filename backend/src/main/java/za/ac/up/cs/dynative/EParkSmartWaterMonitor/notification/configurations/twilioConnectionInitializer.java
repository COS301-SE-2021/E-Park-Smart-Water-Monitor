package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.configurations;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class twilioConnectionInitializer {

    private final static Logger twilioLogger = LoggerFactory.getLogger(twilioConnectionInitializer.class);
    private final TwilioConfig config;

    @Autowired
    public twilioConnectionInitializer(TwilioConfig config)
    {
        this.config = config;
        Twilio.init(
                config.getAccountId(),
                config.getToken()
        );
        twilioLogger.info("TWILIO INITIALISED on {} ", config.getNumber());
    }
}
