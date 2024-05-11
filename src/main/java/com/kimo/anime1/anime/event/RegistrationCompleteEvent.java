package com.kimo.anime1.anime.event;

import com.kimo.anime1.anime.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 注册完成发布事件
 * @author  kimo
 */
@Setter
@Getter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String verificationCode;

    public RegistrationCompleteEvent(User user,String verificationCode) {
        super(user);
        this.user = user;
        this.verificationCode = verificationCode;
    }


}
