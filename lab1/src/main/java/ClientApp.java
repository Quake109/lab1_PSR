import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.client.HazelcastClient;

import com.hazelcast.map.IMap;
import models.Client;
import models.Courier;
import models.ParcelPoint;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;



public class ClientApp {

    final private static Random r = new Random(System.currentTimeMillis());
    public static void save(){

        HazelcastInstance client = HazelcastClient.newHazelcastClient();

        Map<Long, Client> clients = client.getMap("clients");
        Map<Long, Courier> couriers = client.getMap("couriers");
        Map<Long, ParcelPoint> parcelPoint = client.getMap("parcelPoints");

        Long clientKey1 = (long) Math.abs(r.nextInt());
        Client client1 = new Client("Krzysztof", "Siczek","0207080362", 666111222);
        System.out.println("PUT " + clientKey1 + " => " + client1);
        clients.put(clientKey1, client1);

        Long clientKey2 = (long) Math.abs(r.nextInt());
        Client client2 = new Client("Basia","Jyk","75121968629",111222333);
        clients.put(clientKey2, client2);
        System.out.println("PUT " + clientKey2 + " => " + client2);

        Long courierKey1 = (long) Math.abs(r.nextInt());
        Courier courier1 = new Courier("Jan","Pyk","87452611859",555333222);
        couriers.put(courierKey1, courier1);
        System.out.println("PUT " + courierKey1 + " => " + courier1);

        Long courierKey2 = (long) Math.abs(r.nextInt());
        Courier courier2 = new Courier("Kasia","Jora","03093069448",222333444);
        couriers.put(courierKey2, courier2);
        System.out.println("PUT " + courierKey2 + " => " + courier2);

        Long courierKey3 = (long) Math.abs(r.nextInt());
        Courier courier3 = new Courier("Jan","Papa","12345678901",111444333);
        couriers.put(courierKey3, courier3);
        System.out.println("PUT " + courierKey3 + " => " + courier3);

        Long parcelPointKey1 = (long) Math.abs(r.nextInt());
        ParcelPoint parcelPoint1 = new ParcelPoint("RAD52M", "Swietokrzyska 5", 100);
        parcelPoint.put(parcelPointKey1, parcelPoint1);
        System.out.println("PUT " + parcelPointKey1 + " => " + parcelPoint1);

        Long parcelPointKey2 = (long) Math.abs(r.nextInt());
        ParcelPoint parcelPoint2 = new ParcelPoint("RAD08A", "Wyscigowa 25", 150);
        parcelPoint.put(parcelPointKey2, parcelPoint2);
        System.out.println("PUT " + parcelPointKey2 + " => " + parcelPoint2);

    }
    public static void download() {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        IMap<Long, Client> clients = client.getMap("clients");
        IMap<Long, Courier> couriers = client.getMap("couriers");
        IMap<Long, ParcelPoint> parcelPoints = client.getMap("parcelPoints");

        System.out.println("All clients: ");
        for (Entry<Long, Client> e : clients.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }

        System.out.println("All couriers: ");
        for (Entry<Long, Courier> e : couriers.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }

        System.out.println("All Parcel Points: ");
        for (Entry<Long, ParcelPoint> e : parcelPoints.entrySet()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }

    }


    public static void getCourierByPesel() {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        System.out.println("Enter the PESEL of courier");
        Scanner scan = new Scanner(System.in);
        String pesel = scan.nextLine();

        IMap<Long, Courier> couriers = client.getMap("couriers");
        long key = -1;
        for (Entry<Long, Courier> entry : couriers.entrySet()) {
            if(entry.getValue().getPesel().equals(pesel)){
             key = entry.getKey();
            }
        }
        if(key != -1){
            Courier courier = couriers.get(key);
            System.out.println("Person found.");
        } else
            System.out.println("PESEL doesnt match");
    }

    public static void delete(){
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        System.out.println("Enter the PESEL of courier");
        Scanner scan = new Scanner(System.in);
        String pesel = scan.nextLine();

        IMap<Long, Courier> couriers = client.getMap("couriers");
        long key = -1;
        for (Entry<Long, Courier> entry : couriers.entrySet()) {
            if(entry.getValue().getPesel().equals(pesel)){
                key = entry.getKey();
            }
        }
        if(key != -1){
            couriers.remove(key);
        } else
            System.out.println("PESEL doesnt match");
    }

    public static void update(){
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        System.out.println("Enter the PESEL of courier");
        Scanner scan = new Scanner(System.in);
        String pesel = scan.nextLine();

        IMap<Long, Client> clients = client.getMap("clients");
        long key = -1;
        for (Entry<Long, Client> entry : clients.entrySet()) {
            if(entry.getValue().getPesel().equals(pesel)){
                key = entry.getKey();
            }
        }
        if(key != -1){
            Client clientModel = clients.get(key);
            clientModel.setPhoneNumer(666111222);
        } else
            System.out.println("PESEL doesnt match");
    }


    public static void main( String[] args ) throws UnknownHostException {

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
                    save();
                    break;
                case "2":
                    System.out.println("Update");
                    update();
                    break;
                case "3":
                    System.out.println("Delete");
                    delete();
                    break;
                case "4":
                    System.out.println("Download");
                    download();
                    getCourierByPesel();
                    break;
                case "5":
                    System.out.println("Process after client");
                    HAgregate.init();
                    break;
                case "6":
                    System.out.println("Process after server");
                    HExecutorService.init();
                    break;
            }
        }


    }
}