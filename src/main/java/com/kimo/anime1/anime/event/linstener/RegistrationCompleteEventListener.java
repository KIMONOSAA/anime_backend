package com.kimo.anime1.anime.event.linstener;

import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.event.RegistrationCompleteEvent;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.security.jwt.JwtService;
import com.kimo.anime1.anime.service.impl.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import static com.kimo.anime1.anime.contant.RedisContants.KEY_UTIL;

/**
 * 注册完成事件监听器
 * @author  kimo
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserService userService;
    private final RedisTemplate<String,String> redisTemplate;
    private final JwtService jwtService;

    @Value("${spring.mail.username}")
    private String masterEmail;
    private final Integer SECONDS = 30;


    private final JavaMailSender mailSender;

    private User theUser;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        theUser = event.getUser();
        redisTemplate.opsForValue().set(KEY_UTIL+theUser.getId(),event.getVerificationCode(), SECONDS,TimeUnit.MINUTES);
        String verificationEmail = event.getVerificationCode();
        try {
            sendVerificationEmail(verificationEmail);
        }catch (MessagingException | UnsupportedEncodingException e){
            throw new BusinessException(ErrorCode.EMAIL_ERROR);
        }
        log.info("以下是你的电子邮件验证码："+ verificationEmail);

    }
    public void sendVerificationEmail(String verificationEmail) throws MessagingException, UnsupportedEncodingException {
        String subject = "验证你的电子邮件";
        String senderName = "验证码";
        String mailContent = "<p>来自Asouli的问候：</p><br />" +
                "<p>感谢你注册Asouli账户。为确保当前是你本人操作,请你输入此邮件中提到的验证码以完成注册。如你无需注册Asouli账户，请忽略该信息</p>"
                + "<br />" + senderName + "<br />" + verificationEmail + "<br />" + "<p>(此验证码将在发送后1分钟过期)</p>" + "<br />" + "<hr />" +
                "Asouli不会通过邮件让你公开或验证你的密码或银行账号号码。如果你收到带有更新账户信息链接的可疑电子邮件，请不要点击该链接，而是应该将该电子邮件报告给Asouli进行调查" + "<br />" + "<hr />";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(masterEmail, senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
