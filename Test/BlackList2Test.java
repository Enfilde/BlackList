import Exception.BlackListException;
import Exception.IncorrectNameException;
import Exception.DoesNotExistException;
import Exception.AlreadyExistException;
import org.junit.Assert;
import org.junit.Test;

public class BlackList2Test {


    @Test
    public void testBlackListConstructor() throws BlackListException {

        BlackList blackList = new BlackList();
        Assert.assertEquals(0, blackList.getEntriesCount());

        BlackList blackList2 = new BlackList("124", "5846423", "mail@dot.com");
        Assert.assertEquals(3, blackList2.getEntriesCount());
        Assert.assertEquals(true, blackList2.hasEntry("124"));
        Assert.assertEquals(true, blackList2.hasEntry("mail@dot.com"));
        Assert.assertEquals(true, blackList2.hasEntry("5846423"));

    }


    @Test
    public void BlacklistAddEntryCorrectCases() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@gmail.com");
        Assert.assertEquals(1, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("some_addr@gmail.com"));

        blackList.addEntry("3805412387465");
        Assert.assertEquals(2, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("3805412387465"));

        blackList.addEntry("87654321547");
        Assert.assertEquals(3, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("87654321547"));

    }


    @Test(expected = IncorrectNameException.class)
    public void BlacklistAddEntryIncorrectCases() throws BlackListException {
        BlackList blackList = new BlackList();
        blackList.addEntry("");

    }

    @Test(expected = AlreadyExistException.class)
    public void BlacklistAddEntryIncorrectCases_2() throws BlackListException {
        BlackList blackList = new BlackList();
        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("some_addr@test.com");
    }


    @Test
    public void BlacklistDropEntryCorrectCases() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("3805412387465");
        blackList.addEntry("87654321547");

        blackList.dropEntry("3805412387465");

        Assert.assertEquals(2, blackList.getEntriesCount());
        Assert.assertEquals(false, blackList.hasEntry("3805412387465"));

        blackList.dropEntry("some_addr@test.com");

        Assert.assertEquals(1, blackList.getEntriesCount());
        Assert.assertEquals(false, blackList.hasEntry("some_addr@test.com"));

        blackList.dropEntry("87654321547");
        Assert.assertEquals(0, blackList.getEntriesCount());

    }


    @Test(expected = IncorrectNameException.class)
    public void BlacklistDropEntryIncorrectCases() throws BlackListException {
        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("87654321547");
        blackList.addEntry("3805412387465");


        blackList.dropEntry("wrong_format");

    }


    @Test(expected = DoesNotExistException.class)
    public void BlacklistDropEntryIncorrectCases_2() throws BlackListException {
        BlackList blackList = new BlackList();

        blackList.dropEntry("test@mail.com");

    }


    @Test
    public void AddAndDropAndAddEntry() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("3805412387465");
        blackList.addEntry("87654321547");

        Assert.assertEquals(true, blackList.hasEntry("3805412387465"));
        blackList.dropEntry("3805412387465");
        Assert.assertEquals(false, blackList.hasEntry("3805412387465"));

        blackList.addEntry("3805412387465");

        Assert.assertEquals(true, blackList.hasEntry("3805412387465"));
        Assert.assertEquals(3, blackList.getEntriesCount());

    }


    @Test
    public void BlacklistClear() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("3805412387465");
        blackList.addEntry("87654321547");

        Assert.assertEquals(3, blackList.getEntriesCount());
        blackList.clear();
        Assert.assertEquals(0, blackList.getEntriesCount());
        blackList.clear();

    }


    @Test
    public void BlacklistAddEntries() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntries("wrong_mail", "345F566", "correct@mail.com", "111");
        Assert.assertEquals(2, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("correct@mail.com"));
        Assert.assertEquals(true, blackList.hasEntry("111"));

        blackList.addEntries("correct@mail", "111");
        Assert.assertEquals(2, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("correct@mail.com"));
        Assert.assertEquals(true, blackList.hasEntry("111"));

    }


    @Test
    public void DropEntries() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntries("mail1@test.com", "345784245", "correct@mail.com", "111");
        Assert.assertEquals(4, blackList.getEntriesCount());

        blackList.dropEntries("unexisting_correct_mail@domain.net", "wrong_mail", "111");
        Assert.assertEquals(3, blackList.getEntriesCount());

        Assert.assertEquals(false, blackList.hasEntry("111"));

    }


    @Test
    public void GetIntersection() throws BlackListException {

        BlackList list1 = new BlackList("mail@test.com", "345678", "correct@mail.com", "111");
        BlackList list2 = new BlackList("mail@test.com", "345678", "111");
        BlackList intersectionResult = new BlackList("mail@test.com", "345678", "111");

        Assert.assertEquals(intersectionResult.blackList, list1.getIntersection(list2));
        Assert.assertEquals(intersectionResult.blackList,list2.getIntersection(list1));

        BlackList emptyList1 = new BlackList();
        BlackList emptyList2 = new BlackList();

        Assert.assertEquals(0,list1.getIntersection(emptyList1).size());
        Assert.assertEquals(0,emptyList1.getIntersection(emptyList2).size());
        Assert.assertEquals(list2.blackList,list2.getIntersection(list2));

    }


    @Test
    public void GetUnion() throws BlackListException {

        BlackList list1 = new BlackList("mail@test.com", "345678", "correct@mail.com", "111");
        BlackList list2 = new BlackList("mail@test.com", "345678","bush@gmail.ru","322");

        BlackList unionResult = new BlackList("mail@test.com", "345678", "correct@mail.com", "111","bush@gmail.ru","322");

        Assert.assertEquals(unionResult.blackList,list1.getUnion(list2));
        Assert.assertEquals(unionResult.blackList,list2.getUnion(list1));

        BlackList emptyList1 = new BlackList();
        BlackList emptyList2 = new BlackList();

        Assert.assertEquals(list1.blackList,list1.getUnion(emptyList1));
        Assert.assertEquals(list1.blackList,emptyList1.getUnion(list1));
        Assert.assertEquals(0,emptyList1.getUnion(emptyList2).size());
        Assert.assertEquals(list1.blackList,list1.getUnion(list1));

    }


    @Test
    public void GetDifference() throws BlackListException {

        BlackList list1 = new BlackList("mail@test.com", "345678", "correct@mail.com", "111");
        BlackList list2 = new BlackList("mail@test.com", "345678","bush@gmail.ru","322");

        BlackList diffResult = new BlackList("correct@mail.com", "111","bush@gmail.ru","322");

        Assert.assertEquals(diffResult.blackList,list1.getDifference(list2));
        Assert.assertEquals(diffResult.blackList,list2.getDifference(list1));



        BlackList emptyList1 = new BlackList();
        BlackList emptyList2 = new BlackList();

        Assert.assertEquals(list1.blackList,list1.getDifference(emptyList1));
        Assert.assertEquals(list1.blackList,emptyList1.getDifference(list1));
        Assert.assertEquals(0,emptyList1.getDifference(emptyList2).size());
        Assert.assertEquals(0,list1.getDifference(list1).size());

    }
}