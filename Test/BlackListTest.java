import Exception.BlackListException;
import Exception.AlreadyExistException;
import Exception.IncorrectNameException;
import Exception.DoesNotExistException;
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class BlackListTest {


    @Test
    public void testBlackListConstructor() throws BlackListException {

        BlackList blackList = new BlackList();
        Assert.assertEquals(0, blackList.getEntriesCount());

        BlackList blackList2 = new BlackList("+380975018366", "+380975018367", "mail@dot.com");
        Assert.assertEquals(3, blackList2.getEntriesCount());
        Assert.assertEquals(true, blackList2.hasEntry("+380975018366"));
        Assert.assertEquals(true, blackList2.hasEntry("mail@dot.com"));
        Assert.assertEquals(true, blackList2.hasEntry("+380975018367"));

    }


    @Test
    public void BlacklistAddEntryCorrectCases() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@gmail.com");
        Assert.assertEquals(1, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("some_addr@gmail.com"));

        blackList.addEntry("+380975018366");
        Assert.assertEquals(2, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("+380975018366"));

        blackList.addEntry("+380975018367");
        Assert.assertEquals(3, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("+380975018367"));

    }


    @Test
    public void BlacklistAddEntryIncorrectCases() throws BlackListException {
        BlackList blackList = new BlackList();

        try {
            blackList.addEntry("");
            fail();
        } catch (IncorrectNameException e) {
            Assert.assertEquals("Entry Name Is Incorrect", e.getMessage());
        }

        blackList.addEntry("some_addr@test.com");

        try {
            blackList.addEntry("some_addr@test.com");
            fail();
        } catch (AlreadyExistException e) {
            Assert.assertEquals("Entry already exists", e.getMessage());
        }

        Assert.assertEquals(1, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("some_addr@test.com"));

    }


    @Test
    public void BlacklistDropEntryCorrectCases() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("+380541238746");
        blackList.addEntry("+380975018366");

        blackList.dropEntry("+380541238746");

        Assert.assertEquals(2, blackList.getEntriesCount());
        Assert.assertEquals(false, blackList.hasEntry("+380541238746"));

        blackList.dropEntry("some_addr@test.com");

        Assert.assertEquals(1, blackList.getEntriesCount());
        Assert.assertEquals(false, blackList.hasEntry("some_addr@test.com"));

        blackList.dropEntry("+380975018366");
        Assert.assertEquals(0, blackList.getEntriesCount());

    }


    @Test(expected = IncorrectNameException.class)
    public void BlacklistDropEntryIncorrectCases() throws BlackListException {
        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("+380975018366");
        blackList.addEntry("+380541238746");

        blackList.dropEntry("wrong_format");

    }


    @Test(expected = IncorrectNameException.class)
    public void BlacklistDropEntryIncorrectCases_4() throws BlackListException {
        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("+380975018366");
        blackList.addEntry("+380541238746");

        blackList.dropEntry("");
    }


    @Test(expected = DoesNotExistException.class)
    public void BlacklistDropEntryIncorrectCases_2() throws BlackListException {
        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("+380541238746");

        blackList.dropEntry("test@mail.com");

    }


    @Test(expected = DoesNotExistException.class)
    public void BlacklistDropEntryIncorrectCases_3() throws BlackListException {
        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("+380541238746");

        blackList.dropEntry("+380541238746");
        blackList.dropEntry("+380541238746");

    }


    @Test
    public void AddAndDropAndAddEntry() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("+380541238746");
        blackList.addEntry("+380975018366");

        Assert.assertEquals(true, blackList.hasEntry("+380541238746"));
        blackList.dropEntry("+380541238746");
        Assert.assertEquals(false, blackList.hasEntry("+380541238746"));

        blackList.addEntry("+380541238746");

        Assert.assertEquals(true, blackList.hasEntry("+380541238746"));
        Assert.assertEquals(3, blackList.getEntriesCount());

    }


    @Test
    public void BlacklistClear() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntry("some_addr@test.com");
        blackList.addEntry("+380541238746");
        blackList.addEntry("+380975018366");

        Assert.assertEquals(3, blackList.getEntriesCount());
        blackList.clear();
        Assert.assertEquals(0, blackList.getEntriesCount());
        blackList.clear();

    }


    @Test
    public void BlacklistAddEntries() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntries("wrong_mail", "345F566", "correct@mail.com", "+380975018366");
        Assert.assertEquals(2, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("correct@mail.com"));
        Assert.assertEquals(true, blackList.hasEntry("+380975018366"));

        blackList.addEntries("correct@mail", "+380975018366");
        Assert.assertEquals(2, blackList.getEntriesCount());
        Assert.assertEquals(true, blackList.hasEntry("correct@mail.com"));
        Assert.assertEquals(true, blackList.hasEntry("+380975018366"));

    }


    @Test
    public void DropEntries() throws BlackListException {

        BlackList blackList = new BlackList();

        blackList.addEntries("mail1@test.com", "+380975018366", "correct@mail.com", "+380975018367");
        Assert.assertEquals(4, blackList.getEntriesCount());

        blackList.dropEntries("unexisting_correct_mail@domain.net", "wrong_mail", "+380975018366");
        Assert.assertEquals(3, blackList.getEntriesCount());

        Assert.assertEquals(false, blackList.hasEntry("+380975018366"));

    }


    @Test
    public void GetIntersection() throws BlackListException {

        BlackList list1 = new BlackList("mail@test.com", "+380975018366", "correct@mail.com", "+380975018367");
        BlackList list2 = new BlackList("mail@test.com", "+380975018366", "+380975018367");
        BlackList intersectionResult = new BlackList("mail@test.com", "+380975018366", "+380975018367");

        Assert.assertEquals(intersectionResult, list1.getIntersection(list2));
        Assert.assertEquals(intersectionResult, list2.getIntersection(list1));

        BlackList emptyList1 = new BlackList();
        BlackList emptyList2 = new BlackList();

        Assert.assertEquals(0, list1.getIntersection(emptyList1).getEntriesCount());
        Assert.assertEquals(0, emptyList1.getIntersection(emptyList2).getEntriesCount());
        Assert.assertEquals(list2, list2.getIntersection(list2));

    }


    @Test
    public void GetUnion() throws BlackListException {


        BlackList list1 = new BlackList("mail@test.com", "+380975018366", "correct@mail.com", "+380975018367");
        BlackList list2 = new BlackList("mail@test.com", "+380975018367", "bush@gmail.ru", "+380975018368");

        BlackList unionResult = new BlackList("mail@test.com", "+380975018367", "correct@mail.com", "+380975018366", "bush@gmail.ru", "+380975018368");

        Assert.assertEquals(unionResult, list1.getUnion(list2));
        Assert.assertEquals(unionResult, list2.getUnion(list1));

        BlackList emptyList1 = new BlackList();
        BlackList emptyList2 = new BlackList();

        Assert.assertEquals(list1, list1.getUnion(emptyList1));
        Assert.assertEquals(list1, emptyList1.getUnion(list1));
        Assert.assertEquals(0, emptyList1.getUnion(emptyList2).getEntriesCount());
        Assert.assertEquals(list1, list1.getUnion(list1));

    }


    @Test
    public void GetDifference() throws BlackListException {

        BlackList list1 = new BlackList("mail@test.com", "+380975018366", "correct@mail.ru", "+380975018367");
        BlackList list2 = new BlackList("mail@test.com", "+380975018366", "bush@gmail.com", "+380975018368");

        BlackList diffResult = new BlackList("correct@mail.com", "+380975018367", "bush@gmail.ru", "+380975018368");

        Assert.assertEquals(diffResult, list1.getDifference(list2));
        Assert.assertEquals(diffResult, list2.getDifference(list1));

        BlackList emptyList1 = new BlackList();
        BlackList emptyList2 = new BlackList();

        Assert.assertEquals(list1, list1.getDifference(emptyList1));
        Assert.assertEquals(list1, emptyList1.getDifference(list1));
        Assert.assertEquals(0, emptyList1.getDifference(emptyList2).getEntriesCount());
        Assert.assertEquals(0, list1.getDifference(list1).getEntriesCount());

    }
}