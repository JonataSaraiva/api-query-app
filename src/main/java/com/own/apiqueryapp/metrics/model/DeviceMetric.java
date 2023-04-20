package com.own.apiqueryapp.metrics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "device")
public class DeviceMetric {
    @Id
    private String id;
    @Field(type = FieldType.Text, name = "name")
    private String name;
    @Field(type = FieldType.Text, name = "group")
    private String group;
    @Field(type = FieldType.Text, name = "metric_value")
    private String metricValue;
    @Field(type = FieldType.Text, name = "timestamp")
    private String timestamp;
}