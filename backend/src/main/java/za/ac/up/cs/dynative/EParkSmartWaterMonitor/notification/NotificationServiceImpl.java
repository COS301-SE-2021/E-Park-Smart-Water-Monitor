package za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import za.ac.up.cs.dynative.EParkSmartWaterMonitor.notification.requests.EMailRequest;

import java.util.ArrayList;
@Service("NotificationServiceImpl")
public class NotificationServiceImpl implements NotificationService
{
    @Autowired
    @Qualifier("gmail")
    private JavaMailSender mailSender;


    public void sendMail(EMailRequest eMailRequest) {

        MimeMessagePreparator preparator = mimeMessage -> {

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(eMailRequest.getToAddresses().toArray(new String[eMailRequest.getToAddresses().size()]));
            message.setFrom(eMailRequest.getFrom(), "");
            message.setSubject(eMailRequest.getSubject());
            if (eMailRequest.getBccAddresses()!=null && eMailRequest.getBccAddresses().size()!=0)
                message.setBcc(eMailRequest.getBccAddresses().toArray(new String[eMailRequest.getBccAddresses().size()]));
            if (eMailRequest.getCcAddresses()!=null && eMailRequest.getCcAddresses().size()!=0)
                message.setCc(eMailRequest.getCcAddresses().toArray(new String[eMailRequest.getCcAddresses().size()]));
            message.setText(eMailRequest.getBody(), false);
        };
        mailSender.send(preparator);
    }
}
