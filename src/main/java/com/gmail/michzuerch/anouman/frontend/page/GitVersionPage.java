package com.gmail.michzuerch.anouman.frontend.page;

import com.gmail.michzuerch.anouman.frontend.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Route(value = "GitVersion", layout = MainLayout.class)
public class GitVersionPage extends VerticalLayout {
    private static final Logger logger = LoggerFactory.getLogger(com.gmail.michzuerch.anouman.frontend.page.GitVersionPage.class);

    private Grid<GitProperty> gitPropertyGrid = new Grid<>(GitProperty.class);
    private H3 gitBranch = new H3();
    private H3 gitBuildTime = new H3();
    private H3 gitCommitId = new H3();

    public GitVersionPage() {
        gitBranch.setText("Branch: " + getBranch());
        gitBuildTime.setText("Build time: " + getBuildTime());
        gitCommitId.setText("Commit id: " + getCommitId());

        gitPropertyGrid.setSizeUndefined();
        gitPropertyGrid.setItems(readGitProperties());
        HorizontalLayout headerLayout = new HorizontalLayout(gitBranch, gitCommitId, gitBuildTime);
        add(headerLayout, gitPropertyGrid);
    }

    private Properties getProperties() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("git.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private String getBranch() {
        return getProperties().getProperty("git.branch");
    }

    private String getBuildTime() {
        return getProperties().getProperty("git.build.time");
    }

    private String getCommitId() {
        return getProperties().getProperty("git.commit.id");
    }

    private Collection<GitProperty> readGitProperties() {
        Properties properties = getProperties();
        List<GitProperty> list = new ArrayList<>();

        Set<String> keys = properties.stringPropertyNames();

        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = properties.getProperty(key);
            list.add(new GitProperty(key, value));
        }
        return list;
    }

    public class GitProperty {
        private String key;
        private String value;

        public GitProperty(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
