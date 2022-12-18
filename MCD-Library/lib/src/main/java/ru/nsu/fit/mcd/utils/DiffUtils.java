package ru.nsu.fit.mcd.utils;

import java.util.LinkedList;
import java.util.List;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import ru.nsu.fit.mcd.search.report.AggregatedClassReport;

public class DiffUtils {

  public static List<Diff> getClassReportsDiffs(AggregatedClassReport report1,
      AggregatedClassReport report2) {
    var str1 = report1.toString();
    var str2 = report2.toString();
    DiffMatchPatch dmp = new DiffMatchPatch();
    return dmp.diffMain(str1, str2, false);
  }

  public static List<Diff> getDiffs(String report1, String report2) {
    DiffMatchPatch dmp = new DiffMatchPatch();
    return dmp.diffMain(report1, report2, false);
  }

  public static String getClassReportsDiffsHtml(AggregatedClassReport report1,
      AggregatedClassReport report2) {
    var str1 = report1.toString();
    var str2 = report2.toString();
    DiffMatchPatch dmp = new DiffMatchPatch();
    var diffs = dmp.diffMain(str1, str2, false);
    return dmp.diffPrettyHtml(diffs);
  }

  public static String GetHtml(List<Diff> diffs) {
    DiffMatchPatch dmp = new DiffMatchPatch();
    return dmp.diffPrettyHtml(diffs);
  }
}
