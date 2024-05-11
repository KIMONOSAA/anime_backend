package com.kimo.anime1.anime.model.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户
 * @author  kimo
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "_user")
public class User implements UserDetails{

    /**
     * 用户id
     */
    @Id
    private UUID id;

    /**
     * 用户名
     */
    private String username;


    /**
     * 密码
     */
    private String password;


    /**
     * 邮箱
     */
    @NotNull
    @Email
    private String email;


    /**
     * 令牌
     */
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;


    /**
     * 评论
     */
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Comment> comments;

    /**
     * 角色
     */
    @Enumerated(EnumType.STRING)
    private Role role;


    /**
     * 用户信息
     */
    @OneToOne(mappedBy = "user")
    private UserInfo userInfo;


    /**
     * 用户头像
     */
    @OneToOne(mappedBy = "user")
    private UserFile userFIle;

    /**
     * 用户关联的评分
     */
    @OneToMany(mappedBy = "user")
    private List<Scoring> scoring;


    /**
     * 是否启用true表示已经登录验证成功，false表示还未登录还没验证成功
     */
    private boolean isEnabled;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;


    public User() {
        this.id = UUID.randomUUID();
    }

    /**
     * 在实体持久化之前调用，用于设置创建时间
     */
    @PrePersist
    protected void onCreate() {
        this.createTime = new Date();
        this.updateTime = new Date(); // 也可以在这里设置updateTime为当前时间
    }

    /**
     * 在实体更新之前调用，用于设置更新时间
     */
    @PreUpdate
    protected void onUpdate() {
        this.updateTime = new Date();
    }

//    private boolean isRegisterAndEmail;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }


    

}
