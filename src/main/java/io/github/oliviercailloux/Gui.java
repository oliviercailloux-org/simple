package io.github.oliviercailloux;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;

import javafx.application.Application;

public class Gui<T> {
	private T id;

  public void copy(List<? extends List<String>> rows) {
    /* TODO correct code that doesn’t compile. */
    // this.rows = rows.stream().<Iterable<KeyboardKey>>map(r -> r).<ImmutableList<KeyboardKey>>map(r -> ImmutableList.<KeyboardKey>copyof(r)).collect(ImmutableList.toImmutableList());
    Stream<Iterable<String>> str = rows.stream().<Iterable<String>>map(r -> r);
    List<String> row0 = rows.get(0);
    ImmutableList.copyOf(row0);
    Stream<ImmutableList<String>> str2 = str.<ImmutableList<String>>map(r -> ImmutableList.<String>copyof(r));
  }
}
