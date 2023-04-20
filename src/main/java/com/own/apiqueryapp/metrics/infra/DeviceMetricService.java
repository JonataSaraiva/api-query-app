package com.own.apiqueryapp.metrics.infra;

import com.own.apiqueryapp.metrics.controller.response.MetricsResponse;
import com.own.apiqueryapp.metrics.model.DeviceMetric;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.ParsedStats;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Slf4j
@Service
public class DeviceMetricService {

    private final ElasticsearchOperations elasticsearchOperations;

    public DeviceMetricService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public Optional<MetricsResponse> statisticsOfDeviceBy(String field,
                                                         String name,
                                                         LocalDateTime startDate,
                                                         LocalDateTime endDate) {

        var queryBuilder = boolQuery()
                .must(matchQuery(field, name))
                .must(rangeQuery("timestamp").gte(startDate).lte(endDate));

        var searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withAggregations(AggregationBuilders.extendedStats("agg").field("metric_value"))
                .build();

        var result = elasticsearchOperations.search(searchQuery, DeviceMetric.class);

        if(!result.hasAggregations())
            return Optional.empty();

        var aggregations = (Aggregations) result.getAggregations().aggregations();
        var stats  = (ParsedStats) aggregations.get("agg");

        return Optional.of(
                new MetricsResponse(stats.getMinAsString(), stats.getMaxAsString(), stats.getAvgAsString())
        );
    }

}
