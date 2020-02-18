import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Exception.AlreadyExistException;
import Exception.BlackListException;
import Exception.DoesNotExistException;
import Exception.IncorrectNameException;

class BlackList {

    public HashSet<String> blackList;

    private static boolean valEmail(String input) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(input);
        return matcher.find();

    }


    public static boolean valPhone(String input) {
        return (input.charAt(0) == '3' && input.charAt(1) == '8' && input.charAt(2) == '0' && input.matches("[0-9]+") && input.length() == 12);
    }


    private void checkAlreadyExist(String contact) throws BlackListException {
        if (blackList.contains(contact)) throw new AlreadyExistException();
    }


    private void checkIncorrectName(String input) throws BlackListException {
        if ((!valEmail(input)) && (!valPhone(input))) throw new IncorrectNameException();
    }


    public BlackList(String... contacts) throws BlackListException {
        blackList = new HashSet<>();
        for (String contact : contacts) {
            checkIncorrectName(contact);
            checkAlreadyExist(contact);
            blackList.add(contact);
        }
    }


    public void addEntry(String contact) throws BlackListException {
        checkIncorrectName(contact);
        checkAlreadyExist(contact);
        blackList.add(contact);
    }


    public boolean hasEntry(String contact) throws BlackListException {
        checkIncorrectName(contact);
        if (blackList.contains(contact)) return true;
        else return false;
    }


    public void dropEntry(String contact) throws BlackListException {
        checkIncorrectName(contact);
        if ((!blackList.contains(contact))) throw new DoesNotExistException();
        blackList.remove(contact);
    }


    public void clear() {
        blackList.clear();
    }


    public int getEntriesCount() {
        return blackList.size();
    }


    public void addEntries(String... contacts) throws IncorrectNameException {
        for (String contact : contacts) {
            if (valEmail(contact) || valPhone(contact)) blackList.add(contact);
        }
    }


    public void dropEntries(String... contacts) throws IncorrectNameException {
        for (String contact : contacts) {
            if (valEmail(contact) || valPhone(contact)) blackList.remove(contact);
        }
    }


    public BlackList getIntersection(BlackList list) throws BlackListException {
        HashSet<String> intersection = new HashSet<String>(this.blackList);
        intersection.retainAll(list.blackList);
        BlackList intersectionList = new BlackList();
        intersectionList.addEntries(intersection.toString());
        return intersectionList;

    }


    public BlackList getUnion(BlackList list) throws BlackListException {
        HashSet<String> union = new HashSet<>(this.blackList);
        union.addAll(list.blackList);
        BlackList unionList = new BlackList();
        unionList.addEntries(union.toString());
        return unionList;

    }


    public BlackList getDifference(BlackList list) throws BlackListException {
        HashSet<String> diff1 = new HashSet<>(this.blackList);
        HashSet<String> diff2 = new HashSet<>(list.blackList);

        diff1.removeAll(list.blackList);
        diff2.removeAll(this.blackList);

        HashSet<String> unionOfDiffs = new HashSet<>(diff1);
        unionOfDiffs.addAll(diff2);

        BlackList differenceList = new BlackList();
        differenceList.addEntries(unionOfDiffs.toString());

        return differenceList;

    }


    public void showList() {
        for (String contact : this.blackList) {
            System.out.println(contact);
        }
    }
}

