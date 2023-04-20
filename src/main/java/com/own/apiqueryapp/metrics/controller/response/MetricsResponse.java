package com.own.apiqueryapp.metrics.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricsResponse {
    @JsonProperty("min_value")
    private String minValue;
    @JsonProperty("max_value")
    private String maxValue;
    @JsonProperty("avg_value")
    private String avgValue;
}
