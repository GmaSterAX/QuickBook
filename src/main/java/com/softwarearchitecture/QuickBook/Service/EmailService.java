package com.softwarearchitecture.QuickBook.Service;

public interface EmailService {
    void sendSimpleEmail(String toEmail, String subject, String body);
}

