package com.sbnz.gleficu.model.phases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class BasePhase {
    protected Integer userId;
    protected Integer recommendId;
}
