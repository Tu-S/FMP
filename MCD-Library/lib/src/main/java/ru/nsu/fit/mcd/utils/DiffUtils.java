package ru.nsu.fit.mcd.utils;

import java.util.LinkedList;
import java.util.List;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import ru.nsu.fit.mcd.search.report.AggregatedClassReport;

public class DiffUtils {

  public static void ShowTextDiff() {
    String text1 = "ABCDELMN";
    String text2 = "ABCFGLMN";
    DiffMatchPatch dmp = new DiffMatchPatch();
    LinkedList<Diff> diffs = dmp.diffMain(text1, text2, false);
    String html = dmp.diffPrettyHtml(diffs);
    for (var diff:diffs) {
      System.out.println(diff.operation + " " + diff.text);
    }
  }

  public static List<Diff> getClassReportsDiffs(AggregatedClassReport report1, AggregatedClassReport report2)
  {
    var str1 = report1.toString();
    var str2 = report2.toString();
    DiffMatchPatch dmp = new DiffMatchPatch();
    return dmp.diffMain(str1, str2, false);
  }

  public static String getClassReportsDiffsHtml(AggregatedClassReport report1, AggregatedClassReport report2)
  {
    var str1 = report1.toString();
    var str2 = report2.toString();
    DiffMatchPatch dmp = new DiffMatchPatch();
    var diffs = dmp.diffMain(str1, str2, false);
    return dmp.diffPrettyHtml(diffs);
  }
}
