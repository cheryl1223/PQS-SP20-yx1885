package edu.nyu.pqs.stopwatch.impl;

import edu.nyu.pqs.stopwatch.api.Stopwatch;
import lombok.Getter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class StopwatchImpl implements Stopwatch {
  private static final Logger logger = 
      Logger.getLogger("edu.nyu.pqs.stopwatch.impl.StopwatchImpl");
  private final String id;
  @Getter
  private boolean isRunning;
  @Getter
  private long startTime;
  @Getter
  private long stopTime;
  @Getter
  private long resumeTime;
  @Getter
  private long totalResumeInterval;
  @Getter
  private long lapTime;
  private List<Long> lapTimes;
  private Object lock;
  
  /**
   * Constructor of StopWatchImpl
   * @param the ID of the stopwatch
   */  
  public StopwatchImpl(final String stopwatchID) {
    id = stopwatchID;
    isRunning = false;
    startTime = 0L;
    stopTime = 0L;
    resumeTime = 0L;
    totalResumeInterval = 0L;
    lapTime = 0L;
    lapTimes = new CopyOnWriteArrayList<Long>();
    lock = new Object();
  }
  
  /**
   * Returns the Id of this stopwatch.
   * @return the Id of this stopwatch.  Will never be empty or null.
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Starts the stopwatch.
   * @throws IllegalStateException thrown when the stopwatch is already running
   */
  @Override
  public void start() {
    synchronized (lock) {
      if (isRunning) {
        logger.info("Stopwatch is already running");
        throw new IllegalStateException("Stopwatch is already running");
      }
      if (startTime == 0L) {
        startTime = System.currentTimeMillis();
      } else {
        resumeTime = System.currentTimeMillis();
        totalResumeInterval += resumeTime - stopTime;
        stopTime = 0L;
      }
      isRunning = true;
    }
  }

  /**
   * Stores the time elapsed since the last time lap() was called
   * or since start() was called if this is the first lap.
   * @throws IllegalStateException thrown when the stopwatch isn't running
   */
  @Override
  public void lap() {
    synchronized (lock) {
      if (!isRunning) {
        logger.info("Stopwatch isn't running");
        throw new IllegalStateException("Stopwatch isn't running");
      }
      long previouLapTime = lapTime;
      lapTime = System.currentTimeMillis() - startTime - totalResumeInterval;
      if (resumeTime == 0L) {
        lapTimes.add(lapTime -  previouLapTime);
      } else {
        int lastIndex = lapTimes.size() - 1;
        lapTimes.set(lastIndex, lapTime -  previouLapTime);
        resumeTime = 0L;
      }

    }
  }

  /**
   * Stops the stopwatch (and records one final lap).
   * @throws IllegalStateException thrown when the stopwatch isn't running
   */
  @Override
  public void stop() {
    synchronized (lock) {
      if (!isRunning) {
        logger.info("Stopwatch isn't running");
        throw new IllegalStateException("Stopwatch isn't running");
      }
      long previouLapTime = lapTime;
      long curTime = System.currentTimeMillis();
      lapTime = curTime - startTime - totalResumeInterval;
      lapTimes.add(lapTime -  previouLapTime);
      stopTime = curTime;
      isRunning = false;
    }
  }

  /**
   * Resets the stopwatch.  If the stopwatch is running, this method stops the
   * watch and resets it.  This also clears all recorded laps.
   */
  @Override
  public void reset() {
    synchronized (lock) {
      isRunning = false;
      startTime = 0L;
      stopTime = 0L;
      resumeTime = 0L;
      totalResumeInterval = 0L;
      lapTime = 0L;
      lapTimes = new CopyOnWriteArrayList<Long>();
    }
  }

  /**
   * Returns a list of lap times (in milliseconds).  This method can be called at
   * any time and will not throw an exception.
   * @return a list of recorded lap times or an empty list.
   */
  @Override
  public List<Long> getLapTimes() {
    synchronized (lock) {
      return lapTimes;
    }
  }
  
}
