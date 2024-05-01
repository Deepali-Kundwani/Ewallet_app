package com.example.Ewallet.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DownloadResponse {
    private String tid;
    private Double amount;
    private String description;
    private LocalDate date;
    private String type;
}
