package app.tasks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Callable;

public class MailWeatherTask implements Callable<String> {

    private String city;

    public MailWeatherTask(String city) {
        this.city = city;
    }

    @Override
    public String call() throws IOException {
        Document document = Jsoup.connect("https://pogoda.mail.ru/prognoz/" + city).get();
        Elements elements = document.select("div.information__content__temperature");
        return elements.last().text();
    }
}
