package com.great.act;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Y.X
 * @version v1.0.0
 * @create on : 2024/1/19 21:56
 **/
public class ActivitiTests {

    public ProcessEngine initProcessEngine() {
        ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        engineConfiguration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        engineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/act_demo?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        engineConfiguration.setJdbcUsername("root");
        engineConfiguration.setJdbcPassword("root123");

        engineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        return engineConfiguration.buildProcessEngine();
    }

    @Test
    public void deploymentOneProcess() {
        ProcessEngine processEngine = initProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("processes/activiti_process.bpmn20.xml")
                .deploy();
        System.out.println(deploy);
    }

    @Test
    public void startOneProcesses() {
        List<String> names = new ArrayList<>();
        names.add("Sam");
        names.add("Tom");
        names.add("Leo");

        Map<String, Object> params = new HashMap<>();
        params.put("names", names);

        ProcessEngine processEngine = initProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("activiti-process", params);

        System.out.println(processInstance.getActivityId());
    }

    @Test
    public void completeTaskStep1() {
        ProcessEngine processEngine = initProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskAssignee("Sam").singleResult();
        taskService.complete(task.getId());
    }

    @Test
    public void claimTaskStep2() {
        ProcessEngine processEngine = initProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskCandidateOrAssigned("Leo").singleResult();
        taskService.claim(task.getId(), "Leo");
    }

    @Test
    public void completeTaskStep2() {
        ProcessEngine processEngine = initProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskCandidateOrAssigned("Leo").singleResult();
        taskService.complete(task.getId());
    }

    @Test
    public void completeTaskStep3() {
        ProcessEngine processEngine = initProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().taskCandidateOrAssigned("Tom").singleResult();
        taskService.complete(task.getId());
    }
}
