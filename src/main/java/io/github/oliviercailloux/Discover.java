package io.github.oliviercailloux;

import static com.google.common.base.Verify.verify;

import com.google.common.collect.Iterables;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.discovery.LauncherDiscoveryListeners;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Discover {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(Discover.class);
  
  public static void main(String[] args) {
    try (LauncherSession session = LauncherFactory.openSession()) {
      LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
          .selectors(DiscoverySelectors.selectPackage(AdditionerTests.class.getPackageName()))
          .selectors(DiscoverySelectors.selectClass(AdditionerTests.class)).filters(
            ClassNameFilter.includeClassNamePatterns(".*Tests")
        ).build();
      SummaryGeneratingListener listener = new SummaryGeneratingListener();
      Launcher launcher = session.getLauncher();
      launcher.registerTestExecutionListeners(listener);
      launcher.registerLauncherDiscoveryListeners(LauncherDiscoveryListeners.logging());
      TestPlan testPlan = launcher.discover(request);
      verify(testPlan.getRoots().size() == 1);
      verify(testPlan.containsTests(), "No tests found.");
      TestIdentifier root = Iterables.getOnlyElement(testPlan.getRoots());
      LOGGER.info("Root: {}.", root.getDisplayName());
      verify(root.isContainer());
      Set<TestIdentifier> children = testPlan.getChildren(root.getUniqueIdObject());
      verify(!children.isEmpty());
      verify(children.size() == 1);
      TestIdentifier child = Iterables.getOnlyElement(children);
      LOGGER.info("Child: {}.", child.getDisplayName());
      verify(child.isContainer());
      launcher.execute(testPlan);
      TestExecutionSummary summary = listener.getSummary();
      StringWriter w = new StringWriter();
      summary.printFailuresTo(new PrintWriter(w));
      LOGGER.info("Tested: {}", w.toString());
    }
  }
}
