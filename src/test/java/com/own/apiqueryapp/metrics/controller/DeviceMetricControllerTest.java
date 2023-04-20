package com.own.apiqueryapp.metrics.controller;

import com.own.apiqueryapp.metrics.infra.DeviceMetricService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class DeviceMetricControllerTest {

    private DeviceMetricController deviceMetricController;
    private final DeviceMetricService metricService = mock(DeviceMetricService.class);

    @BeforeEach
    public void setup(){
        deviceMetricController = new DeviceMetricController(metricService);
    }

    @Test
    public void mustSearchMetricsUsingNameParameter(){
        var name = "smartwatch";
        String group = null;
        var startDate = LocalDateTime.now();
        var endDate = LocalDateTime.now();

        deviceMetricController.search(name, group, startDate.toString(), endDate.toString());

        verify(metricService, atLeast(1))
                .statisticsOfDeviceBy("name", "smartwatch", startDate, endDate);
    }

    @Test
    public void mustSearchMetricsUsingGroupParameter(){
        String name = null;
        var group = "wearable";
        var startDate = LocalDateTime.now();
        var endDate = LocalDateTime.now();

        deviceMetricController.search(name, group, startDate.toString(), endDate.toString());

        verify(metricService, atLeast(1))
                .statisticsOfDeviceBy("group", "wearable", startDate, endDate);
    }

}