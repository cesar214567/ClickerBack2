package clicker.back.services;

import com.sendgrid.Response;

import java.io.IOException;

public interface EmailService {
    Response sendSimpleMessage(String to, String subject, String text) throws IOException;
}
