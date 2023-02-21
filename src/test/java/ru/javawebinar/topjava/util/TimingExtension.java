package ru.javawebinar.topjava.util;

import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * @author Alexei Valchuk, 21.02.2023, email: a.valchukav@gmail.com
 */

public class TimingExtension implements
        BeforeTestExecutionCallback, AfterTestExecutionCallback,
        BeforeAllCallback, AfterAllCallback {

    private static final Logger LOG = LoggerFactory.getLogger(TimingExtension.class);

    private StopWatch stopWatch;

    @Override
    public void beforeAll(ExtensionContext context) {
        stopWatch = new StopWatch("Execute time of " + context.getRequiredTestClass().getSimpleName());
    }


    @Override
    public void beforeTestExecution(ExtensionContext context) {
        LOG.info("Start stopWatch");
        stopWatch.start(context.getDisplayName());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        LOG.info('\n' + stopWatch.prettyPrint() + '\n');
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        stopWatch.stop();
        LOG.info("stop stopWatch");
    }
}
