package de.simmft.core.routing;

public interface FileMoverStrategy {
   public void atomicMove(String outbox, String inbox);
}
