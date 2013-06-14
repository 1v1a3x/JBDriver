package com.darkusha.jbdriver;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderControls;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.spring.SpringApplicationContextFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.jbehave.web.selenium.*;
import org.springframework.context.ApplicationContext;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.web.selenium.WebDriverHtmlOutput.WEB_DRIVER_HTML;

public class Stories extends JUnitStories {
    private static Integer frameWidth;
    private static Integer frameHeight;
    private static Boolean withJsonOnly;
    private static Boolean withOutputAfterEachStory;
    private static Boolean excludingStoriesWithNoExecutedScenarios;
    private static Boolean withFailureTrace;
    private static Boolean withFailureTraceCompression;
    private static Boolean withDefaultFormats;
    private static Boolean resetStateBeforeScenario;
    private static Boolean configurationLoaded;
    private static Integer threads;

    static {
        configurationLoaded = retrieveProperties();
    }

    private static boolean retrieveProperties() {
        try {
            InputStream stream = Stories.class.getResourceAsStream("/configuration.properties");
            if (stream != null) {
                Properties properties = new Properties();
                properties.load(stream);
                frameWidth = getIntegerProperty(640, properties, "configuration.selenium.frameWidth");
                frameHeight = getIntegerProperty(120, properties, "configuration.selenium.frameHeight");
                withJsonOnly = getBooleanProperty(true, properties, "configuration.cross.reference.withJsonOnly");
                withOutputAfterEachStory = getBooleanProperty(true, properties, "configuration.cross.reference.withOutputAfterEachStory");
                excludingStoriesWithNoExecutedScenarios = getBooleanProperty(true, properties, "configuration.cross.reference.excludingStoriesWithNoExecutedScenarios");
                withFailureTrace = getBooleanProperty(true, properties, "configuration.story.reporter.builder.withFailureTrace");
                withFailureTraceCompression = getBooleanProperty(true, properties, "configuration.story.reporter.builder.withFailureTraceCompression");
                withDefaultFormats = getBooleanProperty(false, properties, "configuration.story.reporter.builder.withDefaultFormats");
                resetStateBeforeScenario = getBooleanProperty(false, properties, "configuration.resetStateBeforeScenario");
                threads = getIntegerProperty(1,properties, "ui.tests.threads.number");
                System.setProperty("browser",getStringProperty("firefox", properties, "browser"));
                System.setProperty("test.server.url",getStringProperty("http://localhost:80/",properties,"test.server.url"));
                System.setProperty("user.login",getStringProperty("",properties,"user.login"));
                System.setProperty("user.password",getStringProperty("",properties,"user.password"));
                return true;
            }
            return false;
        } catch (Throwable e) {
            return false;
        }
    }

    private static String getStringProperty(String defaultValue, Properties properties, String key) {
        String stringProperty = properties.getProperty(key);
        if (stringProperty != null) {
            return stringProperty;
        }
        return defaultValue;
    }

    private static Integer getIntegerProperty(Integer defaultValue, Properties properties, String key) {
        String stringProperty = properties.getProperty(key);
        if (stringProperty != null) {
            return Integer.valueOf(stringProperty);
        }
        return defaultValue;
    }

    private static Boolean getBooleanProperty(boolean defaultValue, Properties properties, String key) {
        String stringProperty = properties.getProperty(key);
        if (stringProperty != null) {
            return Boolean.valueOf(stringProperty);
        }
        return defaultValue;
    }

    public Stories() {
        if (!configurationLoaded) {
            throw new IllegalStateException("Configuration was not loaded");
        }

        CrossReference crossReference = new CrossReference();
        if (withJsonOnly) {
            crossReference.withXmlOnly();
        }
        crossReference.withOutputAfterEachStory(withOutputAfterEachStory);
        crossReference.excludingStoriesWithNoExecutedScenarios(excludingStoriesWithNoExecutedScenarios);
        ContextView contextView = new LocalFrameContextView().sized(frameWidth, frameHeight);
        SeleniumContext seleniumContext = new SeleniumContext();
        SeleniumStepMonitor stepMonitor = new SeleniumStepMonitor(contextView, seleniumContext,
                crossReference.getStepMonitor());
        Format[] formats = new Format[]{new SeleniumContextOutput(seleniumContext), CONSOLE, WEB_DRIVER_HTML};
        StoryReporterBuilder reporterBuilder = new StoryReporterBuilder()
                .withCodeLocation(codeLocationFromClass(Stories.class));
        reporterBuilder.withFailureTrace(withFailureTrace);
        reporterBuilder.withMultiThreading(true);
        reporterBuilder.withFailureTraceCompression(withFailureTraceCompression);
        if (withDefaultFormats) {
            reporterBuilder.withDefaultFormats();
        }
        reporterBuilder.withFormats(formats).withCrossReference(crossReference);

        Configuration configuration = new SeleniumConfiguration().useSeleniumContext(seleniumContext)
                .useFailureStrategy(new FailingUponPendingStep())
                .useStoryControls(new StoryControls().doResetStateBeforeScenario(resetStateBeforeScenario))
                .useStoryLoader(new LoadFromClasspath(Stories.class))
                .useStoryReporterBuilder(reporterBuilder);
        useConfiguration(configuration);

        ApplicationContext context = new SpringApplicationContextFactory("applicationContext.xml")
                .createApplicationContext();
        useStepsFactory(new SpringStepsFactory(configuration, context));
        Embedder embedder = new Embedder();
        EmbedderControls embedderControls = new EmbedderControls();
        embedderControls.useThreads(threads);
        embedder.useEmbedderControls(embedderControls);
        useEmbedder(embedder);
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(), asList("**/"
                + System.getProperty("storyFilter", "*")
                + ".story"), null);
    }
}
