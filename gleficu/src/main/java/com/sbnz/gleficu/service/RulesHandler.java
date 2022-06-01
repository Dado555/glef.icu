package com.sbnz.gleficu.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.beans.factory.annotation.Value;

@Service
public class RulesHandler {
    @Value("${rulesDirPath}")
    private String rulesDirPath;

    @Value("${rulesPomPath}")
    private String rulesPomPath;

    public void evaluate(String drl, String ruleFileName) throws IOException, MavenInvocationException {
        this.outputRule(drl, ruleFileName);
        this.mvnRefresh();
    }

    private void outputRule(String drl, String ruleFileName) throws IOException {
        // create new file for new rule
        File dir = new File(this.rulesDirPath);
        if (!dir.exists()) dir.mkdirs();

        String fileName = ruleFileName.replaceAll(" ", "_").toLowerCase() + ".drl";

        File ruleFile = new File(this.rulesDirPath + "/" + fileName);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruleFile))) {
            bw.append(drl);
        }
    }

    public void turnOff(String ruleFileName) throws MavenInvocationException {
        this.deleteRule(ruleFileName);
        this.mvnRefresh();
    }

    private void deleteRule(String ruleFileName)  {
        String fileName = ruleFileName.replaceAll(" ", "_").toLowerCase() + ".drl";
        File ruleFile = new File(this.rulesDirPath + "/" + fileName);
        if (ruleFile.exists()) ruleFile.delete();
    }

    private void mvnRefresh() throws MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();

//        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
//        Map<String, String> systemProperties = runtimeBean.getSystemProperties();
//        Set<String> keys = systemProperties.keySet();
//        for (String key : keys) {
//            String value = systemProperties.get(key);
//            System.out.printf("[%s] = %s.\n", key, value);
//        }

        request.setPomFile(new File(this.rulesPomPath));
        request.setGoals(Arrays.asList("clean", "install"));

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr/share/maven"));  // System.getenv(M2_HOME) --
        invoker.execute(request);
    }
}
