package uk.wardell.tony.javaeight;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;

/**
 * Created by tony on 28/03/2016.
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
        //streaming.executeEightFive();
        //streaming.highestPrice();
        //streaming.lowestPriceOver10k();
        //streaming.highestPriceOver10k();
        //streaming.maxPrice();
        //streaming.avgPrice();
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

        ducatis.stream()
                .filter(d -> {
                    System.out.println("Examining " + d);
                    return d.contains("gale");

                })
                .map(d -> {
                    System.out.println(d);
                    return d;
                });
    }

    private void executeEightTwo(){

        System.out.println("Examining ducatis");

        ducatisModels.stream()
                .filter(d -> {
                    throw new RuntimeException("Bobbins");
//                    System.out.println("Examining " + d.name);
//                    return  d.name.contains("gale");

                })
                .map(d -> {
                    System.out.println(d);
                    return d;
                });
    }


    private void executeEightThree(){

        System.out.println("Repopulating ducatis");

        List<Ducati> newList = ducatisModels.stream()
                .filter(d -> d.name.contains("gale"))
                .collect(Collectors.toList());
        System.out.println(newList);
    }



    private void executeEightFour(){

        System.out.println("Repopulating ducatis");

        ducatisModels.stream()
                .filter(d -> d.name.contains("gale"))
                .map(Ducati::toString)
                .forEach(System.out::println);
    }


    private void executeEightFive(){

        System.out.println("Getting the name of sports bike ducatis");

        ducatisModels.stream()
                .filter(d -> d.name.contains("gale"))
                .map(Ducati::name)
                .forEach(System.out::println);
    }


    private void highestPrice(){

        System.out.println("Calculating highest priced Ducati");

        ducatisModels.sort(comparing(Ducati::price));
        ducatisModels.stream().forEach(System.out::println);
    }


    private void lowestPriceOver10k(){

        System.out.println("Getting the name of Ducatis over 10000, lowest price, sorted by price");

        ducatisModels.stream()
                .filter(d -> d.price > 10000)
                .sorted(comparingInt(Ducati::price))
                .findFirst()
                .map(Ducati::name)
                .ifPresent(System.out::println);
    }


    private void highestPriceOver10k(){

        System.out.println("Getting the name of the highest priced Ducati over 10k");

        ducatisModels.stream()
                .filter(d -> d.price > 10000)
                .sorted(comparingInt(Ducati::price).reversed())
                .findFirst()
                .ifPresent(d -> {
                    System.out.println(d.name + " £"+d.price);
                });
    }


    private void maxPrice(){

        System.out.println("Use max to get highest priced Ducati");

        final Comparator<Ducati> comp = (d1, d2) -> Integer.compare( d1.price, d2.price);


        ducatisModels.stream()
                .max(comp)
                .ifPresent(d -> {
                    System.out.println(d.name + " £"+d.price);
                });
    }



    private void avgPrice(){

        System.out.println("Get average price of the Ducatis");

        ducatisModels
                .stream()
                .mapToInt(Ducati::price)
                .average()
                .ifPresent(avgPrice -> {
                    System.out.println("£"+avgPrice);
                });
    }


    private void overAveragPrice() {

        System.out.println("Get average price of the Ducatis");

        ducatisModels
                .stream()
                .mapToInt(Ducati::price)
                .average()
                .ifPresent(avgPrice -> {
                    System.out.println("Average price is£" + avgPrice);
                    ducatisModels.stream()
                            .filter(d -> d.price > avgPrice)
                            .forEach(d -> {
                                System.out.println(d.name + " £" + d.price);
                            });
                });
    }


    private void doNull(){

        System.out.println("How can we process a null");

        String result = Stream.of(empty)
                .filter(Objects::nonNull)
                .findFirst()
                .map(Objects::toString)
                .orElse("DEFAULT");

        System.out.println(result);

        Stream.of(empty)
                .filter(e -> e==null)
                .findFirst()
                .ifPresent(System.out::println);


//        Pattern TAXON_ID_PATTERN = Pattern.compile("taxon:[0-9]*");
//        String value=null;
//        int DEFAULT_TAXON_ID = 12356;
//
//        int result =  Stream.of(value)
//                .filter(Objects::nonNull)
//                .findFirst()
//                .map(v -> TAXON_ID_PATTERN.matcher(v))
//                .filter(Matcher::matches)
//                .map(matcher -> Integer.parseInt(matcher.group(1)))
//                .orElse(DEFAULT_TAXON_ID);
//
//        System.out.println(result);

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
