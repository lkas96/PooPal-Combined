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

import com.lask.poopal_server.poopal_server.models.Toilet;
import com.lask.poopal_server.poopal_server.repository.ToiletRepo;

@SpringBootApplication
public class PoopalServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PoopalServerApplication.class, args);
	}

	@Value("${looMapURL}")
	private String looMapURL;

	private final int batchSize = 25;
	@Autowired private ToiletRepo tr;

	@Override
	public void run(String... args) {
		if (tr.countToilets() == 0) { // Only scrape if DB is empty
			System.out.println("Database dont have, now scraping the web then adding to db");
			List<Toilet> toilets = scrape();
			tr.saveToilets(toilets, batchSize);
			System.out.println("Scraping and saved to db liao");
		} else {
			System.out.println("Db already have. Skipping scraping.");
		}
	}

	//actual scraping method, checks html then get matchy matchy data
	//and extract wahatever table data wanted
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
                            toilet.setName(columns.get(1).text().trim());
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

}
