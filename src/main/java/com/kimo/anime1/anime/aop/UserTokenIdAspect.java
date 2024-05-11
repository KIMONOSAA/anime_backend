package com.kimo.anime1.anime.aop;


import com.kimo.anime1.anime.comment.ErrorCode;
import com.kimo.anime1.anime.comment.UseIdHolder;
import com.kimo.anime1.anime.exception.BusinessException;
import com.kimo.anime1.anime.model.entity.User;
import com.kimo.anime1.anime.repository.UserRepository;
import com.kimo.anime1.anime.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 实现获取token的切面
 */
@Aspect
@Component
public class UserTokenIdAspect {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    //定义一个切点，匹配控制层用户信息下的所有方法
    @Pointcut("execution(* com.kimo.anime1.anime.controller.UserInfoController.*(..))")
    public void tokenPointcutForUserInfo() {
    }

    //定义一个切点，匹配控制层用户评论信息下的部分方法
    @Pointcut("execution(* com.kimo.anime1.anime.controller.VideoCommentController.addComments(..)) || " +
              "execution(* com.kimo.anime1.anime.controller.VideoCommentController.updatedComments(..)) || " +
              "execution(* com.kimo.anime1.anime.controller.VideoCommentController.deletedComments(..))")
    public void tokenPointcutForVideoComment() {
    }

    //定义一个切点，匹配控制层用户评分下的部分方法
    @Pointcut("execution(* com.kimo.anime1.anime.controller.VideoScoringController.addSoring(..)) || " +
            "execution(* com.kimo.anime1.anime.controller.VideoScoringController.updatedSoring(..)) || " +
            "execution(* com.kimo.anime1.anime.controller.VideoScoringController.deletedSoring(..))")
    public void tokenPointcutForVideoScoring() {
    }


    //环绕通知，在控制层方法执行前后执行逻辑
    @Around("tokenPointcutForUserInfo() || tokenPointcutForVideoComment() || tokenPointcutForVideoScoring()")
    public Object aroundControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        //从当前线程获取HttpServletRequset
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //从header中获取token
        String token = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(token == null || !token.startsWith("Bearer ")){
            throw new BusinessException(ErrorCode.NO_TOKEN);
        }
        jwt = token.substring(7);

        userEmail = jwtService.extractUsername(jwt);
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new BusinessException(ErrorCode.NO_AUTH));
        UseIdHolder.setUserForToken(user);

        Object result = joinPoint.proceed();

        // 清除ThreadLocal中的token
        UseIdHolder.clearUserId();

        return result;
    }
}
