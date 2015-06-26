package com.academy;

import com.academy.service.ExecutorService;
import com.academy.utils.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class App implements CommandLineRunner {

    @Autowired
    private ExecutorService executorService;

    @Bean
    public ApplicationContextProvider applicationContextProvider(ApplicationContext applicationContext) {
        ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
        applicationContextProvider.setApplicationContext(applicationContext);
        return applicationContextProvider;
    }


    @Override
    public void run(String... strings) throws Exception {
        executorService.startCrawling();
    }

    public static void main(String[] args) {
        // TODO: все конечно здорово парсится и заливается в эластик, но теперь нужен вменяемый UI, который бы все это отобразил
        SpringApplication.run(App.class, args);
    }
}
