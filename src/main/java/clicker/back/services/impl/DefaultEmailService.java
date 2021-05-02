package clicker.back.services.impl;

import clicker.back.services.EmailService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class DefaultEmailService implements EmailService {

    @Value("${apikey}")
    String apiKey;
    @Value("${apiemail}")
    String email;
    @Value("${templateId}")
    String templateId;

    public Response sendSimpleMessage(String to, String subject, String text) throws IOException {
        Email _from = new Email(email);
        Email _to = new Email(to);
        Content content = new Content("text/html", text);
        Mail mail = new Mail(_from, subject, _to, content);
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        return response;
    }
    public Response sendTemplateMessage(String to, String subject, String secret) throws IOException {
        Email _from = new Email(email);
        Email _to = new Email(to);
        Content content = new Content("text/html","<p>aaaa</p>" );
        Mail mail = new Mail(_from, subject, _to, content);
        Personalization personalization = new Personalization();
        personalization.addDynamicTemplateData("data",secret);
        personalization.addTo(_to);
            mail.setTemplateId(templateId);
        mail.addPersonalization(personalization);
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        return response;
    }
}
