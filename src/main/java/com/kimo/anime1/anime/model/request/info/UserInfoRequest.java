package com.kimo.anime1.anime.model.request.info;




import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息请求类
 * @author  kimo
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {
    private String name;
    private String desc;
    private String sex;
    private LocalDate date;
}
