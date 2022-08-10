package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.AgeRange;
import com.sbnz.gleficu.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "customer")
@Entity
public class User extends BaseUser implements UserDetails {
    @Column(name = "years", nullable = false)
    private Integer years;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name="ageRange")
    private AgeRange ageRange;

    @Column(name = "authority")
    private String authority;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> favouriteTags;

    public User(String email, String password, Integer years, String authority,Gender gender, List<Tag> favouriteTags) {
        super(email, password);
        this.years = years;
        this.gender = gender;
        this.authority = authority;
        this.favouriteTags = favouriteTags;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
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
        return true;
    }
}
