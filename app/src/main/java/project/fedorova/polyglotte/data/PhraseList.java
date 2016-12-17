package project.fedorova.polyglotte.data;

import java.util.ArrayList;
import java.util.Arrays;

public class PhraseList {
    private static volatile PhraseList instance;
    private String allToDivide = "Greeting:More or less;Not too bad;I am very well, thank you;I am fine!;What's new? What is the news?;How are you? How are you getting on?;How are you doing? How are things?;Hello, hi;Good evening!;Good afternoon!;Good morning!;All the best!;Good bye!;See you;Tomorrow;So-so;Couldn't be worse;\n" +
            "Standart phrases:Yes;No;Please;Thank you (Thanks);Thank you very much;Could you;It's all right;Please, accept my apologies;Young man;Young lady (miss);Sir;Mister n ;Madam;Sorry for;Entrance;Exit;No harm done;Open/ closed;Forbidden;Excuse me;I beg your pardon;Please, forgive me;I am sorry;Excuse me;You are welcome!;It's nothing (not at all);Thank you in advance;I must (would like to) thank you;Thak you very much;Thanks a lot for;Thank you for;Glad (nice) to meet you!;My name is;Let me introduce you to;May i introduce myself?;Tell;Help;Show?;Please;Bring;Read;Give;May i ask you?;May i ask you to?;Will (would) you please, give me?;Do you mind?;May i?;Can i?;Of course (sure);All right;Ok (=okay);I agree;Yes, you may (you can);I shouldn't (don't) mind;I cannot (i can't);It's a pity (unfortunately), i can't;It's impossible;I forbid you to ;By no means!;May i invite you to;The theatre;Restaurant;My place;Let's go to ;With pleasure!;I don't mind;It's a pity;How well i understand you;Don't get upset, things do happen;Don't worry;You did it right;Just a moment (a minute);What is your name?;Май нэйм из;How old are you?;When were you born?;Where are you from?;I am from;Where do you live?;I live in ;What is your native language?;I speak;English;Russian;French;Spanish;Italian;I speak english (russian) a little bit;\n" +
            "Station:What are the fares?;One single and one return ticket for tomorrow, please;Two tickets to, please, for the six thirty pm Train;I want to reserve tickets in advance;I must go and get a ticket for the train (plane, ship);Where can i book a ticket for the train (plane, ship)?;I'd like to pay the fares in advance;I'd like a ticket to the;Nonsmoker (smoker);Slumber coach;I'd like a lower berth;How mane luggage pieces may i take free of charge?;Where can i check my luggage?;Please, take my luggage to ;How does one get to the platform?;How long is it till the train departure?;I want a tiket for tomorrow flight to;What flights are there to?;Is there any direct flight to For the day after tomorrow?;Give me, please a seat by a window;Where is the;Arrivals;Departures;Luggage check-in;Eyquiry office (information desk);Toilet;When does the check-in begin?;The flight is delayed by two hours;Where can i return my ticket?;Where are boat tickets sold?;What is the price of a passage to ;I'd like the first (second, third) class cabin for two;\n" +
            "Passport control:Passport control;Here are my passport and custom declaration;Here is my luggage;It is a private visit;It is a business trip;It is a turistic visit;I travel with a group;Excuse me, i don't understand;I need an interpreter;Call for the head of the group;I will be met;Custom;I have nothing to declare;These are my personal items;This is a present;What is to be mentioned in the customs declaration?;Where can i get my customs papers?;\n" +
            "Orientation in town:I'm seeking;I'm lost;My hotel;Touristic office;Street phone;Chemists;Supermarket;Post office;Bank;Where is the nearest police office;Where is the nearest...?;Metro station;Bus stop;Petrol station;Police;Market;Bakery;Square;Street;Which is the way to the post-office (police station)?;It's about ten minutes walk;It is far off You had better take a bus (taxi, car);\n" +
            "Transport:Where can i take a taxi?;Call a taxi, please;What does it cost to go to?;This address, please;Drive me...;Drive me to the airport;Drive me to the station;Drive me to the hotel;Drive me to a good hotel;Drive to a cheap hotel;Drive me to the city center;Left;Right;I need come back;Stop here, please;What does it cost?;Could you wait for me, please?;What bus must i take to reach Question;How often do the buses run?;What (how much) is the fare ti?;I need one ticket;Tell me,please, where i am to get off?;\n" +
            "Hotel:Registration desk;Do you have a room;Single room;Double room;I want to order a room;With bathroom;With shower;Not expensive;For one night;For a week;How does it cost a night per a man;I pay in cash;I need an iron;Something wrong with light;Something wrong with shower;What's wrong with telephone?;Wake me up, please at 8 o'clock;272 for nine hours;Order a taxi, please for 10 o'clock;\n" +
            "Emergency:Help!;Call the police;Call for a doctor;I'm lost;\n" +
            "Time and dates:Time;Today;Yesterday;Tomorrow;The day before yestarday;The day after tomorrow;Morning;Day;Evening;Night;Week;Days of the week;Monday;Tuesday;Wednesday;Thursday;Friday;Saturday;Sunday;Month;January;February;March;April;May;June;July;August;September;Octorber;November;December;Year;Season;Winter;Spring;Summer;Autumn;Century;Leap year;Tonight;It is noon;It is midnight;It is six (am / pm) sharp;It is ten minutes pas seven am (pm);I haven't a watch;My watch is precise (keeps good time);By my watch;What season is it now?;It's not so cold in england as in russia;What is the weather today;The weather is Today;Fine;Bright;Warm;Sunny;Marvellous;Rainy;Nasty;Frosty;Cold;\n" +
            "Purchases:I'd like to buy a suit for everyday wear;What size is this sweater;I want to try on this dress;Underwear;Jeans;Sweater;Skirt;Costume;Dress (frock);Blouse;I want to buy;How long do they keep this shop open?;Cash-desk;Foodstuffs;Market;Will you reduce the price?;It is free of charge (for nothing); gratis;It is too dear (cheap);By metres;It costs;By the pound;By the piece;What does it cost?;It is sold;What is the price?;I need a black t-shirt;What sport's shoes will you offer me?;I'd like to choose;Soap;Toothpaste;Shampoo;Show me, please;Let's go (do) shopping;We are short of ;We have run out of;Meat;Tinned food;I need a piece of beef;Let's buy some sausage and ham;Give me please ten eggs;Where can we buy the fish?;I need;A head of cabbage;New potatoes;I like fruits;Give me please;One loaf of rye (brown) bread;Long loaf of white (wheat) bread;Is this bread new (fresh) or stale?;\n" +
            "Restaurant:I want to order a table;Waiter;Do you have free tables?;Accept my order;Specialty of the house;Beer;Wine;What year is the wine;Soup;Spaghetti;Macaronis;Sandwich;Cheese / sour cream (sour);Tea / coffee;Soluble coffee;Lettuce;I do not eat meat;Check please;\n" +
            "Numbers:Zero;One;Two;Three;Four;Five;Six;Seven;Eight;Nine;Ten;Eleven;Twelve;Thirteen;Fourteen;Fifteen;Sixteen;Seventeen;Eighteen;Nineteen;Twenty;Twenty one;Twenty two;Thirty;Forty;Fifty;Sixty;Seventy;Eighty;Ninty;Hundred;Thousnad;";
    private ArrayList<String> themes = new ArrayList<>();
    private ArrayList<ArrayList<String>> phrases = new ArrayList<>();
    private PhraseList() {
        String[] tmp = allToDivide.split("\n");
        for (String s : tmp) {
            String[] themeNPhrase = s.split(":");
            themes.add(themeNPhrase[0]);
            phrases.add(new ArrayList<>(Arrays.asList(themeNPhrase[1].split(";"))));
        }
    }

    public static PhraseList getInstance() {
        PhraseList localInstance = instance;
        if (localInstance == null) {
            synchronized (PreferenceVars.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PhraseList();
                }
            }
        }
        return localInstance;
    }

    public ArrayList<String> getThemes() {
        return themes;
    }

    public ArrayList<ArrayList<String>> getPhrases() {
        return phrases;
    }
}
