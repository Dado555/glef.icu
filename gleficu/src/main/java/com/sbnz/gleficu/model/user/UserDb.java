package com.sbnz.gleficu.model.user;

import com.sbnz.gleficu.model.BaseUser;
import com.sbnz.gleficu.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class UserDb extends BaseUser {
    @Column(name = "created_at", nullable = true)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private OffsetDateTime deletedAt;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "favourite_tags", nullable = true)
    private String favouriteTags;

    public UserDb(String email, String password, Integer age, Integer roleId,String gender, String favouriteTags) {
        super(email, password, roleId, false, false);
        this.age = age;
        this.gender = gender;
        this.favouriteTags = favouriteTags;
    }
}
