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
}

