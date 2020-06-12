package uk.ac.ebi.intact.search.interactors.utils;

import java.util.Collection;
import java.util.stream.Collectors;

public class SearchInteractorUtils {

    //Custom version of ClientUtils.escapeQueryChars from solrJ
    public static String escapeQueryChars(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // These characters are part of the query syntax and must be escaped
            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':'
                    || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'
                    || c == '?' || c == '|' || c == '&' || c == ';' || c == '/'
                    || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String convertListIntoSolrFilterValue(Collection<Integer> list) {
        if (list != null) {
            String spaceSeparatedString = list.stream().map(String::valueOf).collect(Collectors.joining(" OR "));
            return spaceSeparatedString;
        } else {
            return "*:*";
        }
    }
}
