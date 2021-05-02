package clicker.back.services.impl;

import clicker.back.services.EmailService;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class DefaultEmailService implements EmailService {

    @Value("${apikey}")
    String apiKey;
    @Value("${apiemail}")
    String email;

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
}
