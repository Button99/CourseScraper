package com.button.courses.services;


import com.button.courses.models.Response;
import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ScraperService {
    private String url= "https://answersq.com/udemy-paid-courses-for-free-with-certificate/";

    public Set<Response> getCourses() {
        Set<Response> responses = new HashSet<>();
        extractFromAnswersQ(responses, url);
        return responses;
    }

    private void extractFromAnswersQ(Set<Response> responses, String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Element element= document.getElementsByClass("elementor-element-b2f25ff").first();
            Elements urlLink = element.select("li").select("a");
            Elements textCourse= element.select("li");


            for(int i=0; i<urlLink.size(); i++) {
                Response res = new Response();
                res.setUrl(urlLink.get(i).attr("href"));
                res.setTitle(textCourse.get(i).text());
                responses.add(res);
            }

            createCsv(responses);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createCsv(Set<Response> responses) {
        File file= new File("FreeCourses.csv");
        try {
            FileWriter fileWriter= new FileWriter(file);
            CSVWriter csvWriter= new CSVWriter(fileWriter);

            String[] header= {"URL", "Title"};
            csvWriter.writeNext(header);
            for(Response r: responses) {
                csvWriter.writeNext(new String[]{r.getUrl(), r.getTitle()});
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
