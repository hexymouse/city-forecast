package app.tasks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.Callable;

public class YandexWeatherTask implements Callable<String> {

    private String city;

    public YandexWeatherTask(String city) {
        this.city = city;
    }

    @Override
    public String call() throws IOException {
        Document document = Jsoup.connect("https://yandex.ru/pogoda/" + city).get();
        Elements element = document.select("div.fact__temp > span.temp__value");
        return element.text();
    }
}
