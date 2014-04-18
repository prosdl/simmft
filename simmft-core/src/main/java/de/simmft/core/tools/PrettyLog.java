package de.simmft.core.tools;

public class PrettyLog {
   
   public static final String SEPARATOR_80 = "--------------------------------------------------------------------------------";
   
   public static String boxedHeader(String msg) {
      return new StringBuilder()
         .append('\n')
         .append(SEPARATOR_80)
         .append('\n')
         .append(String.format("-  %s\n", msg))
         .append(SEPARATOR_80)
         .toString();
   }
}
