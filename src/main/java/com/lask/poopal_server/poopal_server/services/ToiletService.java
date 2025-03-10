package com.lask.poopal_server.poopal_server.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lask.poopal_server.poopal_server.models.Toilet;
import com.lask.poopal_server.poopal_server.repository.ToiletRepo;

@Service
public class ToiletService {
    
    @Value("${looMapURL}")
    private String looMapURL;

    @Autowired private ToiletRepo tr;

    public void dataScrape() {

        List<Toilet> allToilets = new ArrayList<>();
        
        try {

            Document doc = Jsoup.connect(looMapURL).get(); // take html lines from the url essentially
            Elements headers = doc.select("h2[align='center']"); // this one is used to identify the districtName, styled with this. there are 5

            // goes through the site's html table rows and cells whatevevr
            for (Element header : headers) {
                String districtName = header.text().trim();

                // now accessing the table
                Element table = header.nextElementSibling();
                if (table != null && table.tagName().equals("table")) {

                    // now accessing the rows of each header/basically the different distrcts
                    Elements rows = table.select("tr");
                    for (int i = 1; i < rows.size(); i++) {

                        // getting cell data
                        Elements columns = rows.get(i).select("td");
                        if (columns.size() >= 4) {

                            Toilet aToilet = new Toilet();
                            aToilet.setType(columns.get(0).text().trim());
                            aToilet.setName(columns.get(1).text().trim());
                            aToilet.setRating(columns.get(3).select("i.fa-star").size());
                            aToilet.setDistrict(districtName);

                            allToilets.add(aToilet);
                        }
                    }
                }

            }

            //now insert into database
            tr.saveToilet(allToilets, 20); //save in batches by 20

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Toilet> getAllToilets() {
        return tr.getAllToilets();
    }

}
