package mobile.solareye.dodidone.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aleksander on 2/23/2015.
 */
public class EventsDataProvider {
    private LinkedHashMap<EventModel, Boolean> items;
    private List<EventModel> addedItems;

    public EventsDataProvider() {
        this.items = new LinkedHashMap<EventModel, Boolean>();
        this.addedItems = new ArrayList<EventModel>();

        for (int i = 1; i < events.length; i += 2 ) {
            items.put(events[i - 1], true);
            items.put(events[i], false);
        }

        buildAddedItems();
    }


    public List<EventModel> getItems() {
        return addedItems;
    }

    public void remove(int position) {
        items.put(addedItems.get(position), false);
        buildAddedItems();
    }

    public int insertAfter(int position) {
        EventModel addAfter = addedItems.get(position);
        Iterator<EventModel> iterator = items.keySet().iterator();
        EventModel next = iterator.next();

        while (iterator.hasNext() && !next.equals(addAfter)) {
            next = iterator.next();
        }

        do {
            next = iterator.next();
        }
        while (iterator.hasNext() && items.get(next));

        items.put(next, true);
        buildAddedItems();

        return addedItems.lastIndexOf(next);
    }

    private void buildAddedItems() {
        addedItems.clear();
        for (Map.Entry<EventModel, Boolean> entry : items.entrySet()) {
            if (entry.getValue()) {
                addedItems.add(entry.getKey());
            }
        }
        //Collections.sort(addedItems);
    }

