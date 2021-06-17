package clicker.back.services.impl;

import clicker.back.services.EmailService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class DefaultEmailService implements EmailService {

    @Value("${apikey}")
    String apiKey;
    @Value("${apiemail}")
    String email;
    @Value("${validationtemplateId}")
    String validationTemplateId;
    @Value("${recovertemplateId}")
    String recoverTemplateId;

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
    public Response sendTemplateMessage(String to, String subject, String secret,Boolean mode) throws IOException {
        Email _from = new Email(email);
        Email _to = new Email(to);
        Content content = new Content("text/html","<p>aaaa</p>" );
        Mail mail = new Mail(_from, subject, _to, content);
        mail.personalization.get(0).addDynamicTemplateData("data",secret);

        if(mode){//1 for validation 0 for recover
            mail.setTemplateId(validationTemplateId);
        }else {
            mail.setTemplateId(recoverTemplateId);
        }
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        Response response = sg.api(request);
        return response;
    }
}
