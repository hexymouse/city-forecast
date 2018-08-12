package app.services;

import app.tasks.MailWeatherTask;
import app.tasks.YandexWeatherTask;
import com.sun.xml.internal.ws.server.ServerRtException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger LOGGER = Logger.getLogger(WeatherServiceImpl.class.getName());

    private String yandexWeather = null;
    private String mailWeather = null;

    @Override
    public String getTodayTemp(String city) {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> yandexFuture = service.submit(new YandexWeatherTask(city));
        Future<String> mailFuture = service.submit(new MailWeatherTask(city));
        try {
            yandexWeather = yandexFuture.get();
            mailWeather = mailFuture.get();
            LOGGER.info("The weather is " + yandexWeather);
        } catch (InterruptedException e) {
            LOGGER.info(e.getMessage());
        } catch (ExecutionException e) {
            System.out.println(e.getMessage());
        }

        return "Yandex temp = " + yandexWeather + "; mail weather = " + mailWeather;
    }
}
