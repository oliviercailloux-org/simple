package io.github.oliviercailloux;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

public class Copier {

  public static void copy(List<Collection<String>> rows) {
    Collection<String> row0 = rows.get(0);
    ImmutableList.copyOf(row0);

    Stream<Collection<String>> str = rows.stream().<Collection<String>>map(r -> r);
    Stream<ImmutableList<String>> str2 = str.map(r -> ImmutableList.<String>copyOf(r));

    List.of(row0);
  }
}
