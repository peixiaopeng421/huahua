package com.an.search;

import huahua.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class SearchApplication {

    public static void main(String[] args){

        SpringApplication.run(SearchApplication.class,args);


    }

    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }

}