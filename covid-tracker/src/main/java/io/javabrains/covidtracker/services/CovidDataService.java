package io.javabrains.covidtracker.services;

import io.javabrains.covidtracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidDataService {

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    public List<LocationStats> getAllStates() {
        return allStates;
    }

    private List<LocationStats> allStates = new ArrayList<LocationStats>();
    @PostConstruct

//seconds, minute,hour, day, month, year (executes every sec)
@Scheduled(cron="* * * * * *")
//makes a http call to the service
    public void fetchVirusData() throws IOException, InterruptedException {
       List<LocationStats> newStats = new ArrayList<LocationStats>();
        //create client
        HttpClient client = HttpClient.newHttpClient();
        //create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
       //get the data as string
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
       // System.out.println((httpResponse.body()));
    StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
       for (CSVRecord record:records )
       {
           LocationStats locationStats = new LocationStats();
           locationStats.setState(record.get("Province/State"));
           locationStats.setCountry(record.get("Country/Region"));
           int latestCases = Integer.parseInt(record.get(record.size()-1));
           int prevDayCases = Integer.parseInt(record.get(record.size()-2));
           locationStats.setLatestCases(latestCases);
           locationStats.setDiffFromPrevDay(latestCases-prevDayCases);
           System.out.println(locationStats);
           newStats.add(locationStats);
           //String state = record.get("Province/State");
           //System.out.println(state);

       }
       this.allStates=newStats;
    }

}
