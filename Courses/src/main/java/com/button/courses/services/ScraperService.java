package com.button.courses.services;


import com.button.courses.models.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.springframework.stereotype.Service;
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
//            System.out.println(urlLink.size());
//            System.out.println(textCourse.size());

            for(int i=0; i<urlLink.size(); i++) {
                Response res = new Response();
                res.setUrl(urlLink.get(i).attr("href"));
                res.setTitle(textCourse.get(i).text());
                responses.add(res);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
