package problem1;

import problem1.cli.ArgParser;

public class Main {

  public static void main(String[] args) {
    System.out.println("Hello World!");
    ArgParser parser = new ArgParser();
    System.out.println(parser.parse(args));
  }
}
