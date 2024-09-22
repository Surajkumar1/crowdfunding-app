package com.example.demo.es.service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import com.example.demo.entities.campaign.Campaign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignEsService {

    private final ElasticsearchClient client;

    @Autowired
    public CampaignEsService(ElasticsearchClient client) {
        this.client = client;
    }

    public void createCampaignIndex() throws IOException {
        CreateIndexRequest request = CreateIndexRequest.of(i -> i
                .index("campaign")
                .mappings(m -> m
                        .properties("id", p -> p
                                .long_(l -> l) // Specify it as long type
                        )
                        .properties("title", p -> p
                                .text(t -> t) // Text type for title
                        )
                        .properties("description", p -> p
                                .text(t -> t) // Text type for description
                        )
                        .properties("goalAmount", p -> p
                                .double_(d -> d) // Double type for goal amount
                        )
                        .properties("startDate", p -> p
                                .date(d -> d) // Date type for start date
                        )
                        .properties("endDate", p -> p
                                .date(d -> d) // Date type for end date
                        )
                        .properties("status", p -> p
                                .keyword(k -> k) // Keyword type for enum
                        )
                        .properties("donationAmount", p -> p
                                .double_(d -> d) // Double type for donation amount
                        )
                        .properties("userId", p -> p
                                .long_(l -> l) // Long type for user ID
                        )
                        .properties("createdAt", p -> p
                                .date(d -> d) // Date type for created at
                        )
                        .properties("updatedAt", p -> p
                                .date(d -> d) // Date type for updated at
                        )
                        .properties("isEffective", p -> p
                                .boolean_(b -> b) // Boolean type for isEffective
                        )
                )
        );

        client.indices().create(request);
    }

    public void saveCampaign(Campaign campaign) throws IOException {
        createCampaignIndex();
        IndexRequest<Campaign> request = IndexRequest.of(i -> i
                .index("campaign")
                .id(campaign.getId().toString())
                .document(campaign)
        );
        client.index(request);
    }

    public List<Campaign> searchCampaigns(String title) throws IOException {
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("campaign")
                .query(q -> q
                        .match(m -> m
                                .field("title")
                                .query(title)
                        )
                )
        );
        SearchResponse<Campaign> searchResponse = client.search(searchRequest, Campaign.class);
        return searchResponse.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

}