package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EmailRequest;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.responses.EmailResponse;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("NotificationServiceImpl")
public class NotificationServiceImpl implements NotificationService
{
    @Autowired
    @Qualifier("gmail")
    private JavaMailSender mailSender;
    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;

    @Value("${spring.mail.username}")
    String senderUsername;

    public EmailResponse sendMail(EmailRequest eMailRequest) {

        MimeMessagePreparator preparator = mimeMessage -> {

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            message.setTo(eMailRequest.getToAddresses().toArray(new String[eMailRequest.getToAddresses().size()]));
            message.setFrom(senderUsername,eMailRequest.getFrom());
            message.setSubject(eMailRequest.getSubject());
            if (eMailRequest.getBccAddresses()!=null && eMailRequest.getBccAddresses().size()!=0)
                message.setBcc(eMailRequest.getBccAddresses().toArray(new String[eMailRequest.getBccAddresses().size()]));
            if (eMailRequest.getCcAddresses()!=null && eMailRequest.getCcAddresses().size()!=0)
                message.setCc(eMailRequest.getCcAddresses().toArray(new String[eMailRequest.getCcAddresses().size()]));
//            message.setText(eMailRequest.getBody(), false);

            Map<String, Object> templateData = new HashMap<>();
            templateData.put("deviceName", "WATER1000");
            templateData.put("shortDisc", "Has critically low water levels.");
            templateData.put("longBody", "Please check it out at Site ABC as soon as possibly and report your findings.");

            String templateContent = FreeMarkerTemplateUtils
                    .processTemplateIntoString(freemarkerConfig.getConfiguration()
                            .getTemplate("/AlertTemplate.ftlh"),
                            templateData);


            message.setText(templateContent,true);
        };
        mailSender.send(preparator);
        return new EmailResponse("Email sent successfully",true);
    }
}
