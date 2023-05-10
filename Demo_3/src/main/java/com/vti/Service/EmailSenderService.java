package com.vti.Service;

import com.vti.Entity.Account;
import com.vti.Repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IAccountRepository accountRepository;

    public void sendLinkChangePassword(String email){

        // tao ra token
        UUID uuid = UUID.randomUUID(); // dung ham uuid(random) tao ra token
        Account acc = accountRepository.findByEmail(email);
        acc.setToken(uuid.toString());
        acc.setTokenCreated(LocalDateTime.now()); // set thoi gian ton tai token
        accountRepository.save(acc);

        // gui email co chua token ve email nguoi dung
        String subject = "Thay đổi password";
        String body = "Click vào link để thay đổi password: http://127.0.0.1:5500/changePassword.html?token="+ uuid.toString();
        senderMail(email, subject, body);
    }

    // methor gui mail
    public void senderMail(String toEmail, String subject, String body) throws NullPointerException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tienduc2351998@gmail.com");
        message.setTo(toEmail); // dia chi email gui
        message.setText(body);  // noi dung gui mail
        message.setSubject(subject); // tieu de gui mail
        mailSender.send(message);
        System.out.println("Mail send Successful" );
    }

    // methor thay doi password
    public void changePassword(String password, String token) throws Exception {
        Account account = accountRepository.findByToken(token);
        if (!checkTimeToken(account.getTokenCreated())){
            throw new Exception("Token het hạn");
        }
        account.setPassword(new BCryptPasswordEncoder().encode(password));
        account.setToken("");
        accountRepository.save(account);
    }

    // methor kiem tra thoi gian ton tai token
    public boolean checkTimeToken(LocalDateTime tokenCreated){
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreated, now);
        return diff.toMinutes() <= 10;
    }
}