    public static EventModel[] events = {
            new EventModel(0, "Abram Tavernia",         date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Alexa Oquin",            date(0), 150, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(0)),
            new EventModel(0, "Alvin Lainez",           date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Alyce Rakestraw",        date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Angel Scruggs",          date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Annabel Wardle",         date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Ardella Hollinger",      date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Arlean Drewes",          date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Armida Carasco",         date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Asa Modeste",            date(0), 150, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(0)),
            new EventModel(0, "Ashlea Aguillard",       date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Aurore Maris",           date(0), 150, 50, 0, "Detail information"),
            new EventModel(0, "Bao Godbold",            date(1), 250, 50, 0, "Detail information"),
            new EventModel(0, "Bettye Wenger",          date(1), 250, 50, 0, "Detail information"),
            new EventModel(0, "Bill Thatch",            date(1), 250, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(1)),
            new EventModel(0, "Brad Amis",              date(1), 250, 50, 0, "Detail information"),
            new EventModel(0, "Bridget Goulette",       date(1), 250, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(1)),
            new EventModel(0, "Bryan Rarick",           date(1), 250, 50, 0, "Detail information"),
            new EventModel(0, "Camie Malcolm",          date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Caridad Nesbitt",        date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Carleen Maul",           date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Carmelo Ehrmann",        date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Caroll Ruben",           date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Cherryl Suter",          date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Christeen Bonner",       date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Christene Thrailkill",   date(2), 350, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(2)),
            new EventModel(0, "Cindie Luong",           date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Claudio Llanos",         date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Cleveland Selvage",      date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Clint Cullen",           date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Clora Graybeal",         date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Cristin Culton",         date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Crysta Bolt",            date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Cuc Hetzel",             date(2), 350, 50, 0, "Detail information"),
            new EventModel(0, "Daine Cumbie",           date(3), 450, 50, 0, "Detail information"),
            new EventModel(0, "Danuta Villalta",        date(3), 450, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(3)),
            new EventModel(0, "Darci Quick",            date(3), 450, 50, 0, "Detail information"),
            new EventModel(0, "Darius Hermes",          date(3), 450, 50, 0, "Detail information"),
            new EventModel(0, "Delaine Evins",          date(3), 450, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(3)),
            new EventModel(0, "Delpha Godin",           date(3), 450, 50, 0, "Detail information"),
            new EventModel(0, "Dexter Bencomo",         date(3), 450, 50, 0, "Detail information"),
            new EventModel(0, "Dione Rhines",           date(3), 450, 50, 0, "Detail information"),
            new EventModel(0, "Donella Blumstein",      date(3), 450, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(3)),
            new EventModel(0, "Dorene Kisling",         date(3), 450, 50, 0, "Detail information"),
            new EventModel(0, "Dudley Benavides",       date(3), 450, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(3)),
            new EventModel(0, "Dulce Demille",          date(3), 450, 50, 0, "Detail information"),
            new EventModel(0, "Ebonie Wallis",          date(4), 550, 50, 0, "Detail information"),
            new EventModel(0, "Effie Wiley",            date(4), 550, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(4)),
            new EventModel(0, "Elayne Munro",           date(4), 550, 50, 0, "Detail information"),
            new EventModel(0, "Elisha Funches",         date(4), 550, 50, 0, "Detail information"),
            new EventModel(0, "Elna Padua",             date(4), 550, 50, 0, "Detail information"),
            new EventModel(0, "Emmy Denk",              date(4), 550, 50, 0, "Detail information"),
            new EventModel(0, "Farrah Delosantos",      date(5), 650, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(5)),
            new EventModel(0, "Frieda Buesing",         date(5), 650, 50, 0, "Detail information"),
            new EventModel(0, "Gilda Tse",              date(6), 750, 50, 0, "Detail information"),
            new EventModel(0, "Gina Dufault",           date(6), 750, 50, 0, "Detail information"),
            new EventModel(0, "Giovanna Schepis",       date(6), 750, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(6)),
            new EventModel(0, "Glayds Mcguigan",        date(6), 750, 50, 0, "Detail information"),
            new EventModel(0, "Glinda Dunagan",         date(6), 750, 50, 0, "Detail information"),
            new EventModel(0, "Gwenda Fraser",          date(6), 750, 50, 0, "Detail information"),
            new EventModel(0, "Hai Oday",               date(7), 850, 50, 0, "Detail information"),
            new EventModel(0, "Halley Holscher",        date(7), 850, 50, 0, "Detail information"),
            new EventModel("20 минут свободного времени", date(7)),
            new EventModel(0, "Hellen Baillie",         date(7), 850, 50, 0, "Detail information"),
            new EventModel(0, "Herbert Renninger",      date(7), 850, 50, 0, "Detail information"),
            new EventModel(0, "Hobert Yopp",            date(7), 850, 50, 0, "Detail information"),
            new EventModel(0, "Hollis Haubert",         date(7), 850, 50, 0, "Detail information"),
            new EventModel(0, "Hui Lupien",             date(7), 850, 50, 0, "Detail information"),
            new EventModel(0, "Ileen Mccasland",        date(8), 950, 50, 0, "Detail information"),
            new EventModel(0, "Imelda Moser",           date(8), 950, 50, 0, "Detail information"),
            new EventModel(0, "Ione Littlewood",        date(8), 950, 50, 0, "Detail information"),
            new EventModel(0, "Jacalyn Gressett",       date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jacquelyn Butter",       date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jade Churchwell",        date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jami Selph",             date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Janeth Ringwood",        date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jeffry Carcamo",         date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jerlene Zellers",        date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jerome Tomko",           date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jerrod Rother",          date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jettie Conner",          date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Joaquin Keplinger",      date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Joette Healey",          date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jorge Molina",           date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Juana Olds",             date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Jules Friley",           date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Julio Krier",            date(9), 1050, 50, 0, "Detail information"),
            new EventModel(0, "Kareen Bergey",          date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Katharyn Doten",         date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Katherine Ragsdale",     date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Kathryn Edgington",      date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Katia Hock",             date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Keeley Pass",            date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Kendrick Moncada",       date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Kenyetta Wick",          date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Kimber Boulware",        date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Kitty Manthe",           date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Kristan Blake",          date(10), 1150, 50, 0, "Detail information"),
            new EventModel(0, "Lakeisha Medlin",        date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Lakesha Voth",           date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Lanora Pair",            date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Lashon Abramson",        date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Laurie Campa",           date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Laurinda Barcus",        date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Lavern Puig",            date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Lera Mckibben",          date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Long Show",              date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Louanne Garling",        date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Louella Petillo",        date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Lucinda Sockwell",       date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Lyla Fitzsimons",        date(11), 1250, 50, 0, "Detail information"),
            new EventModel(0, "Mackenzie Ooten",        date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Malia Claiborne",        date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Manie Yarberry",         date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Marchelle Halcomb",      date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Marcie Augusta",         date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Marguerita Tenaglia",    date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Mari Sheperd",           date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Mariela Ruggieri",       date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Marielle Connolly",      date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Marilyn Franck",         date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Marisol Marmolejo",      date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Marth Pitchford",        date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Marty Cobey",            date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Maximo Thornburg",       date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Meggan Plumadore",       date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Mellissa Schnitzer",     date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Melodie Kitch",          date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Mimi File",              date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Mina Nolte",             date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Mira Archuleta",         date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Modesto Higgenbotham",   date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Mohammed Orr",           date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Morgan Maddy",           date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Morgan Mensch",          date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Moriah Grubb",           date(12), 1350, 50, 0, "Detail information"),
            new EventModel(0, "Nedra Dyson",            date(13), 1450, 50, 0, "Detail information"),
            new EventModel(0, "Norene Nelms",           date(13), 1450, 50, 0, "Detail information"),
            new EventModel(0, "Odis Mill",              date(14), 1550, 50, 0, "Detail information"),
            new EventModel(0, "Ok Hutter",              date(14), 1550, 50, 0, "Detail information"),
            new EventModel(0, "Olin Bolander",          date(14), 1550, 50, 0, "Detail information"),
            new EventModel(0, "Otilia Dejulio",         date(14), 1550, 50, 0, "Detail information"),
            new EventModel(0, "Otis Shore",             date(14), 1550, 50, 0, "Detail information"),
            new EventModel(0, "Patrina Crystal",        date(15), 1650, 50, 0, "Detail information"),
            new EventModel(0, "Philip Wengert",         date(15), 1650, 50, 0, "Detail information"),
            new EventModel(0, "Porter Ketner",          date(15), 1650, 50, 0, "Detail information"),
            new EventModel(0, "Rhiannon Lavoie",        date(16), 1750, 50, 0, "Detail information"),
            new EventModel(0, "Richard Domingues",      date(16), 1750, 50, 0, "Detail information"),
            new EventModel(0, "Rochell Molock",         date(16), 1750, 50, 0, "Detail information"),
            new EventModel(0, "Rosalva Gutman",         date(16), 1750, 50, 0, "Detail information"),
            new EventModel(0, "Rosalyn Pesce",          date(16), 1750, 50, 0, "Detail information"),
            new EventModel(0, "Rosaria Rosengarten",    date(16), 1750, 50, 0, "Detail information"),
            new EventModel(0, "Roxane Clayborn",        date(16), 1750, 50, 0, "Detail information"),
            new EventModel(0, "Rozanne Mahaney",        date(16), 1750, 50, 0, "Detail information"),
            new EventModel(0, "Sal Wilkinson",          date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Saundra Lundahl",        date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Scotty Ralph",           date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Seema Boots",            date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Selena Eisenhower",      date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Shae Hellard",           date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Shae Latz",              date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Shantay Wilcox",         date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Shawnda Kees",           date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Shayne Cutler",          date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Shenita Cassette",       date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Sherie Culp",            date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Sherrie Poole",          date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Shirley Cliett",         date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Shizue Alcaraz",         date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Sid Streets",            date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Stacia Twellman",        date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Stasia Slay",            date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Stephen Eagles",         date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Svetlana Hallam",        date(17), 1850, 50, 0, "Detail information"),
            new EventModel(0, "Tambra Buchner",         date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Tamie Branham",          date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Tammera Hutt",           date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Tawanna Rameriz",        date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Tawna Seim",             date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Terisa Whitbeck",        date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Terresa Brantley",       date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Terri Barnaby",          date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Tinisha Gammill",        date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Todd Netter",            date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Toshiko Skowron",        date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Traci Schurr",           date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Trish Perino",           date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Tyesha Bruemmer",        date(18), 1950, 50, 0, "Detail information"),
            new EventModel(0, "Valda Skyles",           date(19), 2050, 50, 0, "Detail information"),
            new EventModel(0, "Vella Montilla",         date(19), 2050, 50, 0, "Detail information"),
            new EventModel(0, "Venita Richarson",       date(19), 2050, 50, 0, "Detail information"),
            new EventModel(0, "Vera Noffsinger",        date(19), 2050, 50, 0, "Detail information"),
            new EventModel(0, "Vinnie Gobeil",          date(19), 2050, 50, 0, "Detail information"),
            new EventModel(0, "Waltraud Nelsen",        date(19), 2050, 50, 0, "Detail information"),
            new EventModel(0, "Wendy Zachery",          date(20), 2150, 50, 0, "Detail information"),
            new EventModel(0, "Willard Qualls",         date(20), 2150, 50, 0, "Detail information"),
            new EventModel(0, "Willetta Zucker",        date(20), 2150, 50, 0, "Detail information"),
            new EventModel(0, "Yen Staton",             date(21), 2250, 50, 0, "Detail information"),
            new EventModel(0, "Yolonda Hadnott",        date(21), 2250, 50, 0, "Detail information"),
            new EventModel(0, "Yoshie Califano",        date(21), 2250, 50, 0, "Detail information"),
            new EventModel(0, "Yu Schilke",             date(21), 2250, 50, 0, "Detail information")
    };

    public void update(int position, EventModel event) {
        addedItems.set(position, event);
    }

    private static long date(int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if(day != 0)
            cal.add(Calendar.DAY_OF_MONTH, day);

        return cal.getTimeInMillis();
    }
}