import java.util.*;

import models.Client;
import models.Courier;
import models.ParcelPoint;
import redis.clients.jedis.Jedis;

public class ClientApp {
    private static String listKey1 = "clients";
    private static String listKey2 = "courier";
    private static String listKey3 = "parcelPoint";

    public static void save(Jedis client){
        client.lpush(listKey1, "Krzysztof", "Siczek","0207080362");
        client.lpush(listKey1, "Basia","Jyk","75121968629");
        client.lpush(listKey1, "Jan","Pyk","87452611859");

        client.lpush(listKey2, "03093069448","Kasia","Jora");
        client.lpush(listKey2, "12345678901","Jan","Papa");

        client.lpush(listKey3, "RAD52M", "Swietokrzyska 5");
        client.lpush(listKey3, "RAD08A", "Wyscigowa 25");
    }

    public static void update(Jedis client){
        client.lset(listKey1, 0, "Jaroslaw");
        client.lset(listKey1, 1, "Tusk");
        client.lset(listKey1, 2, "97010562518");
    }

    public static void download(Jedis client){
        List<String> clientsList = client.lrange(listKey1, 0, -1);
        System.out.printf("Clients list: %s\n", clientsList);
        List<String> couriersList = client.lrange(listKey2, 0, -1);
        System.out.printf("Couriers list: %s\n", couriersList);
        List<String> parcelPointList = client.lrange(listKey3, 0, -1);
        System.out.printf("ParcelPoints list: %s\n", parcelPointList);
    }

    public static void pickEmployeeFromList(Jedis client){
        int startIndex;
        int endingIndex;
        Scanner scan = new Scanner(System.in);
        String option = "";
        System.out.println("Pick the option: ");
        System.out.println("0.Employee 1 \n1.Employee 2");
        option = scan.nextLine();

        switch (option) {
            case "0":
                System.out.println("Pracownik numer 1");
                startIndex = 0;
                endingIndex = startIndex + 2;
                List<String> couriersList = client.lrange(listKey2, startIndex, endingIndex);
                System.out.printf("Couriers list: %s\n", couriersList);
                break;
            case "1":
                System.out.println("Pracownik numer 2");
                startIndex = 2;
                endingIndex = startIndex + 2;
                List<String> couriersList2 = client.lrange(listKey2, startIndex, endingIndex);
                System.out.printf("Couriers list: %s\n", couriersList2);
                break;
        }


    }

    public static void main(String[] args) throws Exception {

        try{
            Jedis jedis = new Jedis("localhost");
            System.out.println("Connection Successful");
            System.out.println("The server is running" + jedis.ping());
            jedis.set("library-name", "library");
            System.out.println("Stored string in redis" + jedis.get("library-name"));
        }catch(Exception e){
            System.out.println(e);
        }

        var client = new Jedis();

        Scanner scan = new Scanner(System.in);
        String option = "";
        while (!option.equals("0")) {
            System.out.println("Pick the option: ");
            System.out.println("1.Save \n2.Update \n3.Delete \n4.Download \n5.Process after client \n6.Process after server \n0.Exit");
            option = scan.nextLine();
            switch (option) {
                case "0":
                    System.out.println("Exit");
                    break;
                case "1":
                    System.out.println("Save");
                    save(client);
                    break;
                case "2":
                    System.out.println("Update");
                    update(client);
                    break;
                case "3":
                    System.out.println("Delete");
                    client.flushAll();
                    break;
                case "4":
                    System.out.println("Download");
                    download(client);
                    pickEmployeeFromList(client);
                    break;
                case "5":
                    System.out.println("Cluster-side time call:");
                    System.out.println(client.eval("return redis.call('time')[1]"));
                    break;
                case "6":
                    System.out.println("From what I read it is only possible with Redis Enterprise edition.");
                    break;
            }
        }


    }
}