package com.lask.poopal_server.poopal_server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/loo/")
public class LooMapDirectory {

    // wire from applciation proerpties
    // this way, next time if the url changes, can just update from hosting env
    // varibles.
    // provided if the table format isnt broken as well
    @Value("${looMapURL}")
    private String looMapURL;

    // Data scrape for all districts
    @GetMapping("/scrape")
    public Map<String, List<Map<String, Object>>> scrape() {
        Map<String, List<Map<String, Object>>> directories = new HashMap<>();
        try {
            Document doc = Jsoup.connect(looMapURL).get(); // take url from application properties
            Elements headers = doc.select("h2[align='center']"); // this one is used to identify the districtName,
                                                                 // styled with this. there are 5

            // goes through the site's html table rows and cells whatevevr
            for (Element header : headers) {
                String districtName = header.text().trim();
                List<Map<String, Object>> dataList = new ArrayList<>();

                // now accessing the table
                Element table = header.nextElementSibling();
                if (table != null && table.tagName().equals("table")) {

                    // now accessing the rows of each header/basically the different distrcts
                    Elements rows = table.select("tr");
                    for (int i = 1; i < rows.size(); i++) {

                        // getting cell data
                        Elements columns = rows.get(i).select("td");
                        if (columns.size() >= 4) {
                            Map<String, Object> entry = new HashMap<>();
                            entry.put("type", columns.get(0).text().trim());
                            entry.put("name", columns.get(1).text().trim());
                            entry.put("rating", columns.get(3).select("i.fa-star").size());
                            dataList.add(entry);
                        }
                    }
                }
                directories.put(districtName, dataList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(directories);
        return directories;
    }

    // get the list of 5 regions
    @GetMapping("/scrape/districts")
    public List<String> getDistricts() {
        List<String> districts = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(looMapURL).get(); // Take URL from application properties
            Elements headers = doc.select("h2[align='center']"); // Identify district names

            for (Element header : headers) {
                String districtName = header.text().trim();
                districts.add(districtName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(districts);
        return districts;
    }

    // Data scrape for a selected district
    @GetMapping("/scrape/{district}")
    public List<Map<String, Object>> scrapByDistrict(@PathVariable String district) {
        List<Map<String, Object>> districtList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(looMapURL).get(); // take url from application properties

            // filter out all elements that don’t match the district name
            Elements headers = doc.select("h2[align='center']")
                    .stream()
                    .filter(element -> element.text().equalsIgnoreCase(district))
                    .collect(Collectors.toCollection(Elements::new));

            for (Element header : headers) {
                Element table = header.nextElementSibling();
                if (table != null && table.tagName().equals("table")) {
                    Elements rows = table.select("tr");
                    for (int i = 1; i < rows.size(); i++) {
                        Elements columns = rows.get(i).select("td");
                        if (columns.size() >= 4) {
                            Map<String, Object> entry = new HashMap<>();
                            entry.put("type", columns.get(0).text().trim());
                            entry.put("name", columns.get(1).text().trim());
                            entry.put("rating", columns.get(3).select("i.fa-star").size());
                            districtList.add(entry);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(districtList);
        return districtList;
    }

}
