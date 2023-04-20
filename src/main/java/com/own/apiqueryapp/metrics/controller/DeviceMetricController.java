package com.own.apiqueryapp.metrics.controller;

import static java.time.LocalDateTime.parse;

import com.google.common.base.Strings;
import com.own.apiqueryapp.metrics.controller.response.MetricsResponse;
import com.own.apiqueryapp.metrics.infra.DeviceMetricService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/devices")
public class DeviceMetricController {

    private final DeviceMetricService deviceMetricService;

    public DeviceMetricController(DeviceMetricService deviceMetricService) {
        this.deviceMetricService = deviceMetricService;
    }

    // TODO - Refactor this method
    @GetMapping
    public ResponseEntity<MetricsResponse> search(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String group,
                                      @RequestParam String startDate,
                                      @RequestParam String endDate){
        log.info("new request to metrics of device name: {} and group: {}", name, group);

        Optional<MetricsResponse> metricsResponse;

        if(!Strings.isNullOrEmpty(name))
            metricsResponse = deviceMetricService.statisticsOfDeviceBy("name",  name, parse(startDate), parse(endDate));
         else if (!Strings.isNullOrEmpty(group))
            metricsResponse = deviceMetricService.statisticsOfDeviceBy("group",  group, parse(startDate), parse(endDate));
         else
            return ResponseEntity.badRequest().build();

        return metricsResponse.map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

}
