package com.jj.dheaven.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass //테이블로 생성되는걸 막아준다 
abstract class BaseTimeEntity {

        //엔티티 생성, 수정시 시간이 자동으로 저장

        @CreatedDate
        @Column(updatable = false, name = "cre_date")
        private LocalDateTime cre_date;

        @LastModifiedDate
        @Column(name = "upd_date")
        private LocalDateTime upd_date;


}
