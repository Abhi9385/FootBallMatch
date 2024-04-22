package com.eminence.footballmatch.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.eminence.footballmatch.dto.FootballMatch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class FootballMatchService {

    @Value("${api.football.match}")
    private String footballMatchUrl;
    private RestTemplate restTemplate;

    @Autowired
    public FootballMatchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }



    public Map<String, Object> fetchDrawMatches(String year) {
        int totalPages = getTotalPages();
        List<CompletableFuture<List<FootballMatch>>> futures = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(totalPages);
        for (int page = 1; page <= totalPages; page++) {
            int finalPage = page;
            CompletableFuture<List<FootballMatch>> future = CompletableFuture.supplyAsync(() -> {
                String apiUrl = footballMatchUrl+"?year="+year +"&page=" + finalPage;
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
                try {
                    return extractDrawMatches(responseEntity.getBody());
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }, executorService);
            futures.add(future);
        }

        List<FootballMatch> drawMatche = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(ignored -> futures.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()))
                .join();

        List<FootballMatch> drawMatches = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        long drawMatchesCount = drawMatches.stream()
                .filter(match -> match.getTeam1goals() == match.getTeam2goals())
                .count();

        executorService.shutdown();

        Map<String, Object> response = new HashMap<>();
        response.put("Total Matches in Year "+year, drawMatche.size());
        response.put("drawMatchesCount", drawMatchesCount);

        return response;
    }

    private List<FootballMatch> extractDrawMatches(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(responseBody);
        JsonNode dataNode = jsonNode.get("data");


        List<FootballMatch> matches = new ArrayList<>();
        for (JsonNode matchNode : dataNode) {
            FootballMatch match = new FootballMatch();
            match.setCompetition(matchNode.get("competition").asText());
            match.setYear(matchNode.get("year").asInt());
            match.setRound(matchNode.get("round").asText());
            match.setTeam1(matchNode.get("team1").asText());
            match.setTeam2(matchNode.get("team2").asText());
            match.setTeam1goals(matchNode.get("team1goals").asInt());
            match.setTeam2goals(matchNode.get("team2goals").asInt());
            matches.add(match);
        }
        // Your JSON parsing logic here
        return matches;
    }

    private int getTotalPages() {
        String apiUrl = "https://jsonmock.hackerrank.com/api/football_matches?year=2011&page=1";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
        int totalPages = Integer.parseInt(responseEntity.getBody().split("\"total_pages\":")[1].split(",")[0].trim());
        return totalPages;
    }

}
