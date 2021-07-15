package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.exceptions.InvalidRequestException;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.configurations.TwilioConfig;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.models.Topic;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.SMSRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.EmailResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.SMSResponse;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.UserService;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.models.User;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.user.requests.FindUserByIdRequest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("NotificationServiceImpl")
public class NotificationServiceImpl implements NotificationService
{
    @Autowired
    @Qualifier("gmail")
    private JavaMailSender mailSender;
    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;
    private String templateSelector;
    private final TwilioConfig twilioConfig;
    private  UserService userService;

    @Autowired
    public NotificationServiceImpl(TwilioConfig twilioConfig,@Qualifier("UserService")UserService userService) {
        this.twilioConfig = twilioConfig;
        this.userService = userService;
    }

    @Value("${spring.mail.username}")
    String senderUsername;

    public EmailResponse sendMail(EmailRequest eMailRequest) throws InvalidRequestException {
        if (eMailRequest==null){
            return new EmailResponse("Request is null",false);
        }
        if (eMailRequest.getFrom().equals("")||eMailRequest.getTopic()==null||eMailRequest.getToAddresses()==null||eMailRequest.getBody().equals("")||
            eMailRequest.getDescription().equals("")||eMailRequest.getSubject().equals("")||eMailRequest.getEntity().equals(""))   {
            return new EmailResponse("Request is missing parameters",false);
        }
        if (eMailRequest.getToAddresses().size()<1){
            return new EmailResponse("No recipients specified",false);
        }
        MimeMessagePreparator preparator = mimeMessage ->
        {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());


            message.setTo(eMailRequest.getToAddresses().toArray(new String[eMailRequest.getToAddresses().size()]));
            message.setFrom(senderUsername, eMailRequest.getFrom());
            message.setSubject(eMailRequest.getSubject());
            if (eMailRequest.getBccAddresses() != null && eMailRequest.getBccAddresses().size() != 0)
                message.setBcc(eMailRequest.getBccAddresses().toArray(new String[eMailRequest.getBccAddresses().size()]));
            if (eMailRequest.getCcAddresses() != null && eMailRequest.getCcAddresses().size() != 0)
                message.setCc(eMailRequest.getCcAddresses().toArray(new String[eMailRequest.getCcAddresses().size()]));

            Map<String, Object> templateData = new HashMap<>();

            if (eMailRequest.getTopic()== Topic.ALERT)
            {
                templateSelector="/AlertTemplate.ftlh";
            }
            else
            if (eMailRequest.getTopic()== Topic.INSPECTION_REMINDER)
            {
                templateSelector="/InspectionTemplate.ftlh";
            }
            else
            if (eMailRequest.getTopic()== Topic.REFILL_NOTIFICATION)
            {
                templateSelector="/RefillTemplate.ftlh";
            }
            if (eMailRequest.getTopic()== Topic.PASSWORD_RESET)
            {
                templateSelector="/PasswordResetTemplate.ftlh";
            }
            else

            templateData.put("entity", eMailRequest.getEntity());
            templateData.put("shortDisc", eMailRequest.getDescription());
            templateData.put("longBody", eMailRequest.getBody());

            String templateContent = FreeMarkerTemplateUtils
                    .processTemplateIntoString(freemarkerConfig.getConfiguration().getTemplate(templateSelector), templateData);

            message.setText(templateContent, true);
        };
        mailSender.send(preparator);
        return new EmailResponse("Email sent successfully",true);
    }

    @Override
    public SMSResponse sendSMS(SMSRequest smsRequest) throws InvalidRequestException {
        if (smsRequest==null){
            throw new InvalidRequestException("Request is null");
        }
        ArrayList<User> recipients = smsRequest.getRecipients();
        if (recipients.size()==0){
            recipients= new ArrayList<>();
            if (smsRequest.getUserIds()!=null && smsRequest.getUserIds().size()>0) {
                for (int r = 0; r < smsRequest.getUserIds().size(); r++) {
                    User addThisUser = null;
                    addThisUser = userService.findUserById(new FindUserByIdRequest(smsRequest.getUserIds().get(r))).getUser();
                    if (addThisUser != null) {
                        recipients.add(addThisUser);
                    }
                }
            }
            if (recipients.size()==0){
                throw new InvalidRequestException("No recipients specified");
            }

        }

        ArrayList<String>  smsErrors = new ArrayList<>();

        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher("+111 (202) 555-0125");

        for (int i = 0; i < recipients.size(); i++)
        {
            matcher = pattern.matcher(recipients.get(i).getCellNumber());
            if (!matcher.matches())
            {
                smsErrors.add(recipients.get(i).getUsername());
            }

        }
        if (smsErrors.size()!=0)
        {
            String users ="";

            for (int i = 0; i < smsErrors.size(); i++)
            {
                users += smsErrors.get(i);
                if(i!=smsErrors.size()-1)
                users+=",";
            }
            throw new IllegalArgumentException("The following users have invalid phone numbers: " +users +". Please correct and try again.");
        }
        else {

            PhoneNumber from = new PhoneNumber(twilioConfig.getNumber());
            MessageCreator messageCreator;
            PhoneNumber to;

            for (int i = 0; i < recipients.size(); i++) {
                to = new PhoneNumber(recipients.get(i).getCellNumber());
                messageCreator = Message.creator(to, from,"E-Park System: \nHi "+recipients.get(i).getName()+", \n" +smsRequest.getMessage());
                messageCreator.create();
            }
            return new SMSResponse("Messages sent successfully",true);
        }

    }

}
