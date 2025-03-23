package com.lask.poopal_server.poopal_server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.lask.poopal_server.poopal_server.models.Place;
import com.lask.poopal_server.poopal_server.models.Toilet;
import com.lask.poopal_server.poopal_server.repository.PlacesRepo;
import com.lask.poopal_server.poopal_server.repository.ToiletRepo;
import com.lask.poopal_server.poopal_server.services.PlaceService;

@SpringBootApplication
public class PoopalServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PoopalServerApplication.class, args);
    }

    @Value("${looMapURL}")
    private String looMapURL;

    private final int batchSize = 50;

    @Autowired
    private ToiletRepo tr;
    @Autowired
    private PlacesRepo pr;
    @Autowired
    private PlaceService ps;

    @Override
    public void run(String... args) {
        if (tr.countToilets() == 0) { // Only scrape if DB is empty
            System.out.println("Database dont have, now scraping the web then adding to db");

            List<Toilet> toilets = scrape();
            tr.saveToilets(toilets, batchSize);
            System.out.println("Scraping and saved to db liao");

            // this current toilets list is is java memory one, there is no ID yet, the id
            // IS DONE IN DATABASE MYSQL
            // RETRIEVE FIRST BEFORE PLACEID, BEUCASE USING REFERENCES
            // TOILET ID IS THE PRIMARY KEY
            List<Toilet> toiletsFromDb = tr.getToilets();

            if (pr.countPlaceIds() == 0) {
                System.out.println("Adding place ids to db");
                List<Place> places = getPlaceIds(toiletsFromDb); // assigns the placeid to the lsit of otilets
                pr.addBatchPlaceIds(places, batchSize); // add to the darn db lmao
                System.out.println("Scraping placeids saved to db liao");
            } else {
                System.out.println("Place ids already in db. Skipping scraping.");
                System.out.println("Total place ids: " + pr.countPlaceIds());
            }
        // } else if (pr.countPlaceIds() == 0) {
        //     // placesids is empty, need to populate
        //     System.out.println("Adding place ids to db");
        //     List<Toilet> toiletsFromDb = tr.getAllToilets();
        //     List<Place> places = getPlaceIds(toiletsFromDb); // assigns the placeid to the lsit of otilets
        //     pr.addBatchPlaceIds(places, batchSize); // add to the darn db lmao
        //     System.out.println("Scraping placeids saved to db liao");
        } else {
            System.out.println("Db already have. Skipping scraping.");
            System.out.println("Total toilets: " + tr.countToilets());
        }
    }

    // actual scraping method, checks html then get matchy matchy data
    // and extract wahatever table data wanted
    public List<Toilet> scrape() {
        List<Toilet> toilets = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(looMapURL).get();
            Elements headers = doc.select("h2[align='center']");

            for (Element header : headers) {
                String districtName = header.text().trim();

                Element table = header.nextElementSibling();
                if (table != null && table.tagName().equals("table")) {
                    Elements rows = table.select("tr");

                    for (int i = 1; i < rows.size(); i++) {
                        Elements columns = rows.get(i).select("td");
                        if (columns.size() >= 4) {
                            Toilet toilet = new Toilet();
                            toilet.setDistrict(districtName);
                            toilet.setType(columns.get(0).text().trim());

                            // extrqqact and trim remove the SBS TRANSOT/SMRT/TOWER with the | symbol
                            String nameText = columns.get(1).text().trim();
                            if (nameText.contains("|")) {
                                nameText = nameText.split("\\|", 2)[1].trim(); // keep second one after |
                            }
                            toilet.setName(nameText);

                            toilet.setRating(columns.get(3).select("i.fa-star").size());
                            toilets.add(toilet);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toilets;
    }

    // GOOGLE MAPS PLACEID FUNCTION
    public List<Place> getPlaceIds(List<Toilet> toilets) {

        List<Place> places = new ArrayList<>();

        for (Toilet toilet : toilets) {
            String placeName = toilet.getName();

            System.out.printf("trying to get place details for ", placeName);

            // this returns [id, lat, lon]
            List<String> idLatLon = ps.getPlaceId(placeName); // retrieved id from ze goog places

            Place p = new Place();

            p.setToiletId(toilet.getId());
            p.setPlaceId(idLatLon.get(0));
            p.setLatitude(Double.parseDouble(idLatLon.get(1)));
            p.setLongitude(Double.parseDouble(idLatLon.get(2)));

            places.add(p);
            System.out.println("Toilet: " + toilet.getName() + " PlaceId: " + idLatLon.get(0));

        }

        return places;
    }

}
