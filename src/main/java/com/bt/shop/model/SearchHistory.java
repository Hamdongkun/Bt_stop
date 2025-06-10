package com.bt.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import jakarta.persistence.Id;

@Table(name = "recent_search")
@Entity
@Getter
@Setter
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String keyword;
    private LocalDateTime searchedAt;

}