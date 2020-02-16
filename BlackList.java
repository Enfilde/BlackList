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
        return (input.matches("[0-9]+"));
    }


    private void checkAlreadyExist(String contact) throws BlackListException{
        if(blackList.contains(contact))throw new AlreadyExistException();
    }


    private void checkIncorrectName(String input) throws BlackListException{
        if((!valEmail(input)) && (!valPhone(input))) throw new IncorrectNameException();
    }


    public BlackList(String...contacts) throws BlackListException {
        blackList = new HashSet<>();
        for(String contact : contacts) {
            checkIncorrectName(contact);
            checkAlreadyExist(contact);
            blackList.add(contact);
        }
    }


    public void addEntry(String contact) throws BlackListException{
        checkIncorrectName(contact);
        checkAlreadyExist(contact);
        blackList.add(contact);
    }


    public boolean hasEntry(String contact) throws BlackListException {
        checkIncorrectName(contact);
        if(blackList.contains(contact))return true;
        else return false;
    }


    public void dropEntry(String contact) throws BlackListException{
        checkIncorrectName(contact);
        if((!blackList.contains(contact)))throw new DoesNotExistException();
        blackList.remove(contact);
    }


    public void clear(){
        blackList.clear();
    }


    public int getEntriesCount(){
        return blackList.size();
    }


    public void addEntries(String...contacts) {
        for (String contact : contacts) {
            if(valEmail(contact) || valPhone(contact)) blackList.add(contact);
        }
    }


    public void dropEntries(String...contacts) {
        for (String contact : contacts){
            if(valEmail(contact) || valPhone(contact)) blackList.remove(contact);
        }
    }


    public HashSet getIntersection(BlackList list){
        HashSet<String> intersection = new HashSet<String>(this.blackList);
        intersection.retainAll(list.blackList);
        return intersection;

    }


    public HashSet getUnion(BlackList list){
        HashSet<String> union = new HashSet<String>(this.blackList);
        union.addAll(list.blackList);
        return union;

    }
}

