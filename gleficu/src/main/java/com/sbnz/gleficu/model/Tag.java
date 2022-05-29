package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.MovieTag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity{
    @Column(name = "tag", nullable = false)
    public MovieTag tag;
}
