package com.great.act;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.jupiter.api.Test;

/**
 * @author Y.X
 * @version v1.0.0
 * @create on : 2024/1/28 21:16
 **/
public class ActivitiInitTests {

    @Test
    public void initProcessEngine() {
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        engineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        engineConfiguration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        engineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/act_play?characterEncoding=utf-8&useUnicode=true&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true");
        engineConfiguration.setJdbcUsername("root");
        engineConfiguration.setJdbcPassword("root123");

        ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
        System.out.println(processEngine);
    }
}
