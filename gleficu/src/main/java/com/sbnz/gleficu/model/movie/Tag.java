package com.sbnz.gleficu.model.movie;

import com.sbnz.gleficu.model.BaseEntity;
import com.sbnz.gleficu.model.enums.MovieTag;
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
@Entity
@Table(name = "tag_dbs")
public class Tag extends BaseEntity {
    @Column(name = "created_at", nullable = true)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private OffsetDateTime deletedAt;

    @Column(name = "movie_id", nullable = false)
    public Integer movieId;

    @Column(name = "name", nullable = false)
    public String name;
}
