package uk.wardell.tony.javaeight;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

/**
 * Created by tony on 28/03/2016.
 *
 * Practice Streams in java 8
 *
 * http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
 *
 */
public class Streaming {


    static final List<String> ducatis = Arrays.asList("Multistrada", "Panigale", "Monster", "Scrambler");

    static final List<Ducati> ducatisModels = Arrays.asList(
            new Streaming.Ducati("Multistrada", Ducati.Model.TOURING,   15000),
            new Streaming.Ducati("Panigale",    Ducati.Model.SUPERBIKE, 21000),
            new Streaming.Ducati("Monster",     Ducati.Model.NAKED,     9000),
            new Streaming.Ducati("Scrambler",   Ducati.Model.RETRO,     7000),
            new Streaming.Ducati("Diavel",      Ducati.Model.DIAVEL,    14495));

    final Ducati empty = null;

    public static void main(String[] args) {

        Streaming streaming = new Streaming();
        streaming.executeEight();
        streaming.executeEightThree();
        streaming.executeEightFour();
        streaming.executeEightFive();
        streaming.highestPrice();
        streaming.lowestPriceOver10k();
        streaming.highestPriceOver10k();
        streaming.maxPrice();
        streaming.avgPrice();
        streaming.overAveragPrice();
        streaming.doNull();
    }

    private void execute() {

        for (String aDuc:ducatis){
            if(aDuc.contains("gale")) {
                System.out.println(aDuc);
            }
        }
    }

    private void executeEight(){

        System.out.println("ExecuteEight");

        ducatis.stream()
                .filter(d -> {
                    System.out.println("Examining " + d);
                    return d.contains("gale");

                })
                .map(d -> {
                    System.out.println(d);
                    return d;
                }).forEach(System.out::println);        //doesn't print out without terminal function.
    }


    private void executeEightThree(){

        System.out.println("\nCreating a list of all Ducatis with 'gale' in their name");

        List<Ducati> newList = ducatisModels.stream()
                .filter(d -> d.name.contains("gale"))
                .collect(Collectors.toList());
        System.out.println("\t"+newList);
    }



    private void executeEightFour(){

        System.out.println("\nAnother way to do the same thing");

        ducatisModels.stream()
                .filter(d -> d.name.contains("gale"))
                .map(Ducati::toString)
                .forEach(d -> System.out.println("\t"+d));
    }


    private void executeEightFive(){

        System.out.println("\nGetting the name of sports bike ducatis");

        ducatisModels.stream()
                .filter(d -> d.name.contains("gale"))
                .map(Ducati::name)
                .forEach(d -> System.out.println("\t"+d));
    }


    private void highestPrice(){

        System.out.println("\nCalculating highest priced Ducati");

        List<Ducati> copiedModels = new ArrayList();
        Collections.copy(ducatisModels, copiedModels);

        copiedModels.sort(comparing(Ducati::price));
        copiedModels
                .stream()
                .forEach(d -> System.out.println("\t"+d));
    }


    private void lowestPriceOver10k(){

        System.out.println("\nGetting the name of Ducatis over 10000, lowest price, sorted by price");

        ducatisModels.stream()
                .filter(d -> d.price > 10000)
                .sorted(comparingInt(Ducati::price))
                .findFirst()
                .map(Ducati::name)
                .ifPresent(d -> System.out.println("\t"+d));
    }


    private void highestPriceOver10k(){

        System.out.println("\nGetting the name of the highest priced Ducati over 10k");

        ducatisModels.stream()
                .filter(d -> d.price > 10000)
                .sorted(comparingInt(Ducati::price).reversed())
                .findFirst()
                .ifPresent(d -> {
                    System.out.println("\t"+d.name + " £"+d.price);
                });
    }


    private void maxPrice(){

        System.out.println("\nUse max to get highest priced Ducati");

        final Comparator<Ducati> comp = (d1, d2) -> Integer.compare( d1.price, d2.price);


        ducatisModels.stream()
                .max(comp)
                .ifPresent(d -> {
                    System.out.println("\t"+d.name + " £"+d.price);
                });
    }



    private void avgPrice(){

        System.out.println("\nGet average price of the Ducatis");

        ducatisModels
                .stream()
                .mapToInt(Ducati::price)
                .average()
                .ifPresent(avgPrice -> {
                    System.out.println("\t"+"£"+avgPrice);
                });
    }


    private void overAveragPrice() {

        System.out.println("\nGet average price of the Ducatis");

        ducatisModels
                .stream()
                .mapToInt(Ducati::price)
                .average()
                .ifPresent(avgPrice -> {
                    System.out.println("Average price is£" + avgPrice);
                    ducatisModels.stream()
                            .filter(d -> d.price > avgPrice)
                            .forEach(d -> {
                                System.out.println("\t"+d.name + " £" + d.price);
                            });
                });
    }


    private void doNull(){

        System.out.println("\nHow can we process a null");

        String result = Stream.of(empty)
                .filter(Objects::nonNull)
                .findFirst()
                .map(Objects::toString)
                .orElse("DEFAULT");

        System.out.println("\t"+result);

    }

    private void otherStuff(){
        long bikecount  = Stream
                .builder()
                .add( new Streaming.Ducati("Multistrada", Ducati.Model.TOURING,   15000))
                .add(new Streaming.Ducati("Panigale",    Ducati.Model.SUPERBIKE, 21000))
                .build()
                .count();

        System.out.println("The number of built bikes is " + bikecount);
    }

    //******************************************************************************************************************

    static class Ducati{
        String name;
        int price;
        Model model;
        enum Model {SUPERBIKE, DIAVEL, NAKED, RETRO, TOURING};

        public Ducati(String name, Model model, int cost) {
            this.name=name;
            this.model=model;
            this.price=cost;
        }

        @Override
        public String toString() {
            return "Ducati{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    ", model=" + model +
                    '}';
        }

        public String name() {
            return name;
        }


        public int price(){
            return price;
        }
    }


}
