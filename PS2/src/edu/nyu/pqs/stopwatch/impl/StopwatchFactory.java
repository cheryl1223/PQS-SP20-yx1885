package edu.nyu.pqs.stopwatch.impl;

import edu.nyu.pqs.stopwatch.api.Stopwatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;


/**
 * The StopwatchFactory is a thread-safe factory class for Stopwatch objects.
 * It maintains references to all created Stopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {
  private static final Logger logger = 
      Logger.getLogger("edu.nyu.pqs.stopwatch.impl.StopwatchFactory");
  private static Map<String, Stopwatch> stopwatchMap = 
      new ConcurrentHashMap<String, Stopwatch>();
  private static final Object lock = new Object();
  
  /**
   * Creates and returns a new Stopwatch object
   * @param id The identifier of the new object
   * @return The new Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or
   *     already taken.
   */
   public static Stopwatch getStopwatch(String id) {
    if (id == null) {
      logger.info("id is null");
      throw new IllegalArgumentException("id is null");
    }
    if (id.isEmpty()) {
      logger.info("id is empty");
      throw new IllegalArgumentException("id is empty");
    }
    if (stopwatchMap.containsKey(id)) {
      logger.info("id is already taken");
      throw new IllegalArgumentException("id is already taken");
    }
    synchronized (lock) {
      final Stopwatch stopwatch = new StopwatchImpl(id);
      stopwatchMap.put(id, stopwatch);
      logger.info("Created new stopwatch: " + id);
      return stopwatch;
    }
  }

  /**
   * Returns a list of all created stopwatches
   * @return a List of al creates Stopwatch objects.  Returns an empty
   * list if no Stopwatches have been created.
   */
  public static List<Stopwatch> getStopwatches() {
    synchronized (lock) {
      final List<Stopwatch> stopwatchList = new ArrayList<Stopwatch>(stopwatchMap.values());
      return stopwatchList;
    }
  }
}
