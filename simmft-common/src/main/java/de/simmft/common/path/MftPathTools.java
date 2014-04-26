package de.simmft.common.path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @deprecated
 */

public class MftPathTools {
   
   public static String extractJobUUIDFromPath(String pathToJob) throws MftPathException {
      String regex = ".*/outbox/([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(pathToJob);
      if (matcher.find()) {
         return matcher.group(1);
      } else {
         throw new MftPathException("No job uuid found in '" + pathToJob +"'");
      }

   }

}
